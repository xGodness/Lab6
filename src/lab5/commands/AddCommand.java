package lab5.commands;

import lab5.IOManager;
import lab5.MoviesCollection;
import lab5.movie_classes.Movie;

public class AddCommand extends Command {
    private IOManager ioManager;
    private String[] args;
    private int argsLenght;

    public AddCommand(MoviesCollection moviesCollection, IOManager ioManager, String[] args) {
        super(moviesCollection);
        this.ioManager = ioManager;
        this.args = args;
        argsLenght = args.length;
    }

    @Override
    public void execute() {
        Movie movie = new Movie();
        String movieName = null;
        Integer oscarsCount = null;

        if (argsLenght > 0 && ioManager.isStringValid(args[0])) {
            movieName = args[0];
        }
        while (movieName == null || !ioManager.isStringValid(movieName)) {
            ioManager.printlnOut("Movie name must contain at least one non-space symbol and can contain only alphanumeric characters or space symbols.");
            movieName = ioManager.getNextInput("Please re-enter the movie name: ");
        }
        movie.setName(movieName);


        if (argsLenght > 1 && ioManager.isNumericOnly(args[1])) {
            oscarsCount = Integer.valueOf(args[1]);
        } else {
            if (argsLenght != 1)
        }
            if !(argsLenght == 1 || ioManager.isSpacesOnly(args[1]) {            // <--- assume null input

        }



    }

}
