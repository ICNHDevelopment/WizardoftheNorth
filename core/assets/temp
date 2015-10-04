package xml_converter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
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
public class XML_Converter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println(XML_Converter(new File("C:\\Users\\Albert\\Documents\\NetBeansProjects\\XML_Converter\\src\\xml_converter\\MNUMain.xml")));
        XML_Converter(new File("C:\\Users\\Albert\\Documents\\NetBeansProjects\\XML_Converter\\src\\xml_converter\\MNUMain.xml"));
        
    }
    
    public static ArrayList<Container> XML_Converter(File file){
        ArrayList container = new ArrayList();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Children");
            
            for (int i = 0; i < (nList.item(0).getChildNodes().getLength())/2; i++){
                Container temp = new Container();
                Node node = nList.item(0).getChildNodes().item((i*2)+1);
                for (int j = 0; j < (node.getChildNodes().getLength())/2; j++){
                    //Element elemento = (Element) node.getChildNodes().item((i*2)+1);
                    Element elemento = (Element) node;
                    w(elemento.getElementsByTagName(node.getChildNodes().item((j*2)+1).getNodeName()).item(0).getTextContent());
                    w(node.getChildNodes().item((j*2)+1).getNodeName());
                    set(temp, node.getChildNodes().item((j*2)+1).getNodeName().toLowerCase(), elemento.getElementsByTagName(node.getChildNodes().item((j*2)+1).getNodeName()).item(0).getNodeValue());
                    //Sets node value to object property value by matching node name with object property name.
                }
                container.add(temp);
            }
            //w(getter(container.get(0)), "position");
            
        } 
        catch (ParserConfigurationException | SAXException | IOException | DOMException e) { e.printStackTrace(); }
        
        return container;
    }
    
    public static void w(Object o){ System.out.println(o); }
    public static void set(Object obj, String fld, String value){
        //Sets an object's property value without using (object.property = value;) 
        Class<?> cl = obj.getClass();
        try {
            Field field = cl.getDeclaredField(fld);
            field.setAccessible(true);
            field.set(obj, field.getType().cast(value));
        }
        catch (NoSuchFieldException  | IllegalAccessException e){
            cl = cl.getSuperclass();
            w(e.toString());
        }
        
    }

}
