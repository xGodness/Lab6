package lab5.movie_classes.enums;

public enum EyeColor {
    GREEN("green"),
    RED("red"),
    WHITE("white"),
    BROWN("brown");

    private String label;

    EyeColor(String eyeColor) {
        this.label = eyeColor;
    }

    public static EyeColor valueOfLabel(String label) {
        for (EyeColor e : values()) {
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
