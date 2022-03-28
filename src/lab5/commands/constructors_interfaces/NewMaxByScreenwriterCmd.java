package lab5.commands.constructors_interfaces;

import lab5.collection.MoviesCollection;
import lab5.commands.MaxByScreenwriterCommand;

@FunctionalInterface
public interface NewMaxByScreenwriterCmd extends AbstractNewCommand {
    MaxByScreenwriterCommand create(MoviesCollection moviesCollection);
}
