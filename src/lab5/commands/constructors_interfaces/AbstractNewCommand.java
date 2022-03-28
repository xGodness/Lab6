package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.Command;

/**
 * Functional interface to access command's constructor
 */

@FunctionalInterface
public interface AbstractNewCommand {
    Command create(MoviesCollection moviesCollection);
}
