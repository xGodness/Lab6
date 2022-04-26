package lab6.client;

import lab6.IO.IOManager;
import lab6.commands.*;
import lab6.exceptions.collection_exceptions.RecursionException;
import lab6.exceptions.factory_exceptions.CommandNotFoundException;
import lab6.exceptions.factory_exceptions.FactoryException;
import lab6.exceptions.file_exceptions.FileException;
import lab6.movie_classes.Movie;
import lab6.request_response.Request;
import lab6.request_response.RequestType;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Connects IO with application.
 * Also generates new commands by user's input.
 * Static block loads all available commands to the class loader
 * and saves their descriptions to the sorted linked hash map.
 */
public class ConsoleManager {

    /* Contains all commands' description */
    private final static LinkedHashMap<String, String> descriptionsMap;

    static {
        /* Registering all available commands */
        try {
            CommandsFactory.registerCommand("add", AddCommand.class);
            CommandsFactory.registerCommand("clear", ClearCommand.class);
            CommandsFactory.registerCommand("add_if_max", AddIfMaxCommand.class);
            CommandsFactory.registerCommand("clear", ClearCommand.class);
            CommandsFactory.registerCommand("count_less_than_oscars_count", CountLessThanOscarsCountCommand.class);
            CommandsFactory.registerCommand("filter_starts_with_tagline", FilterStartsWithTaglineCommand.class);
            CommandsFactory.registerCommand("info", InfoCommand.class);
            CommandsFactory.registerCommand("max_by_screenwriter", MaxByScreenwriterCommand.class);
            CommandsFactory.registerCommand("remove_by_id", RemoveByIdCommand.class);
            CommandsFactory.registerCommand("remove_head", RemoveHeadCommand.class);
            CommandsFactory.registerCommand("remove_lower", RemoveLowerCommand.class);
            CommandsFactory.registerCommand("show", ShowCommand.class);
            CommandsFactory.registerCommand("update", UpdateCommand.class);
        } catch (CommandNotFoundException e) {
            e.printStackTrace();
        }

        /* Collecting all available command descriptions */
        HashMap<String, String> unsortedDescriptionsMap = new HashMap<>();
        unsortedDescriptionsMap.put("help", "HELP ... provides help");
        unsortedDescriptionsMap.put("exit", "EXIT ... saves collection and closes program");
        for (String tag : CommandsFactory.getAllRegisteredTags()) {
            try {
                unsortedDescriptionsMap.put(tag, CommandsFactory.getDescription(tag));
            } catch (CommandNotFoundException e) {
                e.printStackTrace();
            }
        }
        descriptionsMap = HashMapSorter.sortHashMap(unsortedDescriptionsMap);

    }

    //    private Application application;
    private IOManager ioManager;
    private MovieBuilder movieBuilder;
    private ClientFileManager fileManager;
    private HashSet<String> scriptStackTrace;
    private LinkedList<String> commandsWithMovieArg = new LinkedList<>();
    private LinkedList<String> commandsWithNotMovieArgs = new LinkedList<>();


    public ConsoleManager(IOManager ioManager) {
        this.ioManager = ioManager;
        movieBuilder = new MovieBuilder(ioManager);
        fileManager = new ClientFileManager(ioManager);
        scriptStackTrace = new HashSet<>();
        commandsWithMovieArg.add("add");
        commandsWithMovieArg.add("add_if_max");
        commandsWithMovieArg.add("remove_lower");
        commandsWithMovieArg.add("update");
        commandsWithNotMovieArgs.add("count_less_than_oscars_count");
        commandsWithNotMovieArgs.add("filter_starts_with_tagline");
        commandsWithNotMovieArgs.add("remove_by_id");
        commandsWithNotMovieArgs.add("update");
    }


    /**
     * Method that begins Console Manager iteration.
     */
    public Request execute() {
        if (ioManager.isScannerStackEmpty() && !scriptStackTrace.isEmpty()) {
            scriptStackTrace.clear();
        }

        String input;
        LinkedList<String> parsedInput = new LinkedList<>();
        String commandTag;

        input = ioManager.getNextInput("Type command (type \"help\" for help): ").toLowerCase(Locale.ROOT);

        for (String word : input.split("\\s+")) {
            parsedInput.add(word.trim());
        }

        if (parsedInput.size() == 0) {
            return null;
        }

        commandTag = parsedInput.pollFirst();

        switch (commandTag) {
            case ("help"):
                for (String cmdDescription : descriptionsMap.values()) {
                    ioManager.printlnInfoFormat(cmdDescription.split("\\.\\.\\."));
                }
                return null;
            case ("exit"):
                return new Request(RequestType.EXIT);
            case ("execute_script"):
                if (parsedInput.size() == 0) {
                    ioManager.printlnErr("Too few arguments");
                    return null;
                }
                ioManager.printlnStatus("Executing script...");
                try {
                    executeScript(parsedInput.getFirst());
                } catch (FileException | IOException | RecursionException e) {
                    ioManager.printlnErr(e.getMessage());
                    if (e instanceof RecursionException) {
                        scriptStackTrace.clear();
                        ioManager.printlnStatus("Terminating script execution...");
                        ioManager.clearScannerStack();
                        return null;
                    }
                }
            default:
                try {
                    Command command = CommandsFactory.getCommand(commandTag);
                    Object[] commandArgs = new Object[2];
                    if (commandsWithNotMovieArgs.contains(commandTag)) {
                        if (parsedInput.size() == 0) {
                            ioManager.printlnErr("Too few arguments");
                            return null;
                        }
                        commandArgs[0] = parsedInput.get(0);
                    }
                    if (commandsWithMovieArg.contains(commandTag)) {
                        Movie movie = movieBuilder.buildMovie();
                        if (commandArgs[0] == null) commandArgs[0] = movie;
                        else commandArgs[1] = movie;
                    }
                    return new Request(RequestType.EXECUTE_COMMAND, command, commandArgs);
                } catch (FactoryException e) {
//                    ioManager.printlnErr(e.getMessage());
                    return null;
                }


        }
    }


    /**
     * Method that executes scripts from file.
     *
     * @param fileName Name of the file contains script to execute
     * @throws FileException      Exception thrown if program could not access specified script file
     * @throws IOException        Exception thrown if IO manager had caught incorrect input
     * @throws RecursionException Exception thrown if recursion had been found
     */
    public void executeScript(String fileName) throws FileException, IOException, RecursionException {
        if (scriptStackTrace.contains(fileName)) {
            throw new RecursionException("Recursion has been found");
        }
        File file = fileManager.openFile(fileName);
        scriptStackTrace.add(fileName);
        Scanner fileScanner = new Scanner(file).useDelimiter("\n");
        ioManager.pushScanner(fileScanner);
//        String input;
//        String[] parsedInput;
//
//        while (fileScanner.hasNextLine()) {
//
//            input = fileScanner.nextLine();
//            ioManager.printlnStatus(">>> " + input);
//            parsedInput = input.split("\\s+");
//
//            if (parsedInput.length == 0) {
//                continue;
//            }
//
//            String commandTag = parsedInput[0].trim();
//
//            switch (commandTag) {
//
//                case ("help"):
//                    for (String cmdDescription : descriptionsMap.values()) {
//                        ioManager.printlnInfoFormat(cmdDescription.split("\\.\\.\\."));
//                    }
//                    break;
//
//                case ("exit"):
//                    return false;
//
//                case ("execute_script"):
//                    if (parsedInput.length == 1) {
//                        ioManager.printlnErr("Script file name wasn't specified");
//                        continue;
//                    }
//                    ioManager.printlnStatus("Executing script...");
//                    boolean statusCode = executeScript(parsedInput[1], stackTrace);
//                    if (!statusCode) {
//                        return false;
//                    }
//                    break;
//
//                default:
//                    try {
//                        Command command = CommandsFactory.getCommand(commandTag, application.getMoviesCollection());
//                        String[] commandArgs = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
//                        for (int i = 0; i < commandArgs.length; i++) {
//                            commandArgs[i] = commandArgs[i].trim();
//                        }
//                        ioManager.printlnSuccess(
//                                application.executeCommand(command, commandArgs)
//                        );
//                    } catch (CommandNotFoundException | CannotAccessCommandException | CollectionException e) {
//                        ioManager.printlnErr(e.getMessage());
//                    }
//                    break;
//            }
//
//        }
//        ioManager.popScanner();
//        return true;
    }

}