package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;
import lab6.movieclasses.Movie;

public class RemoveLowerCommand extends Command {
    public static final String tag = "remove_lower";
    public static final String description = "REMOVE_LOWER {element} ... removes all lab6.collection's elements that are smaller than given element";

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
    public String execute(@NotNull MoviesCollectionImpl moviesCollection, Object[] args) throws CollectionException {
        try {
            Movie movie = (Movie) args[0];
            moviesCollection.removeLower(movie);
            return "Lower elements have been removed";
        } catch (RuntimeException e) {
            throw new CollectionException("Movie was not specified");
        }
    }
}
