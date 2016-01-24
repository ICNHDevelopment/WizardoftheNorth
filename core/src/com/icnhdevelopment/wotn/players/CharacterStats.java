package com.icnhdevelopment.wotn.players;

/**
 * Created by kyle on 1/24/16.
 */
public class CharacterStats {

    public CharacterStats(int l, int v, int a, int r, int s, int w){
        level = 1;
        BaseVitality = 35;
        IvVitality = 10;
        CurrentVitality = getVitality();
        BaseAgility = 90;
        IvAgility = 10;
        BaseWisdom = 50;
        IvWisdom = 10;
        CurrentWisdom = getWisdom();
        BaseStrength = 55;
        IvStrength = 10;
        BaseResistance = 30;
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
        double result = level + 10 + ((((BaseVitality+IvVitality)*2)*level)/100);
        return (int)Math.floor(result);
    }

    int BaseAgility;
    int IvAgility = 10;
    public int getAgility(){
        double result = 5 + (((BaseAgility+IvAgility)*2)*level)/100;
        return (int)Math.floor(result);
    }

    int BaseResistance;
    int IvResistance = 10;
    public int getResistance(){
        double result = 5 + (((BaseResistance+IvResistance)*2)*level)/100;
        return (int)Math.floor(result);
    }

    int BaseStrength;
    int IvStrength = 10;
    public int getStrength(){
        double result = 5 + (((BaseStrength+IvStrength)*2)*level)/100;
        return (int)Math.floor(result);
    }

    float CurrentWisdom;
    public float getCurrentWisdom(){ return CurrentWisdom; }
    int BaseWisdom;
    int IvWisdom = 10;
    public int getWisdom(){
        double result = 5 + (((BaseWisdom+IvWisdom)*2)*level)/100;
        return (int)Math.floor(result);
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
