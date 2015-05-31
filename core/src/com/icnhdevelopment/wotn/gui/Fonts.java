package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Created by kyle on 5/31/15.
 */
public class Fonts {

    public static String OPEN_SANS = "OpenSans";

    public static BitmapFont loadFont(String name) {
        return loadFont(name, 12);
    }

    public static BitmapFont loadFont(String name, int size) {
        return loadFont(name, size, Color.WHITE, Color.WHITE);
    }

    public static BitmapFont loadFont(String name, int size, Color fontColor, Color borderColor) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + name + ".ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = fontColor;
        parameter.borderColor = borderColor;
        if (borderColor != fontColor) {
            parameter.borderWidth = (int)((float)size/12.0);
        }
        BitmapFont temp = generator.generateFont(parameter);
        generator.dispose();
        return temp;
    }
}
