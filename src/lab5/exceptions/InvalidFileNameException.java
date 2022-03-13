package lab5.exceptions;

public class InvalidFileNameException extends FileException {
    public InvalidFileNameException() {
        super("File name is invalid");
    }
}
