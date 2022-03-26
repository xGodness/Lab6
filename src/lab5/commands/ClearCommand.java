package lab5.commands;

import lab5.MoviesCollection;

public class ClearCommand extends Command {
    private String tag = "clear";
    public String getTag() {
        return tag;
    }

    private String description =
            "| CLEAR                                      | clears collection\n                                                                             |";
    public String getDescription() {
        return description;
    }

    public ClearCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) {
        getMoviesCollection().clearCollection();
        return "Collection has been cleared";
    }

}