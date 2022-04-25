package lab6.IO;

import java.util.Scanner;
import java.util.Stack;

/**
 * Input-Output Manager
 */

public class IOManager {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_RESET = "\033[0m";
    private final Scanner defaultScanner = new Scanner(System.in).useDelimiter("\n");
    private Scanner scanner;
    private StringValidator stringValidator;

    private Stack<Scanner> scannerStack = new Stack<>();

    public IOManager() {
        scanner = defaultScanner;
        stringValidator = new StringValidator();
    }

    public void pushScanner(Scanner scanner) {
        scannerStack.push(scanner);
        this.scanner = scanner;
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    // return true if empty
    public boolean popScanner() {
        scannerStack.pop();
        if (scannerStack.isEmpty()) {
            scanner = defaultScanner;
            return true;
        } else {
            scanner = scannerStack.peek();
            return false;
        }
    }

    public void clearScannerStack() {
        scannerStack.clear();
        scanner = defaultScanner;
    }

    public String getNextInput() {
        while (!scannerStack.isEmpty() && !scanner.hasNext()) {
            popScanner();
        }
        String input = null;
        while (input == null) {
            try {
                input = scanner.nextLine();
                if (!scannerStack.isEmpty()) {
                    printlnOut(input);
                }
            } catch (Exception e) {
                printlnOut("Invalid input error. Try again: ");
//                scanner = new Scanner(System.in).useDelimiter("\n");
            }
        }
        return input;
    }

    public String getNextInput(String preMessage) {
        if (scannerStack.isEmpty()) printOut(preMessage);
        return getNextInput();
    }

    public String redText(String message) {
        return ANSI_RED + message + ANSI_RESET;
    }

    public String yellowText(String message) {
        return ANSI_YELLOW + message + ANSI_RESET;
    }

    public String greenText(String message) {
        return ANSI_GREEN + message + ANSI_RESET;
    }

    public String statusText(String message) {
        return "[STATUS] " + message;
    }

    public void printOut(String message) {
        System.out.print(message);
    }

    public void printlnOut(String message) {
        System.out.println(message);
    }

    public void printlnErr(String message) {
        System.out.println(redText(message));
    }

    public void printlnInfo(String message) {
        System.out.println(yellowText(message));
    }

    public void printlnSuccess(String message) {
        System.out.println(greenText(message));
    }

    public void printlnInfoFormat(String[] message) {
        if (message.length == 2) {
            System.out.format("%-54s", yellowText(message[0]));
            System.out.format("%s", yellowText(message[1]) + "\n");
        }
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
