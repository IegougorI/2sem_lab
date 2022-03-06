package database;

public enum View {
    STREET("STREET"),
    YARD("YARD"),
    PARK("PARK"),
    GOOD("GOOD"),
    BAD("BAD");

    private final String name;

    View(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }



    public static String show() {
        StringBuilder s = new StringBuilder("");
        for (View view : View.values()) {
            s.append(view.getName()).append(" ");
        }
        return s.toString();
    }
}
