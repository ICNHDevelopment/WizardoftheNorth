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
 		ArrayList<String> temp
 		int count = 0;
 		
 		//Might change this into a for-each loop later
 		for (int i = 0; i < t.size(); i++){
 			if (t.get(i).length() + count <= maxCharactersPerLine){
 				lines.add(t.get(i));
 			}
 			else {
 				
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
