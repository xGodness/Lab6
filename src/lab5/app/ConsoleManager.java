package lab5.app;

import lab5.IO.IOManager;
import lab5.commands.*;
import lab5.commands.constructors_interfaces.*;
import lab5.exceptions.collection_exceptions.CollectionException;
import lab5.exceptions.collection_exceptions.RecursionException;
import lab5.exceptions.file_exceptions.FileException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Parses commands and throws them to the invoker
 */


public class ConsoleManager {

    private Application application;
    private IOManager ioManager;

    private HashMap<String, AbstractNewCommand> commandsHashMap;
    private HashMap<String, String> descriptionsHashMap;


    private String fileName;

//    static {
//        CommandsFactory.registerCommand("add", AddCommand.class);
//        CommandsFactory.registerCommand("clear", ClearCommand.class);
//    }

    public ConsoleManager(Application application) {
        this.application = application;
        this.ioManager = application.getIoManager();

        commandsHashMap = new HashMap<>();
        descriptionsHashMap = new HashMap<>();

        NewAddCmd newAddCmd = AddCommand::new;
        NewAddIfMaxCmd newAddIfMaxCmd = AddIfMaxCommand::new;
        NewClearCmd newClearCmd = ClearCommand::new;
        NewCountLessThanOscarsCountCmd newCountLessThanOscarsCountCmd = CountLessThanOscarsCountCommand::new;
        NewFilterStartsWithTaglineCmd newFilterStartsWithTaglineCmd = FilterStartsWithTaglineCommand::new;
        NewInfoCmd newInfoCmd = InfoCommand::new;
        NewMaxByScreenwriterCmd newMaxByScreenwriterCmd = MaxByScreenwriterCommand::new;
        NewRemoveByIdCmd newRemoveByIdCmd = RemoveByIdCommand::new;
        NewRemoveHeadCmd newRemoveHeadCmd = RemoveHeadCommand::new;
        NewRemoveLowerCmd newRemoveLowerCmd = RemoveLowerCommand::new;
        NewSaveCmd newSaveCmd = SaveCommand::new;
        NewShowCmd newShowCmd = ShowCommand::new;
        NewUpdateCmd newUpdateCmd = UpdateCommand::new;

        commandsHashMap.put("add", newAddCmd);
        commandsHashMap.put("add_if_max", newAddIfMaxCmd);
        commandsHashMap.put("clear", newClearCmd);
        commandsHashMap.put("count_less_than_oscars_count", newCountLessThanOscarsCountCmd);
        commandsHashMap.put("filter_starts_with_tagline", newFilterStartsWithTaglineCmd);
        commandsHashMap.put("info", newInfoCmd);
        commandsHashMap.put("max_by_screenwriter", newMaxByScreenwriterCmd);
        commandsHashMap.put("remove_by_id", newRemoveByIdCmd);
        commandsHashMap.put("remove_head", newRemoveHeadCmd);
        commandsHashMap.put("remove_lower", newRemoveLowerCmd);
        commandsHashMap.put("save", newSaveCmd);
        commandsHashMap.put("show", newShowCmd);
        commandsHashMap.put("update", newUpdateCmd);

        descriptionsHashMap.put("help", "HELP ... provides help");
        descriptionsHashMap.put("exit", "EXIT ... closes program without saving");
        Command tmpCmd;
        for (AbstractNewCommand newCmdFunc : commandsHashMap.values()) {
            tmpCmd = newCmdFunc.create(application.getMoviesCollection());
            descriptionsHashMap.put(tmpCmd.getTag(), tmpCmd.getDescription());
        }
        tmpCmd = null;
        System.gc();
    }


    public void execute() {
        String input;
        String[] parsedInput;


        while (true) {
            input = ioManager.getNextInput("Type command (type \"help\" for help): ").toLowerCase(Locale.ROOT);
            parsedInput = input.split("\\s+");

            if (parsedInput.length == 0) {
                continue;
            }


            if (parsedInput[0].equals("help")) {
                for (String cmdDescription : descriptionsHashMap.values()) {
                    ioManager.printlnInfoFormat(cmdDescription.split("\\.\\.\\."));
                }
                continue;
            }

            if (parsedInput[0].equals("exit")) {
                ioManager.printlnStatus("Terminating program...");
                return;
            }

            if (parsedInput[0].equals("execute_script")) {
                ioManager.printlnStatus("Executing script...");
                if (parsedInput.length == 1) {
                    ioManager.printlnErr("Script file name wasn't specified");
                    continue;
                }
                HashSet<String> stack = new HashSet();
                stack.add(parsedInput[1]);
                try {
                    boolean code = executeScript(parsedInput[1], new HashSet<>());
                    if (!code) {
                        ioManager.printlnStatus("Terminating program...");
                        ioManager.clearScannerStack();
                        return;
                    }
                    ioManager.clearScannerStack();
                    continue;
                } catch (FileException | RecursionException | IOException e) {
                    ioManager.clearScannerStack();
                    ioManager.printlnErr(e.getMessage());
                    continue;
                }
            }

            if (commandsHashMap.containsKey(parsedInput[0])) {
//                Class<? extends Command> commandClass = CommandsFactory.getCommand(parsedInput[0]);
//                Command command = commandClass.newInstance(application.getMoviesCollection());
                Command command = commandsHashMap.get(parsedInput[0]).create(application.getMoviesCollection());
//                ioManager.printlnInfo(Arrays.toString(parsedInput));

                String[] cmdArgs = null;
                if (parsedInput.length > 1) {
                    cmdArgs = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
                }


                try {
                    ioManager.printlnSuccess(
                            application.executeCommand(command, cmdArgs)
                    );
                } catch (CollectionException e) {
                    ioManager.printlnErr(e.getMessage());
                }
            }

        }
    }

    public boolean executeScript(String fileName, HashSet<String> stackTrace) throws FileException, IOException, RecursionException {
        if (stackTrace.contains(fileName)) {
            throw new RecursionException("Recursion has been found");
        }
        File file = application.getFileManager().openFile(fileName);
        stackTrace.add(fileName);
        Scanner fileScanner = new Scanner(file).useDelimiter("\n");
        ioManager.pushScanner(fileScanner);
        String input;
        String[] parsedInput;
        while (fileScanner.hasNextLine()) {
            input = fileScanner.nextLine();
            System.out.println(input);
            parsedInput = input.split("\\s+");
            if (parsedInput.length == 0) {
                continue;
            }
            if (parsedInput[0].equals("help")) {
                for (String cmdDescription : descriptionsHashMap.values()) {
                    ioManager.printlnInfoFormat(cmdDescription.split("\\.\\.\\."));
                }
                continue;
            }
            if (parsedInput[0].equals("exit")) {
                ioManager.printlnStatus("Terminating program...");
                return false;
            }

            if (parsedInput[0].equals("execute_script")) {
                if (parsedInput.length == 1) {
                    ioManager.printlnErr("Script file name wasn't specified");
                    continue;
                }
                boolean statusCode = executeScript(parsedInput[1], stackTrace);
                if (!statusCode) {
                    return false;
                }
                continue;
            }

            if (commandsHashMap.containsKey(parsedInput[0])) {
                Command command = commandsHashMap.get(parsedInput[0]).create(application.getMoviesCollection());
                String[] cmdArgs = null;
                if (parsedInput.length > 1) {
                    cmdArgs = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
                }
                try {
                    ioManager.printlnSuccess(
                            application.executeCommand(command, cmdArgs)
                    );
                } catch (CollectionException e) {
                    ioManager.printlnErr(e.getMessage());
                }
            }

        }
        ioManager.popScanner();
        return true;
    }


}