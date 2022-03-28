package lab5.commands;

import lab5.collection.MoviesCollection;

public class AddCommand extends Command {
    public static final String description = "ADD {element} ... adds new element to collection";
    private String tag = "add";

    public AddCommand(MoviesCollection moviesCollection) {
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
        super.getMoviesCollection().addMovie();
        return "New movie has been added \n";
    }

}