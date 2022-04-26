package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.IdException;
import lab6.server.MoviesCollection;

public class RemoveByIdCommand extends Command {
    public static final String tag = "remove_by_id";
    public static final String description =
            "REMOVE_BY_ID [id] ... removes element with given id";

    public RemoveByIdCommand() {
        super();
    }

    public static String getTag() {
        return tag;
    }

    public static String getDescription() {
        return description;
    }

    @Override
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws IdException {
        try {
            Long id = Long.valueOf((String) args[0]);
            if (id <= 0) {
                throw new IdException("Incorrect input. Positive integer expected");
            }
            if (!moviesCollection.checkId(id)) {
                throw new IdException("Movie with such id doesn't exist");
            }
            moviesCollection.removeMovie(id);
            return "Movie with id " + id + "  has been removed";

        } catch (RuntimeException e) {
            throw new IdException("Incorrect input. Positive integer expected");
        }
    }

}
