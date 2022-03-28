package lab5;

import lab5.app.Application;

public class Main {
    public static void main(String[] args) {
        String fileName = null;

        if (args.length != 0) {
            fileName = args[0];
        }

        Application application = new Application();
        application.loadCollection(fileName);

        application.consoleStart(fileName);


    }

}


/*
    private static MoviesCollection loadFile(String fileName) throws IOException {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\n");


        FileManager fileManager = new FileManager();
        MoviesCollection collection;

        while (true) {
            try {
                collection = fileManager.load(fileName);
                return collection;

            } catch (FileNotFoundException e) {
                System.out.println("File with such name was not found.");
                boolean fileWasCreated = createNewFile(fileName, inputScanner, fileManager);
                if (fileWasCreated) {
                    return new MoviesCollection();
                }
                System.out.print("Type XML-file name to load: ");
                fileName = getNextInput(inputScanner);

            } catch (InvalidFileNameException e) {
                printErr(e, "File name is invalid. Try another one");
                fileName = getNextInput(inputScanner);

            } catch (FilePermissionException e) {
                printErr(e, "You don't have permission to read this file. Try another one");
                fileName = getNextInput(inputScanner);

            } catch (JAXBException e) {
                printErr(e, "Cannot load the file. Probably it was damaged. Try another one");
                fileName = getNextInput(inputScanner);
            }
        }
    }



    private static void printErr(Exception e, String message) {
        System.out.println(message);
        System.out.print("Type XML-file name to load: ");
    }

    private static String getNextInput(Scanner scanner) {
        String input = null;
        while (input == null) {
            try {
                input = scanner.next();
            } catch (Exception e) {
                System.out.print("Invalid input error. Try again: ");
            }
        }
        return input;
    }



    private static boolean createNewFile(String fileName, Scanner scanner, FileManager fileManager) {
        String response = null;
        while (response == null
                || !( response.equals("y") || response.equals("Y") || response.equals("n") || response.equals("N") )) {

            System.out.print("Would you like to create one? (y/n): ");
            response = getNextInput(scanner);
        }
        if (response.equals("y") || response.equals("Y")) {
            try {
                fileManager.create(fileName);
                return true;
            } catch (InvalidFileNameException | IOException e) {
                System.out.println("File with such name cannot be created");
            } catch (FileAlreadyExistsException e) {
                System.out.println("File with such name already exists");
            } catch (FilePermissionException e) {
                System.out.println("You don't have permission to create files in this directory");
            } catch (CannotCreateFileException e) {
                System.out.println("An error occurred during creating the file");
            }
        }

        return false;
    }


    static Pattern validationPattern = Pattern.compile("[ \\wА-Яа-яЁё!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}\\\\~№]+");
    public static boolean isStringInvalid(String fileName) {
        if (fileName == null) {
            return true;
        }
        Matcher matcher = Main.validationPattern.matcher(fileName);
        if (matcher.find()) {
            return !fileName.substring(matcher.start(), matcher.end()).equals(fileName);
        }
        return true;
    }
*/
