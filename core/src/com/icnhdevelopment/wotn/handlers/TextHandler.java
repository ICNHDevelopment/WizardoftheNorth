package com.icnhdevelopment.wotn.handlers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Albert on 1/21/2016.
 */
public class TextHandler {

	String currentText, newText;
	int scrollSpeed;
	static int counter = 0;
	final int maxCharactersPerLine = 0; //Change This Later
	final int maxNumOfLines = 0; //Change This Later
	
	public TextHandler(String text){
	    currentText = text;
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
 	
 	public void scrollText(String oldText, String text){
 	    newText = text.substring(0, text.indexOf(oldText) + 1); //It might be +1 for the index or -1.
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
 	}
 	
 	public void setScrollSpeed(int speed){
 		//scrollSpeed = speed;
 	}
 	
 	public static void update(){
 		scrollText(newText, currentText);
 	}
}
