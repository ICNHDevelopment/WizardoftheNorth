package com.icnhdevelopment.wotn.handlers;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.icnhdevelopment.wotn.gui.Container;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.lang.reflect.Field;

/**
 *
 * @author Albert
 */
public class XMLConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XML_Converter(new File("core/assets/ui/Menus/MNUMain"));

    }

    public static ArrayList<Container> XML_Converter(File file){
        ArrayList<Container> children = new ArrayList<Container>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Children");
            Container temp = null;
            for (int i = 0; i < (nList.item(0).getChildNodes().getLength())/2; i++){
                Node node = nList.item(0).getChildNodes().item((i*2)+1);
                for (int j = 0; j < (node.getChildNodes().getLength())/2; j++){
                    //Element elemento = (Element) node.getChildNodes().item((i*2)+1);
                    Element elemento = (Element) node;
                    temp = Container.getContainer(elemento.getNodeName());
                    String nodeName = node.getNodeName();
                    String nodePropertyName = node.getChildNodes().item((j*2)+1).getNodeName();
                    String nodePropertyValue = elemento.getElementsByTagName(node.getChildNodes().item((j*2)+1).getNodeName()).item(0).getTextContent();
                    w(nodeName + "." + nodePropertyName + ": " + nodePropertyValue);
                    set(temp, temp.getClass(), nodePropertyName.toLowerCase(), nodePropertyValue);
                    //Sets node value to object property value by matching node name with object property name.
                }
                if (temp!=null) children.add(temp);
            }
            //w(getter(container.get(0)), "position");

        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException e) { e.printStackTrace(); }

        return children;
    }

    public static void w(Object o){ System.out.println(o); }

    public static void set(Object obj, Class<?> cl, String fld, String value){
        //Sets an object's property value without using (object.property = value;)

        //ALBERT VALUE IS NULL SOMETIMES BUT WHEN IT IS NOT NULL IT SHOULD WORK

        w(value);
        Class<?> cls = cl;
        try {
            Field field = cls.getDeclaredField(fld);
            field.setAccessible(true);
            field.set(obj, field.getType().cast(value));
        }
        catch (NoSuchFieldException  | IllegalAccessException e){
            cls = cls.getSuperclass();
            set(obj, cls, fld, value);
            //w(e.toString());
        }

    }

}

