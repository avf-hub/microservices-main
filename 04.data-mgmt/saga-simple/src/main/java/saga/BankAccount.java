package saga;

class BankAccount {
	private int balance;

	public BankAccount(int balance) {
		this.balance = balance;
	}

	public void transfer(BankAccount to, int amount) throws SagaException {
		if (balance < amount) {
			throw new SagaException("cannot transfer: balance < amount: " + balance + " < " + amount);
		}
		balance -= amount;
		to.balance += amount;
	}

	@Override
	public String toString() {
		return "balance = " + balance;
	}
}
