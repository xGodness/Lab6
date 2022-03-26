package lab5.commands;

import lab5.MoviesCollection;

public abstract class Command implements CommandImpl {
    public String getTag() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    private MoviesCollection moviesCollection;

    public Command(MoviesCollection moviesCollection) {
        this.moviesCollection = moviesCollection;
    }

    public MoviesCollection getMoviesCollection() {
        return moviesCollection;
    }
}
