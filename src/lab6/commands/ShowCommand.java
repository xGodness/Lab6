package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollection;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.movie_classes.Movie;

public class ShowCommand extends Command {
    public static final String tag = "show";
    public static final String description = "SHOW ... shows all collection's elements";

    public ShowCommand() {
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
        StringBuilder result = new StringBuilder();
        for (Movie movie : moviesCollection.getCollection()) {
            result.append(movie.toString()).append("\n");
        }
        return result.toString().trim();
    }


}
