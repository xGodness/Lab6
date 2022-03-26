package lab5;

import lab5.commands.*;
import lab5.commands.constructors_interfaces.*;
import lab5.exceptions.collection_exceptions.CollectionException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class ConsoleManager {

    private MoviesCollection moviesCollection;
    private IOManager ioManager;
    private Invoker invoker;

    private HashMap<String, AbstractNewCommand> commandsHashMap;
    private HashMap<String, String> descriptionsHashMap;

    private String fileName;

    public ConsoleManager(MoviesCollection moviesCollection, String fileName) {
        this.moviesCollection = moviesCollection;
        this.ioManager = moviesCollection.getIOManager();
        this.invoker = new Invoker();
        this.fileName = fileName;

//        commandsMap = new Map<String, AbstractNewCommand>();
        commandsHashMap = new HashMap<String, AbstractNewCommand>();
        descriptionsHashMap = new HashMap<String, String>();

        NewAddCmd newAddCmd = AddCommand::new;
        NewClearCmd newClearCmd = ClearCommand::new;
//        NewHelpCmd newHelpCmd = HelpCommand::new;
        NewInfoCmd newInfoCmd = InfoCommand::new;
        NewRemoveByIdCmd newRemoveByIdCmd = RemoveByIdCommand::new;
        NewRemoveHeadCmd newRemoveHeadCmd = RemoveHeadCommand::new;
//        NewSaveCmd newSaveCmd = SaveCommand::new; todo
        NewShowCmd newShowCmd = ShowCommand::new;
        NewUpdateByIdCmd newUpdateByIdCmd = UpdateByIdCommand::new;

        commandsHashMap.put("add", newAddCmd);
        commandsHashMap.put("clear", newClearCmd);
//        commandsHashMap.put("help", newHelpCmd);
        commandsHashMap.put("info", newInfoCmd);
        commandsHashMap.put("remove_by_id", newRemoveByIdCmd);
        commandsHashMap.put("remove_head", newRemoveHeadCmd);
//        commandsHashMap.put("save", newSaveCmd); todo
        commandsHashMap.put("show", newShowCmd);
        commandsHashMap.put("update_by_id", newUpdateByIdCmd);

        Command tmpCmd;
        for (AbstractNewCommand newCmdFunc : commandsHashMap.values()) {
            tmpCmd = newCmdFunc.create(moviesCollection);
            descriptionsHashMap.put(tmpCmd.getTag(), tmpCmd.getDescription());
        }
        tmpCmd = null;
        System.gc();
    }



    public void execute() {
        String input;
        String[] parsedInput;

        boolean collectionWasSaved = false;

        while (true) {
            input = ioManager.getNextInput("Type command (type \"help\" for help): ").toLowerCase(Locale.ROOT);
            parsedInput = input.split(" ");

            if (parsedInput.length == 0) {
                continue;
            }

            if (parsedInput[0].equals("help")) {
                for (String cmdDescription : descriptionsHashMap.values()) {
                    ioManager.printlnHelp(cmdDescription);
                }
                continue;
            }

            if (parsedInput[0].equals("exit")) {
                ioManager.printlnOut("Terminating program...");
                return;
            }

            if (commandsHashMap.containsKey(parsedInput[0]))
            { //------------------------------------------------------------------------------------
                Command command = commandsHashMap.get(input).create(moviesCollection);
                String[] cmdArgs = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
                invoker.setCommand(command, cmdArgs);

                try {
                    ioManager.printlnSuccess(
                            invoker.executeCommand()
                    );
                } catch (CollectionException e) {
                    ioManager.printlnErr(e.getMessage());
                }
            } //------------------------------------------------------------------------------------

        }
    }

}