package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.InfoCommand;

@FunctionalInterface
public interface NewInfoCmd extends AbstractNewCommand {
    InfoCommand create(MoviesCollection collection);
}
