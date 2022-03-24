package lab5.movie_classes;

import java.time.LocalDateTime;

public class Movie {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле может быть null
    private String tagline; //Строка не может быть пустой, Поле может быть null
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person screenwriter;

    public Movie(String name, Coordinates coordinates, LocalDateTime creationDate, Integer oscarsCount,
                 String tagline, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.tagline = tagline;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", tagline='" + tagline + '\'' +
                ", genre=" + genre +
                ", mpaaRating=" + mpaaRating +
                ", screenwriter=" + screenwriter +
                '}';
    }

    // Getters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public String getTagline() {
        return tagline;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }


}
