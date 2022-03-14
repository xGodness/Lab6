package lab5.commands;

import lab5.IOManager;
import lab5.MoviesCollection;
import lab5.movie_classes.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class AddCommand extends Command {
    private IOManager ioManager;
    private String[] args;
    private int argsLenght;

    public AddCommand(MoviesCollection moviesCollection, IOManager ioManager, String[] args) {
        super(moviesCollection);
        this.ioManager = ioManager;
        this.args = args;
        argsLenght = args.length;
    }

    @Override
    public void execute() {
        Movie movie = new Movie();
        String input;

        /**
         * Movie name field fetch
         */
        String movieName = null;
        while (movieName == null) {
            input = ioManager.getNextInput("Specify movie name: ");
            if (ioManager.isStringValid(input)) {
                movieName = input;
            } else {
                ioManager.printlnOut("||| Incorrect input. Movie name must contain at least one alphanumeric or standard special character.");
            }
        }

        /**
         * Coordinates field fetch
         */
        Double x = null;
        Float y = null;
        while (x == null && y == null) {
            input = ioManager.getNextInput("Specify X (double) and Y (float) coordinates: ");
            try {
                String[] coords = input.split(" ");
                x = Double.parseDouble(coords[0]);
                y = Float.parseFloat(coords[1]);
            } catch (NumberFormatException e) {
                ioManager.printlnOut("||| Incorrect input. X coordinate must be double-type value, Y coordinate must be float-type value.");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        /**
         * Creation date field generation
         */
        LocalDateTime creationDate = LocalDateTime.now();

        /**
         * Oscars count field fetch
         */
        Integer oscarsCount = 0;
        while (oscarsCount < 1) {
            input = ioManager.getNextInput("Specify Oscar count: ");
            if (input.equals("")) {
                oscarsCount = null;
                break;
            }
            try {
                oscarsCount = Integer.parseInt(input);
                if (oscarsCount < 1) {
                    ioManager.printlnOut("||| Incorrect input. Oscars count must be positive integer or null.");
                }
            } catch (NumberFormatException e) {
                ioManager.printlnOut("||| Incorrect input. Oscars count must be positive integer or null.");
            }
        }

        /**
         * Tagline field fetch
         */
        String tagline = "";
        while (tagline.equals("")) {
            input = ioManager.getNextInput("Specify tagline: ");
            if (input.equals("")) {
                tagline = null;
                break;
            }
            if (ioManager.isStringValid(input)) {
                tagline = input;
            } else {
                ioManager.printlnOut("||| Incorrect input. Tagline must contain only standard characters or be a null.");
            }
        }

        /**
         * Genre field fetch
         */
        MovieGenre genre = null;
        while (genre == null) {
            input = ioManager.getNextInput("Specify movie genre (Action, Western, Drama, Musical or Sci-fi): ");
            try {
                genre = MovieGenre.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioManager.printlnOut("||| Incorrect input. Movie genre must be Action, Western, Drama, Musical or Sci-fi.");
            }
        }

        /**
         * Mpaa rating fetch
         */
        MpaaRating mpaaRating = null;
        while (mpaaRating == null) {
            input = ioManager.getNextInput("Specify MPAA rating (PG, PG_13, R): ").toLowerCase(Locale.ROOT);
            try {
                mpaaRating = MpaaRating.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioManager.printlnOut("||| Incorrect input. MPAA rating must be PG, PG_13 or R.");
            }
        }

        /**
         * Screenwriter name field fetch
         */
        String personName = null;
        while (personName == null) {
            input = ioManager.getNextInput("Specify screenwriter's name: ");
            if (ioManager.isStringValid(input)) {
                personName = input;
            } else {
                ioManager.printlnOut("||| Incorrect input. Screenwriter's name must contain only standard characters, at least one must be provided.");
            }
        }

        /**
         * Screenwriter's birthday field fetch
         */
        LocalDate date = null;
        int year, month, day;
        while (date == null) {
            input = ioManager.getNextInput("Specify screenwriter's date of birth (dd.mm.yyyy): ");
            if (input.equals("")) {
                break;
            }
            String[] birthDate = input.split(".");
            try {
                date = LocalDate.of(
                        Integer.parseInt(birthDate[2]),
                        Integer.parseInt(birthDate[1]),
                        Integer.parseInt(birthDate[0])
                );
            } catch (IllegalArgumentException | DateTimeException e) {
                ioManager.printlnOut("||| Incorrect input. Year, month and day must be valid integers.");
            }

        }

        /**
         * Screenwriter's eye color fetch
         */
        EyeColor eyeColor = null;
        while (eyeColor == null) {
            input = ioManager.getNextInput("Specify screenwriter's eye color (green, red, white or brown): ").toLowerCase(Locale.ROOT);
            try {
                eyeColor = EyeColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioManager.printlnOut("||| Incorrect input. Eye color must be green, red, white or brown.");
            }
        }

        /**
         * Screenwriter's hair color fetch
         */
        HairColor hairColor = null;
        while (hairColor == null) {
            input = ioManager.getNextInput("Specify screenwriter's hair color (blue, yellow or white): ").toLowerCase(Locale.ROOT);
            try {
                hairColor = HairColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioManager.printlnOut("||| Incorrect input. Eye color must be blue, yellow or white.");
            }
        }

        /**
         * Screenwriter's country fetch
         */
        Country nationality = null;
        while (nationality == null) {
            input = ioManager.getNextInput("Specify screenwriter's nationality (UK, USA, Vatican, South Korea, North Korea): ").toLowerCase(Locale.ROOT);
            try {
                nationality = Country.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioManager.printlnOut("||| Incorrect input. Nationality must be UK, USA, Vatican, South Korea or North Korea.");
            }
        }

        /**
         * Screenwriter's location fetch
         */
        Location location = null;
        while (location == null) {
            input = ioManager.getNextInput("Specify location's X (integer), Y (double) and name (string) values: ");
            if (input.equals("")) {
                break;
            }
            String[] locationValues = input.split(" ");
            try {
                int locationX = Integer.parseInt(locationValues[0]);
                double locationY = Double.parseDouble(locationValues[1]);
                String locationName = locationValues[2];
                if (ioManager.isStringValid(locationName)) {
                    ioManager.printlnOut("||| Incorrect input. X and Y must be integer and double, name must contain at least one standard character.");
                }
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e)
        }















        /**
         * Movie's name field fetch
         */
        if (argsLenght > 0 && ioManager.isStringValid(args[0])) {
            movieName = args[0];
        }
        while (movieName == null || !ioManager.isStringValid(movieName)) {
            ioManager.printlnOut("Movie name must contain at least one non-space symbol and can contain only alphanumeric characters or space symbols.");
            movieName = ioManager.getNextInput("Please re-enter the movie name: ");
        }
        movie.setName(movieName);

        /**
         * Movie's oscarCount field fetch
         */
        if (argsLenght > 1 && !ioManager.isSpacesOnly(args[1])) {
            if (ioManager.isNumericOnly(args[1])) {
                movie.setOscarsCount(Integer.valueOf(args[1]));
            } else {
                boolean oscarCountFetch = true;
                while (oscarCountFetch) {
                    ioManager.printlnOut("oscarCount field must be greater than or equal to 0 (null allowed).");
                    String input = ioManager.getNextInput("Please re-enter the Oscars count: ");
                    if (ioManager.isSpacesOnly(input)) {
                        oscarCountFetch = false;
                    } else if (ioManager.isNumericOnly(input)) {
                        oscarsCount = Integer.valueOf(input);
                        oscarCountFetch = false;
                    }
                }
            }
        }

        /**
         * Movie's tagline field fetch
         */
        if (argsLenght > 2) {
            if (ioManager.isSpacesOnly(args[2])) {

            }
            if (ioManager.isStringValid(args[2])) {
                tagline = args[2];
            } else {

            }

        }


    }

}
