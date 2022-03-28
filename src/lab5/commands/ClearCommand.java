package lab5.commands;

import lab5.collection.MoviesCollection;

public class ClearCommand extends Command {
    private String tag = "clear";
    private String description = "CLEAR ... clears the collection";

    public ClearCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String execute(String[] args) {
        getMoviesCollection().clearCollection();
        return "Collection has been cleared";
    }

}