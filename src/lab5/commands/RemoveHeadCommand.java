package lab5.commands;

import lab5.MoviesCollection;
import lab5.exceptions.collection_exceptions.EmptyCollectionException;
import lab5.movie_classes.Movie;

public class RemoveHeadCommand extends Command {

    public RemoveHeadCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) throws EmptyCollectionException {
        Movie movie = getMoviesCollection().removeHead();
        return movie.toString();
    }

}
