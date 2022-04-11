package lab5.app;

import lab5.collection.MoviesCollection;
import lab5.exceptions.collection_exceptions.LoadCollectionException;
import lab5.exceptions.collection_exceptions.SaveCollectionException;
import lab5.exceptions.file_exceptions.CannotCreateFileException;
import lab5.exceptions.file_exceptions.FileAlreadyExistsException;
import lab5.exceptions.file_exceptions.FilePermissionException;
import lab5.exceptions.file_exceptions.InvalidFileNameException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Manager that reads and saves data in the files.
 */


public class FileManager {
    private Application application;

    /**
     * The only constructor. Needs Application as argument because link to the connected application is necessary.
     *
     * @param application   Connected Application instance
     */
    public FileManager(Application application) {
        this.application = application;
    }

    /**
     * Method that loads collection from the XML-file.
     *
     * @param  fileName                     Name of the file to read collection from
     * @return                              Collection instance that created from the file
     * @throws InvalidFileNameException     Exception thrown if invalid file name was given
     * @throws FilePermissionException      Exception thrown if file is not accessible
     * @throws FileNotFoundException        Exception thrown if file was not found
     * @throws LoadCollectionException      Exception thrown if program could not parse the file and load collection from it
     */
    public MoviesCollection load(String fileName)
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, LoadCollectionException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        fileName = checkExtension(fileName);

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File with such name wasn't found");
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new FilePermissionException("You don't have permission to read or write this file");
        }

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(fileStream);

        try {
            JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (MoviesCollection) unmarshaller.unmarshal(streamReader);
        } catch (JAXBException e) {
            application.getIoManager().printlnStatus("Unable to parse file. Opening as empty...");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println("");
            printWriter.close();
            return new MoviesCollection();
        }

    }

    /**
     * Method that creates new file.
     *
     * @param  fileName                     Name of the file to create
     * @throws InvalidFileNameException     Exception thrown if invalid file name was given
     * @throws FilePermissionException      Exception thrown if file is not accessible
     * @throws FileNotFoundException        Exception thrown if file was not found
     * @throws LoadCollectionException      Exception thrown if program could not parse the file and load collection from it
     */
    public void create(String fileName)
            throws InvalidFileNameException, FileAlreadyExistsException, FilePermissionException, IOException, CannotCreateFileException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        fileName = checkExtension(fileName);

        File file = new File(fileName);

        if (file.exists()) {
            throw new FileAlreadyExistsException("File with such name already exists");
        }

        try {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new CannotCreateFileException("Cannot create the file");
            }
        } catch (SecurityException e) {
            throw new FilePermissionException("You do not have permission to create files in this directory");
        }

    }


    public boolean save(MoviesCollection collection, String fileName)
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, SaveCollectionException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        fileName = checkExtension(fileName);

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File with such name wasn't found");
        }

        if (!file.canWrite()) {
            throw new FilePermissionException("You do not have permission to save files in this directory");
        }

        PrintWriter printWriter = new PrintWriter(file);

        try {
            JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(collection, printWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new SaveCollectionException("Cannot save collection to the file. Probably file was damaged or doesn't exist");
        }


        return true;

    }


    public File openFile(String fileName) throws InvalidFileNameException, FilePermissionException, FileNotFoundException {
        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File with such name wasn't found");
        }
        if (!file.canRead()) {
            throw new FilePermissionException("You do not have permission to read this file");
        }
        return file;

    }


    private String checkExtension(String fileName) {
        if (!fileName.contains(".xml")) {
            return fileName + ".xml";
        }
        return fileName;
    }


}
