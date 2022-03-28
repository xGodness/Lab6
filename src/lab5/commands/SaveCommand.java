package lab5.commands;

import lab5.collection.MoviesCollection;

public class SaveCommand extends Command {
    private String tag = "save";
    private String description = "SAVE ... saves collection to the file";

    public SaveCommand(MoviesCollection moviesCollection) {
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
        super.getMoviesCollection().saveCollection();
        return "Collection has been saved";
    }


}
