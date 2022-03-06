package database;

public enum Furnish {
    DESIGNER("DESIGNER"),
    NONE("NONE"),
    BAD("BAD"),
    LITTLE("LITTLE");

    private final String name;
    Furnish (String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public static String show() {
        StringBuilder s = new StringBuilder("");
        for (Furnish furnish : Furnish.values()) {
            s.append(furnish.getName()).append(" ");
        }
        return s.toString();
    }

}