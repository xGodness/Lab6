package lab5.commands.constructors_interfaces;

import lab5.MoviesCollection;
import lab5.commands.ClearCommand;

@FunctionalInterface
public interface NewClearCmd extends AbstractNewCommand {
    ClearCommand create(MoviesCollection moviesCollection);
}
