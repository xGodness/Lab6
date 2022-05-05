package lab6.commands;

import lab6.exceptions.collectionexceptions.CollectionException;
import com.sun.istack.internal.NotNull;
import lab6.collection.CollectionManagerImpl;

public interface CommandImpl {
    String execute(@NotNull CollectionManagerImpl moviesCollection, Object[] args) throws CollectionException;
}
