package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.FilterStartsWithTaglineCommand;

@FunctionalInterface
public interface NewFilterStartsWithTaglineCmd extends AbstractNewCommand {
    FilterStartsWithTaglineCommand create(MoviesCollection moviesCollection);
}
