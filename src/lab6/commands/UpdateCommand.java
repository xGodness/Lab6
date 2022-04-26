package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.exceptions.collection_exceptions.IdException;
import lab6.movie_classes.Movie;
import lab6.server.MoviesCollection;

public class UpdateCommand extends Command {
    public static final String tag = "update";
    public static final String description = "UPDATE [id] {element} ... updates element with given id";

    public UpdateCommand() {
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
            Long id = Long.valueOf((String) args[0]);
            if (id <= 0) {
                throw new IdException("Incorrect input. Positive integer expected");
            }
            if (!moviesCollection.checkId(id)) {
                throw new IdException("Movie with such id doesn't exist");
            }
            Movie movie = (Movie) args[1];
            moviesCollection.updateMovie(id, movie);
            return "Movie with id " + id + " has been updated";
        } catch (RuntimeException e) {
            throw new IdException("Incorrect input. Positive integer and movie expected");
        }
    }
}
