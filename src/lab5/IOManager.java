package lab5;

import com.sun.org.apache.xerces.internal.impl.dv.xs.AnySimpleDV;

import java.util.Scanner;

public class IOManager {
    private Scanner scanner;
    private StringValidator stringValidator;

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\t\u001B[32m";
    private static final String ANSI_RESET = "\033[0m";

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

    public void printlnErr(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
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

}
