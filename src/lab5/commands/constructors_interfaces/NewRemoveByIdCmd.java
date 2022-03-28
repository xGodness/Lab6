package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.RemoveByIdCommand;

@FunctionalInterface
public interface NewRemoveByIdCmd extends AbstractNewCommand {
    RemoveByIdCommand create(MoviesCollection moviesCollection);
}
