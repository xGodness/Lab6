package lab5.commands;

import lab5.IOManager;

import java.util.LinkedList;

public class HelpCommand implements CommandImpl {
    private LinkedList<CommandImpl> commandsList;
    private IOManager ioManager;

    public HelpCommand(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public  void execute() {
        ioManager.printlnOut(
                " ______________________________________________________________________________________________________________________________________________" +
                "| HELP                                       | provides information about available commands\n                                                 |" +
                "| INFO                                       | provides information about current collection\n                                                 |" +
                "| SHOW                                       | shows all collection's elements\n                                                               |" +
                "| ADD [name] [oscarsCount] [tagline]         | adds new element to collection\n                                                                |" +
                "| UPDATE [id] {element}                      | updates element with given id\n                                                                 |" +
                "| REMOVE_BY_ID [id]                          | removes element with given id\n                                                                 |" +
                "| CLEAR                                      | clears collection\n                                                                             |" +
                "| SAVE                                       | saves collection to the file\n                                                                  |" +
                "| EXECUTE_SCRIPT [file_name]                 | executes script\n                                                                               |" +
                "| EXIT                                       | closes the program without saving\n                                                             |" +
                "| REMOVE_HEAD                                | shows first element in the collection and removes it\n                                          |" +
                "| ADD_IF_MAX {element}                       | adds new element in the collection if its value is greater than the collection's max element\n  |" +
                "| REMOVE_LOWER {element}                     | removes all elements lower than given  \n                                                       |" +
                "| MAX_BY_SCREENWRITER                        | shows any element of the collection that has max \"screenwriter\" field value\n                 |" +
                "| COUNT_LESS_THAN_OSCARS_COUNT [oscarsCount] | shows number of elements whose \"oscarCount\" field value is lower than given\n                 |" +
                "| FILTER_STARTS_WITH_TAGLINE [tagline]       | shows elements whose \"tagline\" field value starts with the given substring\n                  |" +
                "|____________________________________________|_________________________________________________________________________________________________|" );
    }

}

