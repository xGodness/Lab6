package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;
import lab6.movieclasses.Movie;

public class AddCommand extends Command {
    public static final String description = "ADD {element} ... adds new element to lab6.collection";
    public static final String tag = "add";

    public AddCommand() {
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
            moviesCollection.addMovie(movie);
            return "New movie has been added";
        } catch (RuntimeException e) {
            throw new CollectionException("Movie was not specified");
        }
    }

}