package lab5.movie_classes;

import java.time.LocalDate;

public class Person {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDate birthday; //Поле может быть null
    private EyeColor eyeColor; //Поле не может быть null
    private HairColor hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле может быть null

    public Person(String name, LocalDate birthday, EyeColor eyeColor, HairColor hairColor, Country nationality, Location location) {
        this.name = name;
        this. birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

}
