package lab5.commands;

import lab5.collection.MoviesCollection;
import lab5.exceptions.collection_exceptions.CollectionException;
import lab5.movie_classes.Movie;

public class MaxByScreenwriterCommand extends Command {
    private String tag = "max_by_screenwriter";
    private String description = "MAX_BY_SCREENWRITER ... shows element with the biggest screenwriter field value";

    public MaxByScreenwriterCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String execute(String[] args) throws CollectionException {
        Movie result = getMoviesCollection().maxByScreenwriter();
        if (result == null) {
            throw new CollectionException("Collection is empty");
        }
        return result.toString();
    }
}
