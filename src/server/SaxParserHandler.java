package server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.concurrent.ConcurrentHashMap;
import database.*;

public class SaxParserHandler extends DefaultHandler {
    private static final String TAG_FLAT = "flat";
    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";

    private static final String TAG_COORDINATES = "coordinates";
    private static final String TAG_X = "x";
    private static final String TAG_Y = "y";

    private static final String TAG_AREA = "area";
    private static final String TAG_NUMBEROFROOMS = "numberOfRooms";
    private static final String TAG_FURNISH = "furnish";
    private static final String TAG_VIEW = "view";
    private static final String TAG_TRANSPORT = "transport";

    private static final String TAG_HOUSE = "house";
    private static final String TAG_HOUSENAME = "houseName";
    private static final String TAG_YEAR = "year";
    private static final String TAG_NUMBEROFLIFTS = "numberOfLifts";

    private static final String TAG_CREATIONDATE = "creationDate";
    private static final String TAG_OWNER = "owner";
    private static final String TAG_ID = "id";

    private boolean isFlat = false;
    private boolean isCoordinates = false;
    private boolean isHouse = false;

    private String currentTagName;

    private String key;
    private long id;
    private  String name;
    private  Coordinates coordinates;
    private  java.time.LocalDateTime creationDate;
    private  Double area;
    private  long numberOfRooms;
    private  Furnish furnish;
    private  View view;
    private  Transport transport;
    private  House house;
    private long x;
    private double yfs;
    private String houseName;
    private int year;
    private long numberOfLifts;
    private String owner;

    private ConcurrentHashMap<String , Flat> linkedHashMap = new ConcurrentHashMap<String, Flat>();

    public ConcurrentHashMap<String, Flat> getConcurrentHashMap() {
        return linkedHashMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        System.out.println("Start el " + qName);
        currentTagName = qName;
        switch (currentTagName){
            case (TAG_FLAT):
                isFlat = true;
                break;
            case (TAG_COORDINATES):
                isCoordinates = true;
                break;
            case (TAG_HOUSE):
                isHouse = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName){
            case (TAG_COORDINATES):
                isCoordinates = false;
                coordinates = new Coordinates(x,yfs);
                break;
            case (TAG_HOUSE):
                isHouse = false;
                house = new House(houseName, year, numberOfLifts);
                break;
            case (TAG_FLAT):
                isFlat = false;
                Flat flat = new Flat(name, coordinates, area, numberOfRooms,
                        furnish, view, transport, house, creationDate, owner, id);
                linkedHashMap.put(key, flat);
                break;
        }
        currentTagName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTagName==null){
            return;
        }

        if (isFlat && !isCoordinates && !isHouse){
            switch (currentTagName){
                case (TAG_KEY):
                    key = new String(ch, start, length);
                    break;
                case (TAG_NAME):
                    name = new String(ch, start, length);
                    break;
                case (TAG_AREA):
                    area = Double.parseDouble(new String(ch, start, length));
                    break;
                case (TAG_NUMBEROFROOMS):
                    numberOfRooms = Long.parseLong(new String(ch, start, length));
                    break;
                case (TAG_FURNISH):
                    furnish = Furnish.valueOf(new String(ch, start, length));
                    break;
                case (TAG_VIEW):
                    view = View.valueOf(new String(ch, start, length));
                    break;
                case (TAG_TRANSPORT):
                    transport = Transport.valueOf(new String(ch, start, length));
                    break;
                case (TAG_CREATIONDATE):
                    creationDate = java.time.LocalDateTime.parse(new String(ch, start, length));
                    break;
                case (TAG_OWNER):
                    owner = new String(ch, start, length);
                    break;
                case (TAG_ID):
                    id = Long.parseLong(new String(ch, start, length));
                    break;
            }
        }

        if (isFlat && isCoordinates){
            switch (currentTagName){
                case (TAG_X):
                    x = Long.parseLong(new String(ch, start, length));
                    break;
                case (TAG_Y):
                    yfs = Double.parseDouble(new String(ch, start, length));
                    break;
            }
        }

        if (isFlat && isHouse){
            switch (currentTagName){
                case (TAG_HOUSENAME):
                    houseName = new String(ch, start, length);
                    break;
                case (TAG_YEAR):
                    year = Integer.parseInt(new String(ch, start, length));
                    break;
                case (TAG_NUMBEROFLIFTS):
                    numberOfLifts = Long.parseLong(new String(ch, start, length));
                    break;
            }
        }
    }
}
