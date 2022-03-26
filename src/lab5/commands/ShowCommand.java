package lab5.commands;

import lab5.MoviesCollection;
import lab5.movie_classes.Movie;

public class ShowCommand extends Command {
    private String tag = "show";
    public String getTag() {
        return tag;
    }
    private String description =
            "| SHOW                                       | shows all collection's elements\n                                                               |";
    public String getDescription() {
        return description;
    }

    public ShowCommand(MoviesCollection moviesCollection) {
        super(moviesCollection);
    }

    @Override
    public String execute(String[] args) {
        MoviesCollection moviesCollection = super.getMoviesCollection();
        StringBuilder result = new StringBuilder();
        for (Movie movie : moviesCollection.getCollection()) {
            result.append(movie.toString()).append("\n");
        }
        return result.toString();
    }


}
