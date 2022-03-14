package lab5.movie_classes;

public enum MpaaRating {
    PG("pg"),
    PG_13("pg_13"),
    R("r");

    private final String mpaaRating;

    MpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    @Override
    public String toString() {
        return mpaaRating;
    }

}
