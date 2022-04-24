// Legacy command

//package lab6.commands;
//
//import com.sun.istack.internal.NotNull;
//import lab6.collection.MoviesCollection;
//
//public class SaveCommand extends Command {
//    public static final String tag = "save";
//    public static final String description = "SAVE ... saves collection to the file";
//
//    public SaveCommand() {
//        super();
//    }
//
//    public static String getTag() {
//        return tag;
//    }
//
//    public static String getDescription() {
//        return description;
//    }
//
//    @Override
//    public String execute(@NotNull MoviesCollection moviesCollection, Object[] args) {
//        moviesCollection.saveCollection();
//        return "Collection has been saved";
//    }
//
//
//}
