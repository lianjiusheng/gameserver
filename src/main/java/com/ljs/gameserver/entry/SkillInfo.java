package com.ljs.gameserver.entry;

import java.util.HashMap;
import java.util.Map;

public class SkillInfo {

    public Map<Integer, Integer> getSkillLvlInfo() {
        return skillLvlInfo;
    }

    public void setSkillLvlInfo(Map<Integer, Integer> skillLvlInfo) {
        this.skillLvlInfo = skillLvlInfo;
    }

    public Map<Integer, Integer> skillLvlInfo;
}
