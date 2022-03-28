package lab5.IO;

import java.util.Scanner;

public class IOManager {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_RESET = "\033[0m";
    private Scanner scanner;
    private StringValidator stringValidator;

    public IOManager() {
        scanner = new Scanner(System.in).useDelimiter("\n");
        stringValidator = new StringValidator();
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

    public void printlnInfo(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public void printlnInfoFormat(String[] message) {
        if (message.length == 2) {
            System.out.format("%-80s", ANSI_YELLOW + message[0] + ANSI_RESET);
            System.out.format("%100s", ANSI_YELLOW + message[1] + ANSI_RESET + "\n");
        }
    }

    public void printlnSuccess(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public void printlnStatus(String message) {
        System.out.println("[STATUS] " + message);
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
