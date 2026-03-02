package saga;

class TransferMoneyCommand implements Command {
	private final BankAccount from;
	private final BankAccount to;
	private final int amount;

	public TransferMoneyCommand(BankAccount from, BankAccount to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public void execute() throws SagaException {
		from.transfer(to, amount);
		System.out.println("Transfered " + amount + " from " + from + " to " + to);
	}

	public void rollback() throws SagaException {
		System.out.println("Rollback: Transferring " + amount + " from " + to + " to " + from);
		to.transfer(from, amount);
	}

}
