package lab5.commands.constructors_interfaces;

import lab5.MovieBuilder;
import lab5.MoviesCollection;
import lab5.commands.UpdateByIdCommand;

@FunctionalInterface
public interface NewUpdateByIdCmd extends AbstractNewCommand {
    UpdateByIdCommand create(MoviesCollection moviesCollection);
}
