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

	ArrayList<ArrayList<String>> fullText;
	ArrayList<String> renderedText;
	int lines = 0;
	int frame = 0;

	BitmapFont font;
	Rectangle container;

	int maxLinesHigh;
	
	public TextHandler(String text, Rectangle container){
		this.container = container;
		font = Fonts.loadFont(Fonts.OPEN_SANS, 20);
		maxLinesHigh = (int)(container.getHeight()/(font.getBounds("Tyk").height+8));
		setText(text);
	}
 	
 	public ArrayList<String> textToLines(String text){
 		ArrayList<String> lines = new ArrayList<>();
		String tempLine = "";
		String[] words = text.split(" ");
		for (String s : words){
			String newTemp = tempLine + " " + s;
			if (font.getBounds(newTemp).width>container.width-12){
				lines.add(tempLine);
				tempLine = s;
				if (lines.size()==maxLinesHigh){
					return lines;
				}
			} else {
				tempLine += " " + s;
			}
		}
		if (tempLine.length()>0) lines.add(tempLine);
		return lines;
 	}
 	
 	public boolean scrollText(){
		String renderedCurrent = renderedText.get(lines);
		String fullCurrent = fullText.get(frame).get(lines);
		String renderedLast = renderedText.get(renderedText.size()-1);
		String fullLast = fullText.get(frame).get(fullText.size()-1);
		int renderedCurrentSize = renderedCurrent.length();
		int fullCurrentSize = fullCurrent.length();
		if (lines == fullText.get(frame).size() && renderedLast.length() == fullLast.length()
		&& renderedCurrentSize == fullCurrentSize) return true;
		else {
			if (renderedCurrentSize<fullCurrentSize){
				renderedText.set(lines, fullText.get(frame).get(lines).substring(0, renderedCurrent.length()+1));
			} else {
				if (lines<renderedText.size()-1) {
					lines++;
				}
			}
		}
		return false;
 	}

	public boolean isTextFinished(){
		String renderedCurrent = renderedText.get(lines);
		String fullCurrent = fullText.get(frame).get(lines);
		String renderedLast = renderedText.get(renderedText.size()-1);
		String fullLast = fullText.get(frame).get(fullText.get(frame).size()-1);
		int renderedCurrentSize = renderedCurrent.length();
		int fullCurrentSize = fullCurrent.length();
		if (frame == fullText.size()-1 && renderedLast.length() == fullLast.length()
				&& renderedCurrentSize == fullCurrentSize) return true;
		return false;
	}

	public void goToNextFrame(){
		frame++;
		renderedText.clear();
		for (int i = 0; i<fullText.get(frame).size(); i++){
			renderedText.add("");
		}
	}

	public boolean isFrameFinished(){
		String renderedCurrent = renderedText.get(lines);
		String fullCurrent = fullText.get(frame).get(lines);
		String renderedLast = renderedText.get(renderedText.size()-1);
		String fullLast = fullText.get(frame).get(fullText.size()-1);
		int renderedCurrentSize = renderedCurrent.length();
		int fullCurrentSize = fullCurrent.length();
		if (renderedLast.length() == fullLast.length()
				&& renderedCurrentSize == fullCurrentSize) return true;
		return false;
	}

	public void skipToEnd(){
		renderedText = fullText.get(frame);
		lines = renderedText.size()-1;
	}

	public String getAllTextInFrame(int frame){
		return getAllTextInArrayList(fullText.get(frame));
	}

	public String getAllTextInArrayList(ArrayList<String> ray){
		String txt = "";
		for (String s : ray){
			txt += s + " ";
		}
		return txt;
	}
 	
 	public void setText(String text){
		int frames = 0;
		fullText = new ArrayList<>();
		do {
			fullText.add(textToLines(text));
			String frameTxt = getAllTextInFrame(frames);
			if (frameTxt.length()-2==text.length()){
				text = "";
			} else {
				text = text.substring(frameTxt.length() - 1);
			}
			frames++;
		} while (text.length()>0);
		lines = 0;
		renderedText = new ArrayList<>();
		for (int i = 0; i<fullText.get(frame).size(); i++){
			renderedText.add("");
		}
 	}
 	
 	public boolean update(){
 		return scrollText();
 	}

	public void render(SpriteBatch batch){
		for (int i = 0; i<lines+1; i++){
			int y = (int)(container.y + container.height - ((i+1)*(font.getBounds("Tyk").height+8)));
			font.draw(batch, renderedText.get(i), container.x+6, y);
		}
	}
}
