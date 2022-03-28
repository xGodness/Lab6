package lab5.commands;

import lab5.collection.MoviesCollection;

/**
 * Abstract command class
 */

public abstract class Command implements CommandImpl {
    public String description = "";
    private MoviesCollection moviesCollection;

    public Command(MoviesCollection moviesCollection) {
        this.moviesCollection = moviesCollection;
    }

    public String getTag() {
        return "";
    }

    public String getDescription() {
        return description;
    }

    public MoviesCollection getMoviesCollection() {
        return moviesCollection;
    }
}
