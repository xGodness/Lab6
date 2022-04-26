package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.EmptyCollectionException;
import lab6.movie_classes.Movie;
import lab6.server.MoviesCollection;

public class RemoveHeadCommand extends Command {
    public static final String tag = "remove_head";
    public static final String description = "REMOVE_HEAD ... shows first element in the collection and removes it";

    public RemoveHeadCommand() {
        super();
    }

    public static String getTag() {
        return tag;
    }

    public static String getDescription() {
        return description;
    }

    @Override
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws EmptyCollectionException {
        Movie movie = moviesCollection.removeHead();
        return movie.toString();
    }

}
