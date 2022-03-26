package lab5.commands.constructors_interfaces;

import lab5.MoviesCollection;
import lab5.commands.RemoveHeadCommand;

@FunctionalInterface
public interface NewRemoveHeadCmd extends AbstractNewCommand {
    RemoveHeadCommand create(MoviesCollection moviesCollection);
}
