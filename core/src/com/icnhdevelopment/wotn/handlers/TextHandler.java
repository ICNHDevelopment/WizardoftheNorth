package com.icnhdevelopment.wotn.handlers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Albert on 1/21/2016.
 */
public class TextHandler {

	String currentText;
	int scrollSpeed;
	final int maxCharactersPerLine = 0; //Change This Later
	final int maxNumOfLines = 0; //Change This Later
 	
 	public static ArrayList<String> textToLines(String text){
 		ArrayList<String> t = new ArrayList(Arrays.asList(text.split("\\s+"))); //Added temporary parameter because errors
 		//System.out.println(t.size());
 		//System.out.println(t.get(0));
 		ArrayList<String> lines = new ArrayList<>();
 		String temp="";
 		int count = 0;
 		
 		//Might change this into a for-each loop later
 		for (int i = 0; i < t.size(); i++){
 			if (t.get(i).length() + (count + 1) <= maxCharactersPerLine){// There will always be a space at the end, I don't want to make a seperate case.
 				temp = temp.concat(t.get(i).concat(" ")); //Adds text to string if total character length doesn't exceed max
 				count += t.get(i).length() + 1; //Updates characters count
 				if (i == t.size() - 1) lines.add(temp);
 			}
 			else if (t.get(i).length() + (count + 1) >= maxCharactersPerLine){
 				count = 0;
 				lines.add(temp); //Sets concated string as line and starts concating a new line
 				temp = "";
 				
 				temp.concat(t.get(i) + " ");
 				count += count += t.get(i).length() + 1;
 			}
 			else if(lines.size() > maxNumOfLines){
 				//textBox.setText(1, "") where textBox.setText(int lineNumber, string text);
 				//or something like textBox.clear();
 				
 				count = 0;
 				lines.add(temp);
 				temp = "";
 				
 				temp.concat(t.get(i) + " ");
 				count += count += t.get(i).length() + 1;
 			}
 		}
		return lines;
 	}
 	
 	public void scrollText(ArrayList<String> t, int scrollSpeed){
 		for (int i = 0; i < t.size(); i++){
 			for (int j = 0; j < t.get(i).length(); j++){
 				//Something
 			}
 		}
 	}
 	
 	public void setText(String text){
 		currentText = text;
 	}
 	
 	public void setScrollSpeed(int speed){
 		scrollSpeed = speed;
 	}
}
