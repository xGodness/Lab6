package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.movie_classes.Movie;
import lab6.server.MoviesCollection;

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
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException {
        Movie result = moviesCollection.maxByScreenwriter();
        return result.toString();
    }
}
