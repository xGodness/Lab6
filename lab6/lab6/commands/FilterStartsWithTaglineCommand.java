package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;
import lab6.movieclasses.Movie;

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
    public String execute(@NotNull MoviesCollectionImpl moviesCollection, Object[] args) throws CollectionException {
        if (moviesCollection.getCollectionSize() == 0) {
            throw new CollectionException("Collection is empty");
        }
        try {
            String tagline = (String) args[0];
            LinkedList<Movie> result = moviesCollection.startsWithTagline(tagline);
            if (result == null || result.size() == 0) {
                throw new CollectionException("None of the lab6.collection elements starts with given tagline");
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
