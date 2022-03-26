package lab5.commands.constructors_interfaces;

import lab5.MoviesCollection;
import lab5.commands.ShowCommand;

@FunctionalInterface
public interface NewShowCmd extends AbstractNewCommand {
    ShowCommand create(MoviesCollection moviesCollection);
}