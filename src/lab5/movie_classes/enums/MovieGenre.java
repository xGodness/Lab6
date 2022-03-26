package lab5.movie_classes.enums;

public enum MovieGenre {
    ACTION("action"),
    WESTERN("western"),
    DRAMA("drama"),
    MUSICAL("musical"),
    SCIENCE_FICTION("sci-fi");

    private final String label;

    MovieGenre(String genre) {
        this.label = genre;
    }

    public static MovieGenre valueOfLabel(String label) {
        for (MovieGenre e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }

}
