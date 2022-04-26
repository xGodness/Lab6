package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.server.MoviesCollection;

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
    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException {
        if (moviesCollection.getCollection().isEmpty()) throw new CollectionException("Collection is empty");
        return
                "Collection type     : " + moviesCollection.getCollection().getClass() + "\n" +
                        "Initialization date : " + moviesCollection.getInitDateTime() + "\n" +
                        "Collection size     : " + moviesCollection.getCollectionSize();
    }

}
