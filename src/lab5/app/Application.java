package lab5.app;

import lab5.IO.IOManager;
import lab5.collection.MoviesCollection;
import lab5.commands.Command;
import lab5.exceptions.collection_exceptions.CollectionException;
import lab5.exceptions.collection_exceptions.LoadCollectionException;
import lab5.exceptions.collection_exceptions.SaveCollectionException;
import lab5.exceptions.file_exceptions.CannotCreateFileException;
import lab5.exceptions.file_exceptions.FileAlreadyExistsException;
import lab5.exceptions.file_exceptions.FilePermissionException;
import lab5.exceptions.file_exceptions.InvalidFileNameException;
import lab5.movie_classes.Movie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Class-connector
 * responsible for whole system processes coordinating
 */


public class Application {
    private IOManager ioManager;
    private MoviesCollection moviesCollection;
    private FileManager fileManager;
    private ConsoleManager consoleManager;
    private Invoker invoker;

    private String currentFileName;

    /*________________________________________________________________________________________________________________
                                                    Constructor
    ________________________________________________________________________________________________________________*/

    public Application() {
        this.ioManager = new IOManager();
        this.moviesCollection = null;
        this.fileManager = new FileManager(this);
        this.invoker = new Invoker(this);
    }

    /*________________________________________________________________________________________________________________
                                               Load or save collection
    ________________________________________________________________________________________________________________*/

    public void loadCollection(String fileName) {
        currentFileName = fileName;
        boolean collectionWasLoaded = false;
        while (!collectionWasLoaded) {
//            ioManager.printlnStatus("Loading from \"" + currentFileName + "\"...");
            try {
                MoviesCollection rawMoviesCollection = fileManager.load(currentFileName);
                LinkedList<Movie> rawCollection = rawMoviesCollection.getCollection();
                LinkedList<Movie> wrongMovies = new LinkedList<>();
                for (int i = 0; i < rawCollection.size(); i++) {
                    if (wrongMovies.contains(rawCollection.get(i))) continue;
                    if (rawCollection.get(i).getId() < 1) {
                        wrongMovies.add(rawCollection.get(i));
                        continue;
                    }
                    for (int j = i + 1; j < rawCollection.size(); j++) {
                        if (wrongMovies.contains(rawCollection.get(j))) continue;
                        if (rawCollection.get(i).getId() == rawCollection.get(j).getId()) {
                            wrongMovies.add(rawCollection.get(j));
                        }
                    }
                }
                rawCollection.removeAll(wrongMovies);
                moviesCollection = rawMoviesCollection;

                moviesCollection.setup(this);
                ioManager.printlnSuccess("Collection has been loaded from \"" + currentFileName + "\"");
                collectionWasLoaded = true;
            } catch (InvalidFileNameException | FilePermissionException | LoadCollectionException e) {
                ioManager.printlnErr(e.getMessage());
                currentFileName = fetchFileName();
            } catch (FileNotFoundException e) {
                ioManager.printlnErr(e.getMessage());
                boolean fileWasCreated = createBlankFile(currentFileName);
                if (fileWasCreated) {
                    moviesCollection = new MoviesCollection();
                    moviesCollection.setup(this);
                    collectionWasLoaded = true;
                } else {
                    currentFileName = fetchFileName();
                }
            }
        }
    }

    public void saveCollection() {
        boolean collectionWasSaved = false;
        while (!collectionWasSaved) {
            ioManager.printlnStatus("Saving to \"" + currentFileName + "\"...");
            try {
                fileManager.save(moviesCollection, currentFileName);
//                ioManager.printlnSuccess("Collection has been saved to \"" + currentFileName + "\"");
                collectionWasSaved = true;
            } catch (InvalidFileNameException | FilePermissionException | SaveCollectionException e) {
                ioManager.printlnErr(e.getMessage());
                currentFileName = fetchFileName();
            } catch (FileNotFoundException e) {
                ioManager.printlnErr(e.getMessage());
                createBlankFile(currentFileName);
            }
        }
    }

    /*________________________________________________________________________________________________________________
                                                Auxiliary methods
    ________________________________________________________________________________________________________________*/

    private boolean createBlankFile(String fileName) {
        String response = null;
        while (response == null
                || !(response.equalsIgnoreCase("y")
                || response.equalsIgnoreCase("n"))) {

            response = ioManager.getNextInput(
                    "Would you like to create new blank file \"" + currentFileName + "\"? (y/n): ");
        }

        if (response.equalsIgnoreCase("y")) {
            try {
                fileManager.create(fileName);
                currentFileName = fileName;
                return true;
            } catch (
                    InvalidFileNameException | IOException | FileAlreadyExistsException
                            | FilePermissionException | CannotCreateFileException e) {
                ioManager.printlnErr(e.getMessage());
            }
        }
        return false;
    }

    private String fetchFileName() {
        return ioManager.getNextInput("Specify file name: ");
    }

    public boolean isStringValid(String string) {
        return ioManager.isStringValid(string);
    }



    /*________________________________________________________________________________________________________________
                                                     Initializers
    ________________________________________________________________________________________________________________*/

    public void consoleStart(String fileName) {
        if (moviesCollection == null) {
            ioManager.printlnErr("Collection not presented");
            return;
        }
        this.consoleManager = new ConsoleManager(this);
        consoleManager.execute();
    }




    /*________________________________________________________________________________________________________________
                                                        Getters
    ________________________________________________________________________________________________________________*/

    public IOManager getIoManager() {
        return ioManager;
    }

    public MoviesCollection getMoviesCollection() {
        return moviesCollection;
    }

    public FileManager getFileManager() {
        return fileManager;
    }


    /*________________________________________________________________________________________________________________
                                                   Execute command
    ________________________________________________________________________________________________________________*/

    public String executeCommand(Command command, String[] args) throws CollectionException {
        invoker.setCommand(command, args);
        return invoker.executeCommand();
    }

}
