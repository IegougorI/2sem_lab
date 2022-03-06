package database;


import server.SaxMyParser;

import java.util.*;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FlatCollection {

    public ConcurrentHashMap getHashMap(){
        return linkedHashMap;
    }

    private SaxMyParser parser;
    public FlatCollection(ConcurrentHashMap<String, Flat> linkedHashMap){
        this.linkedHashMap=linkedHashMap;
    }

    private ConcurrentHashMap<String, Flat> linkedHashMap = new ConcurrentHashMap<String, Flat>();


    public String help(){
        return "All commands : " + Commands.show();
    }
    public String info() {
        return ("Type of collection is " + linkedHashMap.getClass().toString() + ". Size of collection is " + linkedHashMap.size());
    }

    public String insert(String key, Flat flat) {
        try {
            if (!linkedHashMap.containsKey(key)) {
                linkedHashMap.put(key, flat);
                linkedHashMap.get(key).setId(Console.getIdCounter());
                return("Element is inserted");
            } else return("Collection already has element with this key");
        } catch (NumberFormatException e) {
            return("Incorrect input! Try again.");
        }
    }

    public String remove(String key, String login){

        try {

            if ((linkedHashMap.containsKey(key))) {
                   if (login.equals(linkedHashMap.get(key).getOwner())) {
                    linkedHashMap.remove(key);
                    return ("Element was removed");
               }else return "You can not remove this";
            } else return "Element is empty";
        } catch (NumberFormatException e) {
            return("Incorrect input! Try again.");
        }
    }

    public String update(String stringID, Flat flat, String login){
        long id;
        String key = null;
        boolean check = false;
        try {
            id = Long.parseLong(stringID);
            Map<String, Flat> map = linkedHashMap;
            for (Map.Entry<String, Flat> entry : map.entrySet()) {
                if (Objects.equals(id, map.get(entry.getKey()).getId())) {
                    key = entry.getKey();
                    check = true;
                }
            }

            if (check) {
                if (login.equals(linkedHashMap.get(key).getOwner())){
                    linkedHashMap.replace(key, flat);
                    linkedHashMap.get(key).setId(id);
                    return("Element is updated");
                }else return "You can not update this";
            }else {
                return("There is no element with ID: " + stringID);
            }

        }catch(NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String clear(String login){
        if (login.equals("xml")) {
            linkedHashMap.clear();
//            xml.clear();
            return ("Collection is empty");
        }else return "You can not clear collection";
    }

    public String show(){
        Map map = linkedHashMap;
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> o1.getValue().getName().compareTo(o2.getValue().getName()));

        Map<String, Flat> sortedMap = new LinkedHashMap<String, Flat>();
        for (Map.Entry<String, Flat> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        String line = "";
        Set set = sortedMap.entrySet();
        for (Object element: set) {
            Map.Entry mapEntry = (Map.Entry) element;
            line += ("key: " + mapEntry.getKey() + " | " + mapEntry.getValue().toString()) + "\n";
        }
        return "LinkedHashMap initial content: \n" + line;
    }

    public String print_descending(){
        Map map = linkedHashMap;
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> o2.getValue().getName().compareTo(o1.getValue().getName()));

        Map<String, Flat> sortedMap = new LinkedHashMap<String, Flat>();
        for (Map.Entry<String, Flat> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        String line = "";
        Set set = sortedMap.entrySet();
        for (Object element: set) {
            Map.Entry mapEntry = (Map.Entry) element;
            line += ("key: " + mapEntry.getKey() + " | " + mapEntry.getValue().toString()) + "\n";
        }
        return "LinkedHashMap initial content: \n" + line;
    }

    public String remove_greater_key(String stringId, String login){
        long id;
        boolean check = false;
        try {
            int i=0;

            id = Long.parseLong(stringId);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ((linkedHashMap.get(mapEntry.getKey()).getId() > id) && (login.equals(linkedHashMap.get(mapEntry.getKey()).getOwner()))){
                    linkedHashMap.remove(mapEntry.getKey());
                    i++;
                    check = true;

                }
            }
            if (!check) {
                return ("There is no element with id greater than " + id);
            }
            return(i + " elements with id greater than " + id + " were deleted. If you can delete it");
        }catch (NumberFormatException e){
                return("Incorrect input! Try again.");
        }
    }

    public String remove_lower_key(String stringId, String login){
        long id;
        boolean check = false;
        try {
            int i=0;

            id = Long.parseLong(stringId);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ((linkedHashMap.get(mapEntry.getKey()).getId() < id) && (login.equals(linkedHashMap.get(mapEntry.getKey()).getOwner()))){
                    linkedHashMap.remove(mapEntry.getKey());
                    i++;
                    check = true;

                }
            }
            if (!check) {
                return ("There is no element with id greater than " + id);
            }
            return(i + " elements with id greater than " + id + " were deleted. If you can delete it");
        }catch (NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String count_greater_than_number_of_rooms(String stringNumber){
        long number;
        int count=0;
        try {
            number=Long.parseLong(stringNumber);
            Set set = linkedHashMap.entrySet();
            for (Object element : set){
                Map.Entry mapEntry = (Map.Entry) element;
                if (linkedHashMap.get(mapEntry.getKey()).getNumberOfRooms() > number){
                    count++;
                }
            }
            return ("There is: " + count + " elements with rooms greater than " + number);
        } catch (NumberFormatException e) {
            return("Incorrect input! Try again.");
        }
    }


    public String filter_less_than_view(String stView){
        Map map = linkedHashMap;
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());

        // Sorting the list based on values

        List<Map.Entry<String, Flat>> l = list.stream().filter(x -> x.getValue().getView().compareTo(View.valueOf(stView)) > 0 ).collect(Collectors.toList());

        Map<String, Flat> sortedMap = new LinkedHashMap<String, Flat>();
        for (Map.Entry<String, Flat> entry : l)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        String line = "";
        Set set = sortedMap.entrySet();
        for (Object element: set) {
            Map.Entry mapEntry = (Map.Entry) element;
            line += ("key: " + mapEntry.getKey() + " | " + mapEntry.getValue().toString()) + "\n";
        }
        return "LinkedHashMap initial content: \n" + line;
    }


}
