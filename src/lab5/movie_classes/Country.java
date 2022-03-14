package lab5.movie_classes;

public enum Country {
    UNITED_KINGDOM("uk"),
    USA("usa"),
    VATICAN("vatican"),
    SOUTH_KOREA("south korea"),
    NORTH_KOREA("north korea");

    private String country;

    Country(String country) {
        this.country = country;
    }

}
