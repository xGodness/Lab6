package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollection;

public class InfoCommand extends Command {
    public static final String tag = "info";
    public static final String description = "INFO ... provides information about collection";

    public InfoCommand() {
        super();
    }

    public static String getTag() {
        return tag;
    }

    public static String getDescription() {
        return description;
    }

    @Override
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) {
        return
                        "Collection type     : " + moviesCollection.getCollection().getClass() + "\n" +
                        "Initialization date : " + moviesCollection.getInitDateTime() + "\n" +
                        "Collection size     : " + moviesCollection.getCollectionSize();
    }

}
