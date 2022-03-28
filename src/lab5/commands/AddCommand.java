package lab5.commands;

import lab5.collection.MoviesCollection;

public class AddCommand extends Command {
    private String tag = "add";
    private String description = "ADD {element} ... adds new element to collection";

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