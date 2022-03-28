package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.UpdateCommand;

@FunctionalInterface
public interface NewUpdateCmd extends AbstractNewCommand {
    UpdateCommand create(MoviesCollection moviesCollection);
}
