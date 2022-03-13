package lab5;

import lab5.movie_classes.Movie;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name = "collection")
public class MoviesCollection {

    private final String initDateTime;

    @XmlElement(name = "movie", type = Movie.class)
    private LinkedList<Movie> collection = new LinkedList<Movie>();

    private HashMap<Long, Movie> identificators;
    private HashSet<Long> usedIds;


    public MoviesCollection() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        initDateTime = dateFormatter.format(date);
    }


    public void addMovie() {


        Long id = generateNextId();
        identificators.put(id, movie);
        usedIds.add(id);
        collection.add(movie);
    }

    public LinkedList<Movie> getCollection() {
        return collection;
    }

    public String getInitDateTime() {
        return initDateTime;
    }

    public int getCollectionSize() {
        return collection.size();
    }

    private Long generateNextId() {
        Long id = Collections.max(usedIds);
        do {
            id++;
        } while (!usedIds.contains(id));
        return id;
    }

}
