package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.graphics.Texture;
import com.icnhdevelopment.wotn.gui.special.SlotType;

import java.util.ArrayList;

/**
 * Created by kyle on 1/15/16.
 */
public class SpecialItem extends Item {

    public int VitalityBonus;
    public int AgilityBonus;
    public int ResistanceBonus;
    public int StrengthBonus;
    public int WisdomBonus;

    public SpecialItem(Texture im, SlotType type){
        super(im);
        this.setType(type);
    }

    public SpecialItem(SpecialItem item){
        super(item);

        this.VitalityBonus = item.VitalityBonus;
        this.AgilityBonus = item.AgilityBonus;
        this.ResistanceBonus = item.ResistanceBonus;
        this.StrengthBonus = item.StrengthBonus;
        this.WisdomBonus = item.WisdomBonus;
    }

    public ArrayList<String> getTooltipData(){
        ArrayList<String> r = new ArrayList<>();
        r.add(name);
        if (VitalityBonus!=0){
            String add = "";
            if (VitalityBonus>0){
                add = "+";
            }
            r.add(add + VitalityBonus+" Vitality");
        }
        if (AgilityBonus!=0){
            String add = "";
            if (AgilityBonus>0){
                add = "+";
            }
            r.add(add + AgilityBonus+" Agility");
        }
        if (ResistanceBonus!=0){
            String add = "";
            if (ResistanceBonus>0){
                add = "+";
            }
            r.add(add + ResistanceBonus+" Resistance");
        }
        if (StrengthBonus!=0){
            String add = "";
            if (StrengthBonus>0){
                add = "+";
            }
            r.add(add + StrengthBonus+" Strength");
        }
        if (WisdomBonus!=0){
            String add = "";
            if (WisdomBonus>0){
                add = "+";
            }
            r.add(add + WisdomBonus+" Wisdom");
        }
        return r;
    }

}
