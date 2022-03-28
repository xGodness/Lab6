package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.CountLessThanOscarsCountCommand;

@FunctionalInterface
public interface NewCountLessThanOscarsCountCmd extends AbstractNewCommand {
    CountLessThanOscarsCountCommand create(MoviesCollection moviesCollection);
}
