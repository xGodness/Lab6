package lab5.commands;

import lab5.IOManager;
import lab5.MoviesCollection;
import lab5.movie_classes.Movie;

public class ShowCommand extends Command {
    private IOManager ioManager;

    public ShowCommand(MoviesCollection moviesCollection, IOManager ioManager) {
        super(moviesCollection);
        this.ioManager = ioManager;
    }

    @Override
    public void execute() {
        MoviesCollection moviesCollection = super.getMoviesCollection();
        for (Movie movie : moviesCollection.getCollection()) {
            ioManager.printlnOut(movie.toString());
        }

    }


}
