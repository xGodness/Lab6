package lab5.movie_classes.enums;

public enum MpaaRating {
    PG("pg"),
    PG_13("pg_13"),
    R("r");

    private final String label;

    MpaaRating(String mpaaRating) {
        this.label = mpaaRating;
    }

    public static MpaaRating valueOfLabel(String label) {
        for (MpaaRating e : values()) {
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
