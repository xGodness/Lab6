package lab5.commands.constructors_interfaces;

import lab5.MoviesCollection;
import lab5.commands.Command;

@FunctionalInterface
public interface AbstractNewCommand {
    Command create(MoviesCollection moviesCollection);
}
