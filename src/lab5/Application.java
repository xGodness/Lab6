package lab5;

import lab5.exceptions.CannotCreateFileException;
import lab5.exceptions.FileAlreadyExistsException;
import lab5.exceptions.FilePermissionException;
import lab5.exceptions.InvalidFileNameException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {
    private IOManager ioManager;
    private MoviesCollection moviesCollection;
    private FileManager fileManager;

    private String currentFileName;

    public Application() {
        this.ioManager = new IOManager();
        this.fileManager = new FileManager(this);
        this.moviesCollection = null;
    }

    public void fileFetch(String fileName) {
        while (moviesCollection == null) {
            try {
                moviesCollection = fileManager.load(fileName);
                currentFileName = fileName;
                return;
            } catch (InvalidFileNameException e) {
                fileName = ioManager.getNextInput(
                        "File name is invalid. Try another one: ");

            } catch (FilePermissionException e) {
                fileName = ioManager.getNextInput(
                        "You don't have permission to read this file. Try another one: ");


            } catch (JAXBException e) {
                fileName = ioManager.getNextInput(
                        "Cannot load the file. Probably it has wrong extension or was damaged. Try another one: ");

            } catch (FileNotFoundException e) {
                ioManager.printlnOut("File with such name was not found.");
                boolean fileWasCreated = createBlankFile(fileName);
                if (fileWasCreated) {
                    moviesCollection = new MoviesCollection();
                    currentFileName = fileName;
                    return;
                }
            }
        }
    }

    private boolean createBlankFile(String fileName) {
        String response = null;
        while (response == null
                || !(response.equals("y") || response.equals("Y") || response.equals("n") || response.equals("N"))) {

            response = ioManager.getNextInput("Would you like to create new blank file with such name? (y/n): ");
        }

        if (response.equals("y") || response.equals("Y")) {
            try {
                fileManager.create(fileName);
                return true;
            } catch (InvalidFileNameException | IOException e) {
                ioManager.printlnOut("File with such name cannot be created");
            } catch (FileAlreadyExistsException e) {
                ioManager.printlnOut("File with such name already exists");
            } catch (FilePermissionException e) {
                ioManager.printlnOut("You don't have permission to create files in this directory");
            } catch (CannotCreateFileException e) {
                ioManager.printlnOut("An error occurred during creating the file");
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
