package database;

import java.io.File;
import java.io.FileReader;
import java.util.*;


public class Console {
    private static long idCounter = 1;


    private static void setID(Long x){
    idCounter=x;
    }

    private static synchronized long createID() {
        return (idCounter++);
    }

    public static long getIdCounter() {
        return idCounter;
    }

    public static long getStartID(FlatCollection collection){

        Map map = collection.getHashMap();
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());
        ArrayList<Long> idList = new ArrayList<Long>();
        for (Map.Entry<String, Flat> entry : list){
            idList.add(entry.getValue().getId());
        }
        long idCounter = idList.stream()
                .mapToLong(v->v)
                .max().orElseThrow(NoSuchElementException::new);
        setID(++idCounter);
        return  idCounter;
    }

    public static long getStartIDforExecute(FlatCollection collection){

        Map map = collection.getHashMap();
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());
        ArrayList<Long> idList = new ArrayList<Long>();
        for (Map.Entry<String, Flat> entry : list){
            idList.add(entry.getValue().getId());
        }
        long idCounter = idList.stream()
                .mapToLong(v->v)
                .max().orElseThrow(NoSuchElementException::new);
        setID(idCounter);
        return  idCounter;
    }

    public static FlatCollection startCollection(String nameFile, FlatCollection collection) throws Exception {
        FileReader fr = new FileReader(nameFile);
        File f = new File(nameFile);
        Scanner file = new Scanner(f, "Windows-1251");
        String line;
        while (file.hasNextLine()) {
            try {
                line = file.nextLine().trim();
                if (!line.equals("")) {
                    try {
                        String[] params = line.split(",");
                        String key = params[0];
                        String name = params[1];
                        long x = Long.parseLong(params[2]);
                        double y = Double.parseDouble(params[3]);
                        Coordinates coordinates = new Coordinates(x, y);
                        double area = Double.parseDouble(params[4]);
                        long numberOfRooms = Long.parseLong(params[5]);
                        Furnish furnish = Furnish.valueOf(params[6]);
                        View view = View.valueOf(params[7]);
                        Transport transport = Transport.valueOf(params[8]);
                        String houseName = params[9];
                        int year = Integer.parseInt(params[10]);
                        long numberOfLifts = Long.parseLong(params[11]);
                        House house = new House(houseName, year, numberOfLifts);
                        long id = Console.createID();
                        java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();
                        Flat flat = new Flat(name, coordinates, area, numberOfRooms,
                                furnish, view, transport, house, creationDate, id);
                        collection.insert(key, flat);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Incorrect input. Collection is empty");
                        break;
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Collection has been downloaded");
                break;
            }
        }
        return collection;
    }

    public static String executeFile(String nameFile, FlatCollection collection, String login) throws Exception{
        boolean executeExit = false;

        FileReader fr = new FileReader(nameFile);
        Scanner file = new Scanner(fr);
        String command;
        StringBuilder line = new StringBuilder();
        String[] commands;
        while (!executeExit) {
            try {
                commands = file.nextLine().trim().split(" ");
                command = commands[0];
            } catch (NoSuchElementException e) {
                line.append("\n" + "File has been executed");
                break;
            }
            try {
                String s;
                switch (command) {
                    case "help":
                        line.append("\n").append("All commands : ").append(Commands.show());
                        break;
                    case "info":
                        line.append("\n").append(collection.info());
                        break;
                    case "show":
                        line.append("\n").append(collection.show());
                        break;
                    case "insert":
                        Flat elem = Console.getElementFromFile(commands[2]);
                        s = collection.insert(commands[1], elem);
                        line.append("\n").append(s);
                        elem.setOwner(login);
                        if(s.equals("Element is inserted")) {
                            elem.setKey((commands[1]));
                        }
                        break;
                    case "update":
                        line.append("\n").append(collection.update(commands[1], Console.getElementFromFile(commands[2]), login));
                        break;
                    case "remove":
                        line.append("\n").append(collection.remove(commands[1], login));
                        break;
                    case "clear":
                        line.append("\n").append(collection.clear(login));
                        break;
                    case "execute_script":
                        line.append("\n" + "You can not execute script from file");
                        break;
                    case "exit":
                        line.append("\n" + "You can not closed program from file");
                        break;
                    case "remove_greater_key":
                        line.append("\n").append(collection.remove_greater_key(commands[1], login));
                        break;
                    case "remove_lower_key":
                        line.append("\n").append(collection.remove_lower_key(commands[1], login));
                        break;
                    case "count_greater_than_number_of_rooms":
                        line.append("\n").append(collection.count_greater_than_number_of_rooms(commands[1]));
                        break;
                    default:
                        line.append("\n").append("There is no command: ").append(command).append("\nUse 'help' to see all commands");
                        break;
                }
            }catch (ArrayIndexOutOfBoundsException e) {
                line.append("\n" + ("Incorrect input. Try againABOB."));
            }
        }
        fr.close();
        return line.toString();
    }

    public static Flat getElementFromFile(String element) {
        try {
            String[] params = element.split(",");



            String name = params[0];
            long x = Long.parseLong(params[1]);
            double y = Double.parseDouble(params[2]);
            Coordinates coordinates = new Coordinates(x, y);
            double area = Double.parseDouble(params[3]);
            long numberOfRooms = Long.parseLong(params[4]);
            Furnish furnish = Furnish.valueOf(params[5]);
            View view = View.valueOf(params[6]);
            Transport transport = Transport.valueOf(params[7]);
            String houseName = params[8];
            int year = Integer.parseInt(params[9]);
            long numberOfLifts = Long.parseLong(params[10]);
            House house = new House(houseName, year, numberOfLifts);
            long id = Console.createID();
            java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();
            Flat flat = new Flat(name, coordinates, area, numberOfRooms,
                    furnish, view, transport, house, creationDate, id);
            return flat;
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("incorrect element");
            return null;
        }
    }

    public static Flat getElement(Scanner scanner){
        System.out.println("New element");

        // initialisation a String name of element
        String name;
        while (true){
            System.out.println("Enter a name:");
            name=scanner.nextLine();
            if (!name.equals("")){
                break;
            }
            System.out.println("You failed. Try again!");
        }

        //initialisation a Coordinates coordinates of element\

        long x;
        while (true){
            System.out.println("Enter X coordinate(X is long): ");
            try {
                x=Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }

        Double y;
        while (true) {
            System.out.println("Enter Y Coordinate(Y is Double): ");
            try {
                y = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        //initialisation a Double area of element\
        Double area;
        while (true) {
            System.out.println("Enter an area (area is Double and 0.0<area<997.0): ");
            try {
                area = Double.parseDouble(scanner.nextLine());
                if (area < Flat.MaxArea & area > Flat.MinArea) {
                    break;
                } else {
                    System.out.println("Area is between 0.0 and 997.0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }

        //initialisation long numberOfRooms of element\
        long numberOfRooms;
        while (true){
            System.out.println("Enter number of rooms(number is long and between 0 and 13): ");
            try {
                numberOfRooms = Long.parseLong(scanner.nextLine());
                if (numberOfRooms>Flat.MinNumberOfRooms & numberOfRooms<Flat.MaxNumberOfRooms){
                    break;
                }
                else {
                    System.out.println("Number of rooms is between 0 and 13");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }

        //initialisation a Furnish furnish
        System.out.println("Choose type of furnish: " + Furnish.show());
        String tryFurnish = scanner.nextLine();
        Furnish furnish = null;
        while (furnish==null){
            try {
                furnish=Furnish.valueOf(tryFurnish);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such furnish: "+ tryFurnish + "\nTry again: \n" + Furnish.show());
            tryFurnish=scanner.nextLine();
            }
        }

        //initialisation a View view
        System.out.println("Choose type of view: " + View.show());
        String tryView = scanner.nextLine();
        View view = null;
        while (view==null){
            try {
                view=View.valueOf(tryView);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such view: "+ tryView + "\nTry again: \n" + View.show());
                tryView=scanner.nextLine();
            }
        }

        //initialisation a Transport transport
        System.out.println("Choose type of transport: " + Transport.show());
        String tryTransport = scanner.nextLine();
        Transport transport = null;
        while (transport==null){
            try {
                transport=Transport.valueOf(tryTransport);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such transport: "+ tryTransport + "\nTry again: \n" + Transport.show());
                tryTransport=scanner.nextLine();
            }
        }

        //initialisation a House of element
        String houseName;
        while (true){
            System.out.println("Enter Name of the house: ");
            try{
                houseName=scanner.nextLine();
                if (!houseName.equals("")){
                    break;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        int year;
        while (true) {
            System.out.println("Enter year of the house(year is Integer and between 0 and 720): ");
            try {
                year = Integer.parseInt(scanner.nextLine());
                if (year > House.MinYear & year < House.MaxYear) {
                    break;
                } else {
                    System.out.println("Year of the house is between 0 and 720");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        long numberOfLifts;
        while (true){
            System.out.println("Enter number of lifts in the house(Number is long and greater than 0): ");
            try {
                numberOfLifts=Long.parseLong(scanner.nextLine());
                if (numberOfLifts>House.MinNumberOfLifts){
                    break;
                }else {
                    System.out.println("Number of lifts greater than 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        House house = new House(houseName, year, numberOfLifts);

        //initialisation ID of element
        long id = Console.createID();

        java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();




        Flat flat = new Flat(name, coordinates, area, numberOfRooms,
                furnish, view, transport, house, creationDate, id);
        return flat;
    }
}
