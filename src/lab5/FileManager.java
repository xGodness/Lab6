package lab5;

import lab5.exceptions.CannotCreateFileException;
import lab5.exceptions.FileAlreadyExistsException;
import lab5.exceptions.FilePermissionException;
import lab5.exceptions.InvalidFileNameException;

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
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, JAXBException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException();
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new FilePermissionException();
        }

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader streamReader = new InputStreamReader(fileStream);

        JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (MoviesCollection) unmarshaller.unmarshal(streamReader);

    }

    public void create(String fileName)
            throws InvalidFileNameException, FileAlreadyExistsException, FilePermissionException, IOException, CannotCreateFileException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException();
        }

        if (!fileName.contains(".xml")) {
            fileName += ".xml";
        }

        File file = new File(fileName);

        if (file.exists()) {
            throw new FileAlreadyExistsException();
        }

        try {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                throw new CannotCreateFileException();
            }
        } catch (SecurityException e) {
            throw new FilePermissionException();
        }

    }

    public void save(MoviesCollection collection, String fileName)
            throws InvalidFileNameException, FilePermissionException, FileNotFoundException, JAXBException {

        if (!application.isStringValid(fileName)) {
            throw new InvalidFileNameException();
        }

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        if (!file.canWrite()) {
            throw new FilePermissionException();
        }

        PrintWriter printWriter = new PrintWriter(file);

        JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(collection, printWriter);

    }




}
