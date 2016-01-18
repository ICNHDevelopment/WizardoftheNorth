package com.icnhdevelopment.wotn.handlers;

/**
 * Created by Albert on 1/16/2016.
 */
import com.badlogic.gdx.ai.steer.behaviors.Alignment;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Label;
import com.icnhdevelopment.wotn.gui.ImageLabel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.ArrayList;

public class TextConverter extends Container{

    public static void main(String[] args) {
        //Container test = Array_To_Container(Text_To_Array(new File("X:\\WizardoftheNorth\\core\\assets\\ui\\Menus\\MNUMain.txt")));
    }

    public static ArrayList<String> Text_To_Array(FileHandle file) {
        ArrayList<String> text = new ArrayList(Arrays.asList(file.readString().split("\n")));
        /*
        System.out.println(file.readString());
        BufferedReader br = null;
        try {
            String sCurrentLine;
            //br = new BufferedReader(new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) text.add(sCurrentLine);
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { if (br != null)br.close(); }
            catch (IOException ex) { ex.printStackTrace(); }
        }
        */
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
                //String name = line.substring(line.indexOf(".Children.") + 10,line.indexOf(")") + 1).replace(")", "");
                String name = line.substring(line.indexOf(".Children.") + 10,line.indexOf(")") + 1).replace(")", "");
                //p(name);
                String type = valueOf(a, contain.getName() + ".Children." + name + ".Type");
                String childFullName = contain.getName() + ".Children." + name;
                if (type.equals("Label")){
                    try{
                        Label l = new Label(contain,
                                new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                                new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                                valueOf(a, childFullName + ".Text"));
                        l.setBordercolor(new Color(valueOf(a, childFullName + ".BorderColor", true)));
                        l.setColor(new Color(valueOf(a, childFullName + ".Color", true)));
                        l.setFontsize(valueOf(a, childFullName + ".FontSize", true));
                        l.setFontType(valueOf(a, childFullName + ".FontType"));
                        l.sethalignment(com.icnhdevelopment.wotn.gui.Alignment.valueOf(valueOf(a, childFullName + ".HAlignment")));
                        l.setUsefontsize(Boolean.valueOf(valueOf(a, childFullName + ".UseFontSize")));
                        l.setvalignment(com.icnhdevelopment.wotn.gui.Alignment.valueOf(valueOf(a, childFullName + ".VAlignment")));
                        l.setBackcolor(new Color(valueOf(a, childFullName + ".BackColor", true)));
                        l.setVisible(Boolean.valueOf(valueOf(a, childFullName + ".SetVisible")));
                        l.setName(name);
                        l.setType(type);
                        l.createFont();
                    }
                    catch(Exception e){
                        //p(e);
                    }
                }
                else if (type.equals("ImageLabel")){
                    try{
                        Container i = new ImageLabel(contain,
                                new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                                new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                                new Texture(valueOf(a, childFullName + ".TextureFile")));
                        i.setVisible(Boolean.valueOf(valueOf(a, childFullName + ".SetVisible")));
                        i.setBackcolor(new Color(valueOf(a, childFullName + ".BackColor", true)));
                        //i.setImagealignment(reflectAlignment(valueOf(a, ".ImageAlignment"))); //I couldn't access this method so...
                        i.setName(name);
                        i.setType(type);
                    }
                    catch(Exception e){
                        //p(e);
                    }
                }
            }
        }
        set(contain, a);
        return contain;
    }

    public static String valueOf(ArrayList<String> a, String variable){
        for (String line : a){
            if (line.contains("{") && line.contains("}") && variable.equals(line.substring(line.indexOf("(") + 1,line.indexOf(")") + 1).replace(")", ""))){
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
                if (line.contains("{") && line.contains("}") && variable.equals(line.substring(line.indexOf("(") + 1,line.indexOf(")") + 1).replace(")", ""))){
                    //Why is there an error for line.indexOf() if you don't put +Something?
                    //Find way so that you don't need replace(")", "")
                    Integer i = Integer.parseInt(line.substring(line.indexOf("{") + 1,line.indexOf("}") + 1).replace("}", ""));
                    return i.intValue();
                }
            }
        }
        return 0;
    }

    public static void set(Container c, ArrayList a){
        c.setPosition(new Vector2(Float.parseFloat(valueOf(a, c.getName() + ".Position.X")), Float.parseFloat(valueOf(a, c.getName() + ".Position.Y"))));
        c.setSize(new Vector2(Float.parseFloat(valueOf(a, c.getName() + ".Size.X")), Float.parseFloat(valueOf(a, c.getName() + ".Size.Y"))));
    }

    public static void p(Object o){
        System.out.println(o);
    }
}
