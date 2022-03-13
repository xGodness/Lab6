package lab5.commands;

import lab5.IOManager;
import lab5.MoviesCollection;

public class InfoCommand extends Command {
    private IOManager ioManager;

    public InfoCommand(MoviesCollection collection, IOManager ioManager) {
        super(collection);
        this.ioManager = ioManager;
    }

    @Override
    public void execute() {
        ioManager.printlnOut(
                "Collection type     : " + super.getMoviesCollection().getClass() + "\n" +
                "Initialization date : " + super.getMoviesCollection().getInitDateTime() + "\n" +
                "Collection size     : " + super.getMoviesCollection().getCollectionSize());
    }

}
