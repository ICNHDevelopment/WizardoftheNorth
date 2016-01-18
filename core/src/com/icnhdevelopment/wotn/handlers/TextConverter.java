package com.icnhdevelopment.wotn.handlers;

/**
 * Created by Albert on 1/16/2016.
 */
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Label;
import com.icnhdevelopment.wotn.gui.ImageLabel;

import java.util.Arrays;
import java.util.ArrayList;

public class TextConverter extends Container{

    public static void main(String[] args) {
        //Container test = Array_To_Container(Text_To_Array(new File("X:\\WizardoftheNorth\\core\\assets\\ui\\Menus\\MNUMain.txt")));
    }

    public static ArrayList<String> Text_To_Array(FileHandle file) {
        ArrayList<String> text = new ArrayList(Arrays.asList(file.readString().split("\n")));
        return text;
    }

    public static Container Array_To_Container(ArrayList<String> a){
        Container contain = new Container();
        boolean hasChildren = false;
        
        //try{ contain.setName(a.get(0).substring(a.get(0).indexOf("[")+1,a.get(0).indexOf("]")+1).replace("]", "")); } catch(Exception e){}
        try{ contain.setName(valueOf(a, "Container.getName()")); } catch(Exception e){}
        try{ contain.setPosition(new Vector2(Float.parseFloat(valueOf(a, contain.getName() + ".Position.X")), Float.parseFloat(valueOf(a, contain.getName() + ".Position.Y")))); } catch(Exception e){}
        try{ contain.setSize(new Vector2(Float.parseFloat(valueOf(a, contain.getName() + ".Size.X")), Float.parseFloat(valueOf(a, contain.getName() + ".Size.Y")))); } catch(Exception e){}

        for (String line : a) if (line.contains(contain.getName() + ".Children")) hasChildren = true;
        if (hasChildren) contain.setChildren(new ArrayList<>());

        for (String line : a) {
            if (line.contains(contain.getName() + ".Children.") && (line.length() - line.replace(".", "").length()) == 2) {
                //String name = line.substring(line.indexOf(".Children.") + 10,line.indexOf(")") + 1).replace(")", "");
                String name = line.substring(line.indexOf(".Children.") + 10,line.indexOf(")") + 1).replace(")", "");
                //p(name);
                String type = valueOf(a, contain.getName() + ".Children." + name + ".Type");
                String childFullName = contain.getName() + ".Children." + name;
                if (type.equals("Label")){
                    Label l = new Label(contain,
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                            valueOf(a, childFullName + ".Text"));
                    try { l.setBordercolor(new Color(valueOf(a, childFullName + ".BorderColor", true))); } catch(Exception e){}
                    try{ l.setColor(new Color(valueOf(a, childFullName + ".Color", true))); } catch(Exception e){}
                    try{ l.setFontsize(valueOf(a, childFullName + ".FontSize", true)); } catch(Exception e){}
                    try{ l.setFontType(valueOf(a, childFullName + ".FontType")); } catch(Exception e){}
                    try{ l.sethalignment(com.icnhdevelopment.wotn.gui.Alignment.valueOf(valueOf(a, childFullName + ".HAlignment"))); } catch(Exception e){}
                    try{ l.setUsefontsize(Boolean.valueOf(valueOf(a, childFullName + ".UseFontSize"))); } catch(Exception e){}
                    try{ l.setvalignment(Alignment.valueOf(valueOf(a, childFullName + ".VAlignment"))); } catch(Exception e){}
                    try{ l.setBackcolor(new Color(valueOf(a, childFullName + ".BackColor", true))); } catch(Exception e){}
                    //try{ l.setVisible(Boolean.valueOf(valueOf(a, childFullName + ".SetVisible"))); } catch(Exception e){}
                    try{ l.setName(name); } catch(Exception e){}
                    try{ l.setType(type); } catch(Exception e){}
                    try{ l.createFont(); } catch(Exception e){}
                }
                else if (type.equals("ImageLabel")){
                    ImageLabel i = new ImageLabel(contain,
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Position.X")), Float.parseFloat(valueOf(a, childFullName + ".Position.Y"))),
                            new Vector2(Float.parseFloat(valueOf(a, childFullName + ".Size.X")), Float.parseFloat(valueOf(a, childFullName + ".Size.Y"))),
                            new Texture(valueOf(a, childFullName + ".TextureFile")));
                    try{ i.setBackcolor(new Color(valueOf(a, childFullName + ".BackColor", true))); } catch(Exception e){}
                    try{ i.setImagealignment(Alignment.valueOf(valueOf(a, childFullName + ".ImageAlignment"))); } catch(Exception e){}
                    try{ i.setHoverImage(new Texture(valueOf(a, childFullName + ".HoverImage"))); } catch(Exception e){}
                    try{
                        boolean isButton = Boolean.valueOf(valueOf(a, childFullName + ".Button"));
                        if (isButton){
                            contain.buttons.add(i);
                            try{ i.setFunc(ButtonFuction.valueOf(valueOf(a, childFullName + ".ButtonFunc")));}catch(Exception e){}
                            try{ i.setDesc(valueOf(a, childFullName + ".ButtonDesc"));}catch(Exception e){}
                        }
                    }catch(Exception e){}
                    try{ i.setName(name); } catch(Exception e){}
                    try{ i.setType(type); } catch(Exception e){}
                }
            }
        }
        return contain;
    }

    public static String valueOf(ArrayList<String> a, String variable){
        for (String line : a){
            if (line.contains("{") && line.contains("}") && variable.equals(line.substring(line.indexOf("(") + 1,line.indexOf(")") + 1).replace(")", ""))){
                //Why is there an error for line.indexOf() if you don't put +Something?
                //Find way so that you don't need replace(")", "")
                return line.substring(line.indexOf("{") + 1,line.indexOf("}") + 1).replace("}", "");
            }
            else if (line.contains("[") && line.contains("]") && variable.equals("Container.getName()")){
                return line.substring(line.indexOf("[") + 1,line.indexOf("]") + 1).replace("]", "");
            }
        }
        return null;
    }

    public static int valueOf(ArrayList<String> a, String variable, Boolean I_WANT_THIS_TO_BE_AN_INTERGER) throws Exception{
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
        throw new Exception();
    }

    public static void p(Object o){
        System.out.println(o);
    }
}
