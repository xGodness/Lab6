package lab5.commands;

import lab5.MoviesCollection;
import lab5.exceptions.collection_exceptions.IdException;

public class RemoveByIdCommand extends Command {
    private String tag = "remove_by_id";
    public String getTag() {
        return tag;
    }

    private String description =
            "| REMOVE_BY_ID [id]                          | removes element with given id\n                                                                 |";
    public String getDescription() {
        return description;
    }

    public RemoveByIdCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) throws IdException {
        if (args.length == 0) {
            throw new IdException("Cannot remove by id because id wasn't specified");
        }
        try {
            Long id = Long.valueOf(args[0]);
            if (id <= 0) {
                throw new IdException("Incorrect input. Positive integer expected");
            }

            if (!getMoviesCollection().checkId(id)) {
                throw new IdException("Movie with such id doesn't exist");
            }

            getMoviesCollection().removeMovie(id);
            return "Movie with id " + id + "  has been removed";

        } catch (NumberFormatException e) {
            throw new IdException("Incorrect input. Positive integer expected");
        }
    }

}
