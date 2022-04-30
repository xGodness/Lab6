package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;
import lab6.movieclasses.Movie;

public class MaxByScreenwriterCommand extends Command {
    public static final String tag = "max_by_screenwriter";
    public static final String description = "MAX_BY_SCREENWRITER ... shows element with the biggest screenwriter field value";

    public MaxByScreenwriterCommand() {
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
        Movie result = moviesCollection.maxByScreenwriter();
        return result.toString();
    }
}
