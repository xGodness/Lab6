package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import com.sun.istack.internal.NotNull;
import lab6.collection.MoviesCollectionImpl;

public interface CommandImpl {
    String execute(@NotNull MoviesCollectionImpl moviesCollection, Object[] args) throws CollectionException;
}
