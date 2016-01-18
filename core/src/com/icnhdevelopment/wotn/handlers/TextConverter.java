package com.icnhdevelopment.wotn.handlers;

/**
 * Created by Albert on 1/16/2016.
 */
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Label;
import com.icnhdevelopment.wotn.gui.ImageLabel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

public class TextConverter extends Container{

    public static void main(String[] args) {
        Container test = Array_To_Container(Text_To_Array(new File("X:\\WizardoftheNorth\\core\\assets\\ui\\Menus\\MNUMain.txt")));
    }

    public static ArrayList<String> Text_To_Array(File file) {
        ArrayList<String> text = new ArrayList();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) text.add(sCurrentLine);
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { if (br != null)br.close(); }
            catch (IOException ex) { ex.printStackTrace(); }
        }
        return text;
    }

    public static Container Array_To_Container(ArrayList<String> a){
        Container contain = new Container();
        boolean hasChildren = false;
        contain.setName(a.get(0).substring(a.get(0).indexOf("[")+1,a.get(0).indexOf("]")+1).replace("]", ""));
        for (String line : a) if (line.contains(contain.getName() + ".Children")) hasChildren = true;
        if (hasChildren) contain.setChildren(new ArrayList<>()); //Make new ArrayList and set that to contain.children?
        for (String line : a) {
            if (line.contains(contain.getName() + ".Children.") && (line.length() - line.replace(".", "").length()) == 2) {
                String name = line.substring(line.indexOf(".Children.") + 10,line.indexOf(")") + 1).replace(")", "");
                String type = valueOf(a, contain.getName() + ".Children." + name + ".Type");
                String childFullName = contain.getName() + ".Children." + name;
                if (type.equals("Label")){
                    Label l = new Label(contain,
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                            valueOf(a, childFullName + ".Text"));
                    l.setBordercolor(reflectColor(valueOf(a, ".BorderColor")));
                    l.setColor(reflectColor(valueOf(a, ".Color")));
                    l.setFontsize(valueOf(a, ".FontSize", true));
                    l.setFontType(valueOf(a, ".FontType"));
                    l.sethalignment(reflectAlignment(valueOf(a, ".HAlignment")));
                    l.setUsefontsize(Boolean.valueOf(valueOf(a, ".UseFontSize")));
                    l.setvalignment(reflectAlignment(valueOf(a, ".VAlignment")));
                    l.setBackcolor(reflectColor(valueOf(a, ".BackColor")));
                    l.setVisible(Boolean.valueOf(valueOf(a, ".SetVisible")));
                    l.setName(name);
                    l.setType(type);
                }
                else if (type.equals("ImageLabel")){
                    Container i = new ImageLabel(contain,
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                            new Texture(Gdx.files.internal(valueOf(a, childFullName + ".TextureFile"))));
                    i.setVisible(Boolean.valueOf(valueOf(a, ".SetVisible")));
                    i.setBackcolor(reflectColor(valueOf(a, ".BackColor")));
                    //i.setImagealignment(reflectAlignment(valueOf(a, ".ImageAlignment"))); //I couldn't access this method so...
                    i.setName(name);
                    i.setType(type);
                }
                //contain.children.get(contain.children.size() - 1).name = name;
                //contain.children.get(contain.children.size() - 1).type = type;
            }
        }
        set(contain, a);
        return contain;
    }

    public static String valueOf(ArrayList<String> a, String variable){
        for (String line : a){
            if (variable.equals(line.substring(line.indexOf("(") + 1,line.indexOf(")") + 1).replace(")", "")) && line.contains("}")){
                //Why is there an error for line.indexOf() if you don't put +Something?
                //Find way so that you don't need replace(")", "")
                return line.substring(line.indexOf("{") + 1,line.indexOf("}") + 1).replace("}", "");
            }
        }
        return null;
    }

    public static int valueOf(ArrayList<String> a, String variable, Boolean I_WANT_THIS_TO_BE_AN_INTERGER){
        if(I_WANT_THIS_TO_BE_AN_INTERGER){
            for (String line : a){
                if (variable.equals(line.substring(line.indexOf("(") + 1,line.indexOf(")") + 1).replace(")", "")) && line.contains("}")){
                    //Why is there an error for line.indexOf() if you don't put +Something?
                    //Find way so that you don't need replace(")", "")
                    return Integer.parseInt(line.substring(line.indexOf("{") + 1,line.indexOf("}") + 1).replace("}", ""));
                }
            }
        }
        return -1;
    }

    public static void set(Container c, ArrayList a){
        c.setPosition(new Vector2(Float.parseFloat(valueOf(a, c.getName() + ".Position.X")), Float.parseFloat(valueOf(a, c.getName() + ".Position.Y"))));
        c.setSize(new Vector2(Float.parseFloat(valueOf(a, c.getName() + ".Size.X")), Float.parseFloat(valueOf(a, c.getName() + ".Size.Y"))));
    }

    public static com.badlogic.gdx.graphics.Color reflectColor(String color){
        try {
            return (com.badlogic.gdx.graphics.Color) Class.forName("com.badlogic.gdx.graphics.Color").getClass().getField(color).get(null);
        }
        catch (Exception e) {
            return com.badlogic.gdx.graphics.Color.WHITE;
            //return null; // Not defined
        }
    }

    public static com.icnhdevelopment.wotn.gui.Alignment reflectAlignment(String alignment){
        try {
            return (com.icnhdevelopment.wotn.gui.Alignment) Class.forName("com.icnhdevelopment.wotn.gui.Alignment").getClass().getField(alignment).get(null);
        }
        catch (Exception e) {
            return null; // Not defined
        }
    }

}
