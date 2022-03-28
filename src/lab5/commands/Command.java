package lab5.commands;

import lab5.collection.MoviesCollection;

public abstract class Command implements CommandImpl {
    private MoviesCollection moviesCollection;

    public Command(MoviesCollection moviesCollection) {
        this.moviesCollection = moviesCollection;
    }

    public String getTag() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public MoviesCollection getMoviesCollection() {
        return moviesCollection;
    }
}
