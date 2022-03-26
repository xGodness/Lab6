package lab5.commands;

import lab5.MoviesCollection;

public class SaveCommand extends Command {
    private String tag = "save";
    public String getTag() {
        return tag;
    }

    private String description = "SAVE --- saves collection to file";
    public String getDescription() {
        return description;
    }

    public SaveCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) {
        super.getMoviesCollection().addMovie();
        return "Collection has been saved";
    }


}
