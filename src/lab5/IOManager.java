package lab5;

import java.util.Scanner;

public class IOManager {
    private Scanner scanner;
    private StringValidator stringValidator;

    public IOManager() {
        scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public String getNextInput() {
        String input = null;
        while (input == null) {
            try {
                input = scanner.nextLine();
            } catch (Exception e) {
                printOut("Invalid input error. Try again: ");
            }
        }
        return input;
    }

    public String getNextInput(String preMessage) {
        printOut(preMessage);
        return getNextInput();
    }

    public void printOut(String message) {
        System.out.print(message);
    }
    public void printlnOut(String message) {
        System.out.println(message);
    }

    public boolean isStringValid(String string) {
        return stringValidator.stringValidation(string);
    }

    public boolean isNumericOnly(String string) {
        return stringValidator.numericValidation(string);
    }

    public boolean isSpacesOnly(String string) {
        return stringValidator.spacesOnlyValidation(string);
    }

    //todo
//    public void printErr(Exception e, String message) {
//        printlnOut(message);
//        System.err.println(e.getMessage());
//    }

}
