package lab5.commands;

import lab5.IOManager;
import lab5.MovieBuilder;
import lab5.MoviesCollection;

public class AddCommand extends Command {
    private IOManager ioManager;
    private MovieBuilder movieBuilder;

    public AddCommand(MoviesCollection moviesCollection, IOManager ioManager) {
        super(moviesCollection);
        this.ioManager = ioManager;
    }

    @Override
    public void execute() {
        super.getMoviesCollection().addMovie(movieBuilder.generateMovie(ioManager));

    }

}