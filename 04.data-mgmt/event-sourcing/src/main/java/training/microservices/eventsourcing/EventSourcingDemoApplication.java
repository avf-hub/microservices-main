package training.microservices.eventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class EventSourcingDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EventSourcingDemoApplication.class, args);
        EventRepository eventRepository = context.getBean(EventRepository.class);
        Bank bank = new Bank(eventRepository);

        // 1️⃣ Создание аккаунтов
        System.out.println("\n>>>> Создаём счета для Алисы и Боба");
        UUID aliceId = bank.createAccount("Алиса", 1000);
        UUID bobId = bank.createAccount("Боб", 500);

        printAccountInfo("✅ Счета созданы:", bank, aliceId, bobId);

        // 2️⃣ Пополнение и снятие
        System.out.println("\n>>>> Пополняем счёт Алисы на 300");
        bank.deposit(aliceId, 300);
        printAccountInfo("✅ Баланс после пополнения:", bank, aliceId);

        System.out.println("\n>>>> Снимаем 600 со счёта Алисы");
        bank.withdraw(aliceId, 600);
        printAccountInfo("✅ Баланс после снятия:", bank, aliceId);

        // Сохраняем момент времени для Time Travel
        Date midPointTime = new Date();
        System.out.println("\n ====== ⏳ Сохраняем момент времени для Time Travel: " + midPointTime + " ⏳======");

        System.out.println("\n>>>> Выполняем перевод 200 от Алисы к Бобу");
        bank.transfer(aliceId, bobId, 200);
        printAccountInfo("✅ Баланс после всех операций (Алиса, Боб):", bank, aliceId, bobId);

        // 3️⃣ Восстановление состояния на определённую дату (Time Travel)
        System.out.println("\n>>>> Time Travel: Восстанавливаем баланс Алисы на момент сохранённой даты");
        double balanceAtMidPoint = bank.getBalanceAtTimestamp(aliceId, midPointTime);
        System.out.printf("✅ Баланс Алисы на момент сохранённой даты: %.2f%n", balanceAtMidPoint);

        // 4️⃣ Создание снепшота баланса
        System.out.println("\n>>>> Создаём снепшот баланса Алисы");
        bank.createSnapshot(aliceId);
        printAccountInfo("✅ Баланс после создания снепшота:", bank, aliceId);

        // 5️⃣ Выполняем ещё одну операцию после создания снепшота
        System.out.println("\n>>>> Выполняем перевод 150 от Алисы к Бобу после создания снепшота");
        bank.transfer(aliceId, bobId, 150);
        printAccountInfo("✅ Баланс после перевода 150:", bank, aliceId, bobId);

        // 6️⃣ Восстановление с использованием снепшота (без проигрывания всех событий)
        System.out.println("\n>>>> Восстанавливаем состояние Алисы с использованием снепшота");
        double restoredBalance = bank.restoreFromSnapshot(aliceId);
        System.out.printf("✅ Баланс Alice после восстановления из снепшота (без повторного проигрывания всех событий): %.2f%n", restoredBalance);

        // 7️⃣ История транзакций
        printAuditLog("📜 История счета Алисы:", bank.getAuditLog(aliceId));
        printAuditLog("📜 История счета Боба:", bank.getAuditLog(bobId));

        // 8️⃣ Обнаружение подозрительных транзакций
        System.out.println("\n>>>> Проверка подозрительных транзакций");
        detectFraudulentTransactions(aliceId, bank);
        detectFraudulentTransactions(bobId, bank);
    }

    /**
     * 🏦 Вывод информации о балансе.
     */
    private static void printAccountInfo(String message, Bank bank, UUID... accountIds) {
        System.out.println("\n" + message);
        for (UUID accountId : accountIds) {
            double balance = bank.calculateBalance(accountId);
            System.out.printf("💰 ID: %s, Баланс: %.2f%n", accountId, balance);
        }
    }

    /**
     * 📜 Вывод истории событий.
     */
    private static void printAuditLog(String message, List<Event> events) {
        System.out.println("\n" + message);
        for (Event event : events) {
            System.out.printf("📌 Тип: %s, Сумма: %.2f, Дата: %s%n",
                    event.getType(), event.getAmount(), event.getDate());
        }
    }

    /**
     * 🔍 Обнаружение подозрительных транзакций (например, снятие больших сумм).
     */
    private static void detectFraudulentTransactions(UUID accountId, Bank bank) {
        List<Event> events = bank.getAuditLog(accountId);
        for (Event event : events) {
            if ("MoneyWithdrawn".equals(event.getType()) && event.getAmount() > 500) {
                System.out.printf("⚠️ Подозрительная транзакция: снятие %.2f со счёта %s%n",
                        event.getAmount(), accountId);
            }
        }
    }
}