package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollection;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.movie_classes.Movie;

import java.util.LinkedList;

public class FilterStartsWithTaglineCommand extends Command {
    public static final String tag = "filter_starts_with_tagline";
    public static final String description = "FILTER_STARTS_WITH_TAGLINE [tagline] ... shows element whose tagline field starts with given substring";

    public FilterStartsWithTaglineCommand() {
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
        if (moviesCollection.getCollectionSize() == 0) {
            throw new CollectionException("Collection is empty");
        }
        try {
            String tagline = (String) args[0];
            LinkedList<Movie> result = moviesCollection.startsWithTagline(tagline);
            if (result == null || result.size() == 0) {
                throw new CollectionException("None of the collection elements starts with given tagline");
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Movie movie : result) {
                stringBuilder.append(movie).append("\n");
            }
            return stringBuilder.toString();

        } catch (RuntimeException e) {
            throw new CollectionException("Tagline was not specified");
        }

    }
}
