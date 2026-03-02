package training.microservices.eventsourcing;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private final EventRepository eventRepository;

    public Bank(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public UUID createAccount(String name, double initialDeposit) {
        if (initialDeposit < 0) throw new IllegalArgumentException("Начальный депозит не может быть отрицательным");

        UUID accountId = UUID.randomUUID();
        eventRepository.save(new Event(accountId, "AccountCreated", initialDeposit));
        return accountId;
    }

    public void deposit(UUID accountId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        eventRepository.save(new Event(accountId, "MoneyDeposited", amount));
    }

    public void withdraw(UUID accountId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        if (calculateBalance(accountId) < amount) throw new IllegalStateException("Недостаточно средств");
        eventRepository.save(new Event(accountId, "MoneyWithdrawn", amount));
    }

    public void transfer(UUID fromId, UUID toId, double amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    /**
     * 🔄 Вычисление баланса **на основе событий**.
     */
    public double calculateBalance(UUID accountId) {
        return eventRepository.findByAccountIdOrderByDate(accountId)
                .stream()
                .mapToDouble(event -> switch (event.getType()) {
                    case "AccountCreated", "MoneyDeposited" -> event.getAmount();
                    case "MoneyWithdrawn" -> -event.getAmount();
                    default -> 0;
                })
                .sum();
    }

    /**
     * ⏳ Восстановление баланса **на определённую дату** (Time Travel).
     */
    public double getBalanceAtTimestamp(UUID accountId, Date timestamp) {
        return eventRepository.findByAccountIdOrderByDate(accountId)
                .stream()
                .filter(event -> event.getDate().before(timestamp))
                .mapToDouble(event -> switch (event.getType()) {
                    case "AccountCreated", "MoneyDeposited" -> event.getAmount();
                    case "MoneyWithdrawn" -> -event.getAmount();
                    default -> 0;
                })
                .sum();
    }

    /**
     * 📌 **Создание снепшота текущего баланса**.
     */
    public void createSnapshot(UUID accountId) {
        double balance = calculateBalance(accountId);
        eventRepository.save(new Event(accountId, "SnapshotCreated", balance));
    }

    /**
     * 🚀 **Восстановление баланса с использованием снепшота**.
     */
    public double restoreFromSnapshot(UUID accountId) {
        List<Event> events = eventRepository.findByAccountIdOrderByDate(accountId);

        // Найти последний снепшот
        Event lastSnapshot = events.stream()
                .filter(event -> "SnapshotCreated".equals(event.getType()))
                .reduce((first, second) -> second) // Берём **последний** снепшот
                .orElseThrow(() -> new RuntimeException("Снепшот не найден"));

        // Начинаем баланс с последнего снепшота
        double balance = lastSnapshot.getAmount();

        // Применяем **только новые события** после снепшота
        for (Event event : events) {
            if (event.getDate().after(lastSnapshot.getDate())) {
                balance += switch (event.getType()) {
                    case "MoneyDeposited" -> event.getAmount();
                    case "MoneyWithdrawn" -> -event.getAmount();
                    default -> 0;
                };
            }
        }

        return balance;
    }

    /**
     * 📜 Получение истории операций аккаунта.
     */
    public List<Event> getAuditLog(UUID accountId) {
        return eventRepository.findByAccountIdOrderByDate(accountId);
    }
}