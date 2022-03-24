package lab5;

import lab5.exceptions.collection_exceptions.LoadCollectionException;
import lab5.exceptions.file_exceptions.CannotCreateFileException;
import lab5.exceptions.file_exceptions.FileAlreadyExistsException;
import lab5.exceptions.file_exceptions.FilePermissionException;
import lab5.exceptions.file_exceptions.InvalidFileNameException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.*;

public class FileManager {
    private Application application;

    public FileManager(Application application) {
        this.application = application;
    }

    public MoviesCollection load(String fileName)
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, LoadCollectionException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File with such name wasn't found");
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new FilePermissionException("You don't have permission to read this file");
        }

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(fileStream);

        try {
            JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (MoviesCollection) unmarshaller.unmarshal(streamReader);
        } catch (JAXBException e) {
            throw new LoadCollectionException("Cannot load the file. Probably it has wrong extension or was damaged");
        }

    }

    public void create(String fileName)
            throws InvalidFileNameException, FileAlreadyExistsException, FilePermissionException, IOException, CannotCreateFileException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        if (!fileName.contains(".xml")) {
            fileName += ".xml";
        }

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

    public void save(MoviesCollection collection, String fileName)
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, JAXBException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException("File name is invalid");
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File with such name wasn't found");
        }

        if (!file.canWrite()) {
            throw new FilePermissionException("You do not have permission to save files in this directory");
        }

        PrintWriter printWriter = new PrintWriter(file);

        JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(collection, printWriter);

    }




}
