package lab5.commands;

import lab5.MoviesCollection;
import lab5.exceptions.collection_exceptions.IdException;

public class UpdateByIdCommand extends Command {
    private String tag = "update_by_id";
    public String getTag() {
        return tag;
    }

    private String description =
            "| UPDATE [id] {element}                      | updates element with given id\n                                                                 |";
    public String getDescription() {
        return description;
    }

    public UpdateByIdCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) throws IdException {
        if (args.length == 0) {
            throw new IdException("Cannot update by id because id wasn't specified");
        }
        try {
            Long id = Long.valueOf(args[0]);
            if (id <= 0) {
                throw new IdException("Incorrect input. Positive integer expected");
            }

            if (!getMoviesCollection().checkId(id)) {
                throw new IdException("Movie with such id doesn't exist");
            }

            getMoviesCollection().updateMovie(id);
            return "Movie with id " + id + " has been updated";


        } catch (NumberFormatException e) {
            throw new IdException("Incorrect input. Positive integer expected");
        }
    }

}
