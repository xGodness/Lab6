package lab6.server;

import lab6.exceptions.collectionexceptions.SaveCollectionException;
import lab6.exceptions.collectionexceptions.CollectionException;
import lab6.exceptions.fileexceptions.*;
import lab6.collection.MoviesCollection;
import lab6.movieclasses.Movie;
import lab6.commands.Command;
import lab6.IO.IOManager;


import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * Class-connector. Operates all others classes presented in code.
 */


public class Application {
    private IOManager ioManager;
    private MoviesCollection moviesCollection;
    private ServerFileManager serverFileManager;
    private Invoker invoker;

    private String currentFileName;
    private boolean collectionWasLoaded = false;

    /*________________________________________________________________________________________________________________
                                                    Constructor
    ________________________________________________________________________________________________________________*/

    public Application() {
        this.ioManager = new IOManager();
        this.moviesCollection = null;
        this.serverFileManager = new ServerFileManager(this);
        this.invoker = new Invoker(this);
    }

    /*________________________________________________________________________________________________________________
                                               Load or save lab6.collection
    ________________________________________________________________________________________________________________*/

    /**
     * Loads lab6.collection from the file.
     * Will be running until lab6.collection is loaded.
     *
     * @param fileName Name of the file to load from
     * @return Runtime messages
     */
    public LinkedList<String> loadCollection(String fileName) throws InvalidFileNameException, FilePermissionException, FileNotFoundException {
        LinkedList<String> runtimeMessages = new LinkedList<>();
        currentFileName = fileName;
        MoviesCollection rawMoviesCollection = serverFileManager.load(currentFileName);
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
        if (wrongMovies.size() != 0) {
            runtimeMessages.add("Corrupted elements were found. Fixing the file...");
        }
        rawCollection.removeAll(wrongMovies);
        moviesCollection = rawMoviesCollection;

        moviesCollection.setup(this);
        collectionWasLoaded = true;

        runtimeMessages.add("Collection has been loaded from \"" + currentFileName + "\"");

        return runtimeMessages;
    }

    /**
     * Saves lab6.collection to the file application is currently working with.
     * File name should already been presented in Application class (field "currentFileName").
     *
     * @return Runtime messages
     */
    public LinkedList<String> saveCollection()
            throws FilePermissionException, InvalidFileNameException, SaveCollectionException, FileNotFoundException {

        LinkedList<String> runtimeMessages = new LinkedList<>();
        runtimeMessages.add("Saving to \"" + currentFileName + "\"...");
        serverFileManager.save(moviesCollection, currentFileName);
        runtimeMessages.add("Collection has been saved");
        return runtimeMessages;
    }


    /*________________________________________________________________________________________________________________
                                                Auxiliary methods
    ________________________________________________________________________________________________________________*/

    /**
     * Creates blank file if file with specified name does not exist.
     *
     * @param fileName Name of the file to create
     * @return "true" if file was created and "false" otherwise
     */
    public String createBlankFile(String fileName)
            throws InvalidFileNameException, FileAlreadyExistsException, FilePermissionException, CannotCreateFileException {

        serverFileManager.create(fileName);
        currentFileName = fileName;
        moviesCollection = new MoviesCollection();
        moviesCollection.setup(this);
        collectionWasLoaded = true;
        return "New file has been created";
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






    /*________________________________________________________________________________________________________________
                                                        Getters
    ________________________________________________________________________________________________________________*/

    /**
     * @return Application's IOManager
     */
    public IOManager getIoManager() {
        return ioManager;
    }

    /**
     * @return Application's MoviesCollection
     */
    public MoviesCollection getMoviesCollection() {
        return moviesCollection;
    }

    /**
     * @return Application's FileManager
     */
    public ServerFileManager getFileManager() {
        return serverFileManager;
    }


    /*________________________________________________________________________________________________________________
                                                   Execute command
    ________________________________________________________________________________________________________________*/

    /**
     * Sets command to invoker and makes it to execute.
     *
     * @param command Command to execute
     * @param args    Command arguments (necessary, can be null)
     * @return Execution result
     * @throws CollectionException Exception thrown during executing the command
     */
    public String executeCommand(Command command, Object[] args) throws CollectionException {
        System.out.println(args);
        invoker.setCommand(command, args);
        return invoker.executeCommand();
    }

}
