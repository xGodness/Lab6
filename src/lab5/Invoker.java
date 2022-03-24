package lab5;

import lab5.commands.CommandImpl;

public class Invoker {
    private MoviesCollection collection;
    private CommandImpl command;

    public Invoker(MoviesCollection collection) {
        this.collection = collection;
    }

    public void setCommand(CommandImpl command) {
        this.command = command;
    }

    public void executeCommand() {
//        command.execute();
    }

    public CommandImpl getCommand() {
        return command;
    }

}
