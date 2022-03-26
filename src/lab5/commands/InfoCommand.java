package lab5.commands;

import lab5.MoviesCollection;

public class InfoCommand extends Command {
    private String tag = "info";
    public String getTag() {
        return tag;
    }

    private String description =
            "| INFO                                       | provides information about current collection\n                                                 |";
    public String getDescription() {
        return description;
    }

    public InfoCommand(MoviesCollection collection) {
        super(collection);
    }

    @Override
    public String execute(String[] args) {
        return
                "Collection type     : " + super.getMoviesCollection().getClass() + "\n" +
                "Initialization date : " + super.getMoviesCollection().getInitDateTime() + "\n" +
                "Collection size     : " + super.getMoviesCollection().getCollectionSize();
    }

}
