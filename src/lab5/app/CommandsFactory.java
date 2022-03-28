package lab5.app;

import lab5.commands.Command;
import lab5.exceptions.collection_exceptions.CollectionException;

import java.util.HashMap;

/**
 * NOT REALISED YET
 * commands factory
 */

public class CommandsFactory {
    private static HashMap<String, Class<? extends Command>> registeredCommands = new HashMap<>();


    public static void registerCommand(String tag, Class<? extends Command> _class) {
        registeredCommands.put(tag, _class);
    }

    public static String getDescription(String tag) throws CollectionException {
        if (registeredCommands.containsKey(tag)) {
            String description;
            Class<? extends Command> command = registeredCommands.get(tag);
            try {
                description = String.valueOf(command.getDeclaredField("description"));
            } catch (NoSuchFieldException | SecurityException e) {
                throw new CollectionException("Information about command is not available at the moment. Please try again later. *beep* *beep* *beep*");
            }
            return description;
        }
        throw new CollectionException("Such command doesn't exist");
    }

    public static Command getCommand(String tag) throws CollectionException {
        if (registeredCommands.containsKey(tag)) {
//            return registeredCommands.get(tag).newInstance();
        }
        throw new CollectionException("Such command doesn't exist");
    }

//    public static AbstractNewCommand getConstructor(String tag) throws NoSuchMethodException {
//        if (registeredCommands.containsKey(tag)) {
//            Constructor<? extends Command> constructor = registeredCommands.get(tag).get;
//        }
//    }

}
