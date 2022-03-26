package lab5.commands;

import lab5.MoviesCollection;

public class AddCommand extends Command {
    private String tag = "add";
    public String getTag() {
        return tag;
    }

    private String description =
            "| ADD {element}                              | adds new element to collection\n                                                                |";
    public String getDescription() {
        return description;
    }

    public AddCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) {
        super.getMoviesCollection().addMovie();
        return "New movie has been added";
    }

}