package lab5.movie_classes.enums;

public enum Country {
    UNITED_KINGDOM("uk"),
    USA("usa"),
    VATICAN("vatican"),
    SOUTH_KOREA("south korea"),
    NORTH_KOREA("north korea");

    private String label;

    Country(String country) {
        this.label = country;
    }

    public static Country valueOfLabel(String label) {
        for (Country e : values()) {
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
