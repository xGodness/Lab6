package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.RemoveLowerCommand;

@FunctionalInterface
public interface NewRemoveLowerCmd extends AbstractNewCommand {
    RemoveLowerCommand create(MoviesCollection moviesCollection);
}
