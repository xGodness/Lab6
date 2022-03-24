package lab5.commands;

import lab5.IOManager;
import lab5.MovieBuilder;
import lab5.MoviesCollection;
import lab5.movie_classes.Movie;

public class AddCommand extends Command {

    private IOManager ioManager;
    private MovieBuilder movieBuilder;

    public AddCommand(MoviesCollection moviesCollection, MovieBuilder movieBuilder) {
        super(moviesCollection);
        this.movieBuilder = movieBuilder;
    }

    @Override
    public String execute(String[] args) {
        Movie movie = movieBuilder.buildMovie();
        super.getMoviesCollection().addMovie(movie);
        return "Movie was added ( " + movie + ")";
    }

}