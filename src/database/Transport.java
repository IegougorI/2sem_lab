package database;

public enum Transport {
    ENOUGH("ENOUGH"),
    FEW("FEW"),
    LITTLE("LITTLE"),
    NONE("NONE"),
    NORMAL("NORMAL");

    private final String name;

    Transport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String show() {
        StringBuilder s = new StringBuilder("");
        for (Transport transport : Transport.values()) {
            s.append(transport.getName()).append(" ");
        }
        return s.toString();
    }
}