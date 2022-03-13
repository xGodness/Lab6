package lab5;

public class ConsoleManager {

    private boolean isActive = true;



    public void execute() {
        while (isActive) {
            System.out.print("Type command (type \"help\" for help): ");
        }
    }


}
