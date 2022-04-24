package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollection;
import lab6.exceptions.collection_exceptions.CollectionException;

public interface CommandImpl {
    String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException;
}
