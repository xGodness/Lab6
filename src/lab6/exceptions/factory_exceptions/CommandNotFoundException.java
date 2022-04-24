package lab6.exceptions.factory_exceptions;

public class CommandNotFoundException extends FactoryException {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
