package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.AddIfMaxCommand;

@FunctionalInterface
public interface NewAddIfMaxCmd extends AbstractNewCommand {
    AddIfMaxCommand create(MoviesCollection collection);
}
