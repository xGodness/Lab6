package lab5.movie_classes;

public enum MovieGenre {
    ACTION("action"),
    WESTERN("western"),
    DRAMA("drama"),
    MUSICAL("musical"),
    SCIENCE_FICTION("sci-fi");

    private final String genre;

    MovieGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }

}
