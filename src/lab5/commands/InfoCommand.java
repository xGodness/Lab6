package lab5.commands;

import lab5.MoviesCollection;

public class InfoCommand extends Command {

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
