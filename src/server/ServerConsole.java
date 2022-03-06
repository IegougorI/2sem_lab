package server;
import java.io.IOException;
import java.util.Scanner;
import database.*;

import javax.xml.parsers.ParserConfigurationException;

public class ServerConsole extends Thread {
    private FlatCollection collection;
    private boolean exit;
    public ServerConsole(FlatCollection collection, boolean f){
        this.collection = collection;
        exit = f;
    }



    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext() && !exit) {
            String command = scanner.nextLine();
            if (command.equals("save")) {
                XmlGenerating generating = new XmlGenerating();
                try {
                    generating.generateXMLFile(collection);
                    System.out.println("Collection has been saved");
                } catch (ParserConfigurationException | IOException e) {
                    System.out.println("Problems with generating file");
                }
            }else if(command.equals("exit")){

                System.exit(0);
            } else {
                System.out.println("This console can only save collection");
            }
        }
    }
}