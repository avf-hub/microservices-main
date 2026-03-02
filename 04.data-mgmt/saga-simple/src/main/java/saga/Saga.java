package saga;

import java.util.ArrayList;
import java.util.List;

public class Saga {
	private final List<Command> commands;
	private int executedCommands;

	public Saga() {
		commands = new ArrayList<>();
	}

	public void addCommand(Command command) {
		commands.add(command);
	}

	public void execute() throws SagaException {
		executedCommands = 0;
		for (Command command : commands) {
			command.execute();
			executedCommands++;
		}
	}

	public void rollback() throws SagaException {
		for (int i = executedCommands - 1; i >= 0; i--) {
			commands.get(i).rollback();
		}
	}
}
