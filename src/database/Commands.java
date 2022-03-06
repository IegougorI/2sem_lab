package database;

public enum Commands {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    INSERT("insert key"),
    UPDATE("update id"),
    REMOVE("remove key"),
    EXECUTE("execute_script file"),
    EXIT("exit"),
    remove_greater_id("remove_greater_id id"),
    remove_lower_id("remove_lower_id id"),
    history("history"),
    count_greater_than_number_of_rooms("count_greater_than_number_of_rooms numberOfRooms"),
    filter_less_than_view("filter_less_than_view view"),
    print_descending("print_descending");



    private final String name;

    Commands(String name){
        this.name = name;
    }

    public static String show(){
        StringBuilder s = new StringBuilder("");
        for (Commands commands : Commands.values()) {
            s.append(commands.getName()).append(", ");
        }
        return s.deleteCharAt(s.length() - 2).toString();
    }

    public String getName() {
        return name;
    }
}
