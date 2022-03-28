package lab5.app;

import lab5.commands.CommandImpl;
import lab5.exceptions.collection_exceptions.CollectionException;

/**
 * Executes commands
 */

public class Invoker {
    private Application application;
    private CommandImpl command;
    private String[] args;

    public Invoker(Application application) {
        this.application = application;
    }

    public void setCommand(CommandImpl command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public String executeCommand() throws CollectionException {
        return command.execute(args);
    }

    public CommandImpl getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}
