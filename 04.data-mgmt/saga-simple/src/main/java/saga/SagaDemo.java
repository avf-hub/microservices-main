package saga;

public class SagaDemo {

	public static void main(String[] args) {
		BankAccount account1 = new BankAccount(1000);
		BankAccount account2 = new BankAccount(1000);
		BankAccount account3 = new BankAccount(1000);
		BankAccount account4 = new BankAccount(1000);
		System.out.println("--- State before Saga ---");
		printAccountsInfo(account1, account2, account3, account4);
		Saga saga = new Saga();
		saga.addCommand(new TransferMoneyCommand(account1, account2, 100));
		saga.addCommand(new TransferMoneyCommand(account3, account4, 2000));
		try {
			System.out.println("--- Executing Saga ---");
			saga.execute();
		} catch (SagaException e) {
			printAccountsInfo(account1, account2, account3, account4);
			System.out.println("Exception: " + e.getMessage());
			System.out.println("--- Saga rolled back ---");
			try {
				saga.rollback();
			} catch (SagaException se) {
				se.printStackTrace();
				System.out.println("Unable to roll back Saga");
			}
		}
		printAccountsInfo(account1, account2, account3, account4);
	}

	private static void printAccountsInfo(BankAccount... accounts) {
		int i = 1;
		for (BankAccount account : accounts) {
			System.out.println("account" + i + ": " + account);
			i++;
		}
	}

}
