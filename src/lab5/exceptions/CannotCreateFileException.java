package lab5.exceptions;

public class CannotCreateFileException extends FileException {
    public CannotCreateFileException() {
        super("Cannot create such file");
    }
}
