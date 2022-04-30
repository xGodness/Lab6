package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;
import lab6.movieclasses.Movie;

public class AddIfMaxCommand extends Command {
    public static final String tag = "add_if_max";
    public static final String description = "ADD_IF_MAX {element} ... adds new element to lab6.collection if it's value is bigger than biggest element in lab6.collection";

    public AddIfMaxCommand() {
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
            return moviesCollection.addIfMax(movie) ? "New movie has been added" : "Specified movie has not added";
        } catch (RuntimeException e) {
            throw new CollectionException("Movie was not specified");
        }
    }

}
