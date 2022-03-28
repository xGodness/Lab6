package lab5.commands;

import lab5.collection.MoviesCollection;

public class InfoCommand extends Command {
    private String tag = "info";
    private String description = "INFO ... provides information about collection";

    public InfoCommand(MoviesCollection collection) {
        super(collection);
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String execute(String[] args) {
        return
                "Collection type     : " + super.getMoviesCollection().getClass() + "\n" +
                        "Initialization date : " + super.getMoviesCollection().getInitDateTime() + "\n" +
                        "Collection size     : " + super.getMoviesCollection().getCollectionSize();
    }

}
