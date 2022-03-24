package lab5.commands;

import lab5.MovieBuilder;
import lab5.MoviesCollection;
import lab5.exceptions.collection_exceptions.IdException;

public class RemoveByIdCommand extends Command {

    private MovieBuilder movieBuilder;

    public RemoveByIdCommand(MoviesCollection moviesCollection, MovieBuilder movieBuilder) {
        super(moviesCollection);
        this.movieBuilder = movieBuilder;
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

            getMoviesCollection().removeMovie(id);
            return "Movie was removed by id " + id;

        } catch (NumberFormatException e) {
            throw new IdException("Incorrect input. Positive integer expected");
        }
    }

}
