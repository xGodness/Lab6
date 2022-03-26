package lab5.movie_classes;

public class Coordinates {
    private Double x; //Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Double x, Float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y= " + y + "}";
    }
}
