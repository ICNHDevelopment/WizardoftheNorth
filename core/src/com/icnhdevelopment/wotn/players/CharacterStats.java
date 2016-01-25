package com.icnhdevelopment.wotn.players;

/**
 * Created by kyle on 1/24/16.
 */
public class CharacterStats {

    Character character;

    public CharacterStats(Character c, int l, int v, int a, int r, int s, int w){
        this.character = c;
        level = l;
        BaseVitality = v;
        IvVitality = 10;
        CurrentVitality = getVitality();
        BaseAgility = a;
        IvAgility = 10;
        BaseWisdom = w;
        IvWisdom = 10;
        CurrentWisdom = getWisdom();
        BaseStrength = s;
        IvStrength = 10;
        BaseResistance = r;
        IvResistance = 10;
        CurrentExperience = 0;
        NextLevelExp = CalculateLevelExp(level+1);
        CurrLevelExp = CalculateLevelExp(level);
    }

    int level;
    public int getLevel(){ return level; }

    float CurrentExperience;
    public float getCurrentExperience(){ return CurrentExperience; }
    float NextLevelExp;
    float CurrLevelExp;
    public float getRequiredExperience(){
        return NextLevelExp-CurrLevelExp;
    }

    float CurrentVitality;
    public float getCurrentVitality(){ return CurrentVitality; }
    int BaseVitality;
    int IvVitality = 10;
    public int getVitality(){
        double result = level + 10 + ((((BaseVitality+IvVitality+character.getBonusVitality())*2)*level)/100);
        return (int)Math.floor(result+character.getBonusVitality()/20);
    }

    int BaseAgility;
    int IvAgility = 10;
    public int getAgility(){
        double result = 5 + (((BaseAgility+IvAgility+character.getBonusAgility())*2)*level)/100;
        return (int)Math.floor(result+character.getBonusAgility()/20);
    }

    int BaseResistance;
    int IvResistance = 10;
    public int getResistance(){
        double result = 5 + (((BaseResistance+IvResistance+character.getBonusResistance())*2)*level)/100;
        return (int)Math.floor(result+character.getBonusResistance()/20);
    }

    int BaseStrength;
    int IvStrength = 10;
    public int getStrength(){
        double result = 5 + (((BaseStrength+IvStrength+character.getBonusStrength())*2)*level)/100;
        return (int)Math.floor(result+character.getBonusStrength()/20);
    }

    float CurrentWisdom;
    public float getCurrentWisdom(){ return CurrentWisdom; }
    int BaseWisdom;
    int IvWisdom = 10;
    public int getWisdom(){
        double result = 5 + (((BaseWisdom+IvWisdom+character.getBonusWisdom())*2)*level)/100;
        return (int)Math.floor(result+character.getBonusWisdom()/20);
    }

    float CalculateLevelExp(int currentLevel){
        float nCube = (float)Math.pow((double)currentLevel, 3.0);
        float returnVal;
        if (currentLevel<=15){
            returnVal = ((((currentLevel+1)/3.0f)+24)/50.0f)*nCube;
        }
        else if (currentLevel<=36){
            returnVal = (((currentLevel)+14)/50.0f)*nCube;
        }
        else {
            returnVal = ((((currentLevel)/2.0f)+32)/50.0f)*nCube;
        }
        return (float)Math.floor(returnVal);
    }
}
