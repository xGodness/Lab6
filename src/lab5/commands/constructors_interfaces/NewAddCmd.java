package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.AddCommand;

@FunctionalInterface
public interface NewAddCmd extends AbstractNewCommand {
    AddCommand create(MoviesCollection moviesCollection);
}
