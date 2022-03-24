package lab5;

import lab5.exceptions.collection_exceptions.LoadCollectionException;
import lab5.exceptions.file_exceptions.CannotCreateFileException;
import lab5.exceptions.file_exceptions.FileAlreadyExistsException;
import lab5.exceptions.file_exceptions.FilePermissionException;
import lab5.exceptions.file_exceptions.InvalidFileNameException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {
    private IOManager ioManager;
    private MoviesCollection moviesCollection;
    private FileManager fileManager;
    private MovieBuilder movieBuilder;

    private String currentFileName;

    public Application() {
        this.ioManager = new IOManager();
        this.movieBuilder = new MovieBuilder(ioManager);
        this.fileManager = new FileManager(this);
        this.moviesCollection = null;
    }

    public void fileFetch(String fileName) {
        while (moviesCollection == null) {
            try {
                moviesCollection = fileManager.load(fileName);
                currentFileName = fileName;
                return;

            } catch (InvalidFileNameException | FilePermissionException | LoadCollectionException e) {
                ioManager.printlnErr(e.getMessage());
                fileName = ioManager.getNextInput(
                        "Specify file name: ");

            } catch (FileNotFoundException e) {
                ioManager.printlnErr(e.getMessage());
                boolean fileWasCreated = createBlankFile(fileName);
                if (fileWasCreated) {
                    moviesCollection = new MoviesCollection();
                    currentFileName = fileName;
                }
            }
        }
    }

    private boolean createBlankFile(String fileName) {
        String response = null;
        while (response == null
                || !(response.equals("y") || response.equals("Y") || response.equals("n") || response.equals("N"))) {

            response = ioManager.getNextInput(
                    "Would you like to create new blank file with such name? (y/n): ");
        }

        if (response.equals("y") || response.equals("Y")) {
            try {
                fileManager.create(fileName);
                return true;
            } catch (InvalidFileNameException | IOException | FileAlreadyExistsException | FilePermissionException | CannotCreateFileException e) {
                ioManager.printlnErr(e.getMessage());
            }
        }
        return false;
    }

    public boolean isStringValid(String string) {
        return ioManager.isStringValid(string);
    }

//    private void setFileName(String fileName) {
//        this.fileName = fileName;
//    }

}
