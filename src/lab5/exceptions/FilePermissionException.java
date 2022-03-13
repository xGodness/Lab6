package lab5.exceptions;

public class FilePermissionException extends FileException {
    public FilePermissionException() {
        super("No permission to read, write or execute the file");
    }
}
