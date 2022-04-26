package lab6.commands;

import com.sun.istack.internal.NotNull;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.server.MoviesCollection;

public interface CommandImpl {
    String execute(@NotNull MoviesCollection moviesCollection, Object[] args) throws CollectionException;
}
