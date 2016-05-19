package com.icnhdevelopment.wotn.handlers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Fonts;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Albert on 1/21/2016.
 */
public class TextHandler {

	String currentText, newText = "";
	int scrollSpeed;
	static int counter = 0;
	final int maxCharactersPerLine = 0; //Change This Later
	final int maxNumOfLines = 0; //Change This Later

	BitmapFont font;
	Rectangle container;
	
	public TextHandler(String text, Rectangle container){
		setText(text);
		this.container = container;
		font = Fonts.loadFont(Fonts.OPEN_SANS, 20);
	}
 	
 	public ArrayList<String> textToLines(String text){
 		ArrayList<String> t = new ArrayList(Arrays.asList(text.split("\\s+"))); //Added temporary parameter because errors
 		ArrayList<String> lines = new ArrayList<>();
 		String temp="";
 		int count = 0;
 		
 		//Might change this into a for-each loop later
 		for (int i = 0; i < t.size(); i++){
 			if (t.get(i).length() + (count + 1) <= maxCharactersPerLine){// There will always be a space at the end, I don't want to make a seperate case.
 				temp = temp.concat(t.get(i).concat(" ")); //Adds text to string if total character length doesn't exceed max
 				System.out.println(temp);
 				count += t.get(i).length() + 1; //Updates characters count
 				if (i == t.size() - 1) lines.add(temp);
 			}
 			else if (t.get(i).length() + (count + 1) >= maxCharactersPerLine){
 				count = 0;
 				lines.add(temp); //Sets concated string as line and starts concating a new line
 				temp = "";
 				
 				temp = temp.concat(t.get(i).concat(" "));
 				count += t.get(i).length() + 1;
 			}
 		}
		return lines;
 	}
 	
 	public boolean scrollText(String oldText, String text){
		if (text.length() == oldText.length()) return true;
		else {
			newText = text.substring(0, oldText.length() + 1);
		}
		return false;
 	}
 	
 	/*
 	public void scrollText(ArrayList<String> t, int scrollSpeed){
 	    int count = 0;
 	    String temp = "";
 		for (int i = 0; i < t.size(); i++){
 			for (int j = 0; j < t.get(i).length()*scrollSpeed; j++){
 				count++;
 				if (count%scrollSpeed==0) temp = t.get(i).substring(0,i+1); //replace temp with line[i].text or something like that to indicate which line
 			}
 		}
 	}
 	*/
 	
 	public void setText(String text){
 		currentText = text;
		newText = "";
 	}
 	
 	public void setScrollSpeed(int speed){
 		//scrollSpeed = speed;
 	}
 	
 	public boolean update(){
 		return scrollText(newText, currentText);
 	}

	public void render(SpriteBatch batch){
		font.draw(batch, newText, container.x, container.y+font.getBounds(newText).height);
	}
}
