package com.icnhdevelopment.wotn.handlers;

/**
 * Created by Albert on 1/21/2016.
 */
public class TextHandler {

	String currentText;
	int scrollSpeed;
	final int maxCharactersPerLine = 0; //Change This Later
	final int maxNumOfLines = 0; //Change This Later
 	
 	public void scrollText(String text){
 		ArrayList<String> t = text.split();
 		ArrayList<String> lines = new ArrayList<String>();
 		String temp;
 		int count = 0;
 		
 		//Might change this into a for-each loop later
 		for (int i = 0; i < t.size(); i++){
 			if (t.get(i).length() + (count + 1) <= maxCharactersPerLine){// There will always be a space at the end, I don't want to make a seperate case.
 				temp.concat(t.get(i) + " ");
 				count += t.get(i).length() + 1;
 			}
 			else if (t.get(i).length() + (count + 1) <= maxCharactersPerLine){
 				count = 0;
 				lines.add(temp);
 				temp = "";
 				
 				temp.concat(t.get(i) + " ");
 				count += count += t.get(i).length() + 1;
 			}
 			else if(lines.size() > maxNumOfLines){
 				//Something about clearing textbox and adding new text
 			}
 		}
 	}
 	
 	public void setText(String text){
 		currentText = text;
 	}
 	
 	public void setScrollSpeed(String speed){
 		scrollSpeed = speed;
 	}
}
