package lab5;

import lab5.commands.Command;

import java.util.HashMap;

public class ConsoleManager {

    private IOManager ioManager;
    private boolean isActive = true;
    private HashMap<String, Command> commandsHashMap;

    public void execute() {
        while (isActive) {
            String input = ioManager.getNextInput("Type command (type \"help\" for help): ");

        }
    }

