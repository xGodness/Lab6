package lab5.exceptions;

public class FileAlreadyExistsException extends FileException {
    public FileAlreadyExistsException() {
        super("The file with such name already exists");
    }
}
