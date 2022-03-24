package lab5.commands;

import lab5.MovieBuilder;
import lab5.MoviesCollection;
import lab5.exceptions.collection_exceptions.IdException;
import lab5.movie_classes.Movie;

public class UpdateByIdCommand extends Command {

    private MovieBuilder movieBuilder;

    public UpdateByIdCommand(MoviesCollection moviesCollection, MovieBuilder movieBuilder) {
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

            Movie movie = movieBuilder.buildMovie();
            getMoviesCollection().updateMovie(id, movie);
            return "Movie was updated by id " + id + " ( " + movie.toString() + " )";


        } catch (NumberFormatException e) {
            throw new IdException("Incorrect input. Positive integer expected");
        }
    }

}
