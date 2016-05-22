package com.icnhdevelopment.wotn.players;

/**
 * Created by kyle on 5/21/16.
 */
public class PartyCharacter extends NPCharacter {

    public PartyCharacter(String pref){
        setDefaults();
        prefix = pref;
    }

    public void setDefaults(){
        defaultFile = "characters/images/";
    }

    public void interact(){

    }
}
