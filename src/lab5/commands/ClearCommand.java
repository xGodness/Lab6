package lab5.commands;

import lab5.MoviesCollection;

public class ClearCommand extends Command {

    public ClearCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) {
        getMoviesCollection().clearCollection();
        return "Collection was cleared";
    }

}
