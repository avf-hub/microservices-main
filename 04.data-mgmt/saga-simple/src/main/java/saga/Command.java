package saga;

interface Command {
	void execute() throws SagaException;

	void rollback() throws SagaException;
}
