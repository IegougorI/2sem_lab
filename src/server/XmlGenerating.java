package server;

import database.Flat;
import database.FlatCollection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class XmlGenerating {

    public void generateXMLFile(FlatCollection collection) throws ParserConfigurationException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element flatCollection = document.createElement("FlatCollection");
        document.appendChild(flatCollection);

        Map map = collection.getHashMap();
        List<Map.Entry<String, Flat>> list = new LinkedList<Map.Entry<String, Flat>>(map.entrySet());
        for (Map.Entry<String, Flat> entry : list) {
            Element flat = document.createElement("flat");
            flatCollection.appendChild(flat);

            Element key = document.createElement("key");
            Text keyText = document.createTextNode(entry.getKey());
            flat.appendChild(key);
            key.appendChild(keyText);

            Element name = document.createElement("name");
            Text nameText = document.createTextNode(entry.getValue().getName());
            flat.appendChild(name);
            name.appendChild(nameText);

            Element coordinates = document.createElement("coordinates");
            flat.appendChild(coordinates);
            Element x = document.createElement("x");
            Element y = document.createElement("y");
            Text xText = document.createTextNode(String.valueOf(entry.getValue().getX()));
            Text yText = document.createTextNode(String.valueOf(entry.getValue().getYfs()));
            coordinates.appendChild(x);
            coordinates.appendChild(y);
            x.appendChild(xText);
            y.appendChild(yText);

            Element area = document.createElement("area");
            Text areaText = document.createTextNode(String.valueOf(entry.getValue().getArea()));
            flat.appendChild(area);
            area.appendChild(areaText);

            Element numberOfRooms = document.createElement("numberOfRooms");
            Text numberOfRoomsText = document.createTextNode(String.valueOf(entry.getValue().getNumberOfRooms()));
            flat.appendChild(numberOfRooms);
            numberOfRooms.appendChild(numberOfRoomsText);

            Element furnish = document.createElement("furnish");
            Text furnishText = document.createTextNode(String.valueOf(entry.getValue().getFurnish()));
            flat.appendChild(furnish);
            furnish.appendChild(furnishText);

            Element view = document.createElement("view");
            Text viewText = document.createTextNode(String.valueOf(entry.getValue().getView()));
            flat.appendChild(view);
            view.appendChild(viewText);

            Element transport = document.createElement("transport");
            Text transportText = document.createTextNode(String.valueOf(entry.getValue().getTransport()));
            flat.appendChild(transport);
            transport.appendChild(transportText);

            Element house = document.createElement("house");
            flat.appendChild(house);
            Element houseName = document.createElement("houseName");
            Text houseNameText = document.createTextNode(entry.getValue().getHouseName());
            Element year = document.createElement("year");
            Text yearText = document.createTextNode(String.valueOf(entry.getValue().getYear()));
            Element numberOfLifts = document.createElement("numberOfLifts");
            Text numberOfLiftsText = document.createTextNode(String.valueOf(entry.getValue().getNumberOfLifts()));
            house.appendChild(houseName);
            houseName.appendChild(houseNameText);
            house.appendChild(year);
            year.appendChild(yearText);
            house.appendChild(numberOfLifts);
            numberOfLifts.appendChild(numberOfLiftsText);

            Element creationDate = document.createElement("creationDate");
            Text creationDateText = document.createTextNode(String.valueOf(entry.getValue().getCreationDate()));
            flat.appendChild(creationDate);
            creationDate.appendChild(creationDateText);

            Element owner = document.createElement("owner");
            Text ownerText = document.createTextNode(entry.getValue().getOwner());
            flat.appendChild(owner);
            owner.appendChild(ownerText);

            Element id = document.createElement("id");
            Text idText = document.createTextNode(String.valueOf(entry.getValue().getId()));
            flat.appendChild(id);
            id.appendChild(idText);
        }
        DOMImplementation impl = document.getImplementation();
        DOMImplementationLS implLS = (DOMImplementationLS)impl.getFeature("LS", "3.0");
        LSSerializer ser = implLS.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);
        String str = ser.writeToString(document);


        LSOutput out = implLS.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(Files.newOutputStream(Paths.get("collection.xml")));
        ser.write(document, out);

    }
}
