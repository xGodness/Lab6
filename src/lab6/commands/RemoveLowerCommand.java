package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.movie_classes.Movie;
import lab6.server.MoviesCollection;

public class RemoveLowerCommand extends Command {
    public static final String tag = "remove_lower";
    public static final String description = "REMOVE_LOWER {element} ... removes all collection's elements that are smaller than given element";

    public RemoveLowerCommand() {
        super();
    }

    public static String getTag() {
        return tag;
    }

    public static String getDescription() {
        return description;
    }

    @Override
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException {
        try {
            Movie movie = (Movie) args[0];
            moviesCollection.removeLower(movie);
            return "Lower elements have been removed";
        } catch (RuntimeException e) {
            throw new CollectionException("Movie was not specified");
        }
    }
}
