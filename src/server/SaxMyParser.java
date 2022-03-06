package server;

import database.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class SaxMyParser {

    //parsing XML collection to FlatCollection collection

    public ConcurrentHashMap<String, Flat> parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SaxParserHandler handler = new SaxParserHandler();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (Exception e) {
            System.out.println("Open sax parser error " + e.toString());
            return null;
        }

        File file = new File("collection.xml");
        try {
            parser.parse(file, handler);
        } catch (SAXException e) {
            System.out.println("sax parsing error " + e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("IO parser error " + e.toString());
            return null;
        }
        return handler.getConcurrentHashMap();
    }

}
