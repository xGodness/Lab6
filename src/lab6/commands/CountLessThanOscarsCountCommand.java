package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.collection.MoviesCollectionImpl;
import com.sun.istack.internal.NotNull;

public class CountLessThanOscarsCountCommand extends Command {
    public static final String tag = "count_less_than_oscars_count";
    public static final String description = "COUNT_LESS_THAN_OSCARS_COUNT [oscarsCount] ... returns amount of elements whose oscarsCount's value is smaller than given";

    public CountLessThanOscarsCountCommand() {
        super();
    }

    public static String getTag() {
        return tag;
    }

    public static String getDescription() {
        return description;
    }

    @Override
    public String execute(@NotNull MoviesCollectionImpl moviesCollection, Object[] args) throws CollectionException {
        try {
            int value = Integer.parseInt((String) args[0]);
            return Integer.valueOf(moviesCollection.countLessThanOscarsCount(value)).toString();
        } catch (RuntimeException e) {
            throw new CollectionException("Incorrect input. Integer expected");
        }
    }

}
