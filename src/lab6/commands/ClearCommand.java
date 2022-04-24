package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollection;

public class ClearCommand extends Command {
    public static final String tag = "clear";
    public static final String description = "CLEAR ... clears the collection";

    public ClearCommand() {
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
        moviesCollection.clearCollection();
        return "Collection has been cleared";
    }

}