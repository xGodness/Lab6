package lab5.movie_classes;

public class Location {
    private int x;
    private double y;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Location(int x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name=" + name +
                '}';
    }
}
