package lab5;

import lab5.exceptions.collection_exceptions.EmptyCollectionException;
import lab5.exceptions.collection_exceptions.IdException;
import lab5.movie_classes.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name = "collection")
public class MoviesCollection {

    @XmlElement(name = "movie", type = Movie.class)
    private LinkedList<Movie> collection = new LinkedList<Movie>();

    private final String initDateTime;
    private HashMap<Long, Movie> identificators;
    private HashSet<Long> usedIds;


    public MoviesCollection() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        initDateTime = dateFormatter.format(date);
    }

    public Movie removeHead() throws EmptyCollectionException {
        if (collection.isEmpty()) {
            throw new EmptyCollectionException("Collection is empty");
        }
        Movie headMovie = collection.getFirst();
        collection.remove(headMovie);

        return collection.getFirst();
    }

    public void clearCollection() {
        collection.clear();
        identificators.clear();
        usedIds.clear();
    }

    public void addMovie(Movie movie) {
        Long id = generateNextId();
        movie.setId(id);
        identificators.put(id, movie);
        usedIds.add(id);
        collection.add(movie);
    }

    public void updateMovie(Long id, Movie movie) throws IdException {
        if (!checkId(id)) {
            throw new IdException("Movie with id " + id + " doesn't exist");
        }
        identificators.replace(id, movie);
    }

    public void removeMovie(Long id) throws IdException {
        if (!checkId(id)) {
            throw new IdException("Movie with id " + id + " doesn't exist");
        }
        identificators.remove(id);
        usedIds.remove(id);
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

    public boolean checkId(Long id) {
        return usedIds.contains(id);
    }

    private Long generateNextId() {
        Long id;
        if (usedIds.isEmpty()) {
            id = 1L;
        } else {
            id = Collections.max(usedIds);
            do {
                id++;
            } while (!usedIds.contains(id));
        }
        return id;
    }

}
