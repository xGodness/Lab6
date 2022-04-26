package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.movie_classes.Movie;
import lab6.server.MoviesCollection;

public class AddCommand extends Command {
    public static final String description = "ADD {element} ... adds new element to collection";
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
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException {
        try {
            Movie movie = (Movie) args[0];
            moviesCollection.addMovie(movie);
            return "New movie has been added";
        } catch (RuntimeException e) {
            throw new CollectionException("Movie was not specified");
        }
    }

}