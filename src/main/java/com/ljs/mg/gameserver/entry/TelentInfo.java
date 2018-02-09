package com.ljs.mg.gameserver.entry;

import java.util.Map;

/**
 * 专精信息
 * **/
public class TelentInfo {

    //专精倾向
    public int trendId;

    //专精等级

    public int trendLv;

    //指挥官专精 talentID -> level
    public Map<Integer, Integer> talent ;

    //专精点
    public int talentPoint = 1;

    //专精下一次可以重置的时间
    public long talentResetTime;

    public int getTrendId() {
        return trendId;
    }

    public void setTrendId(int trendId) {
        this.trendId = trendId;
    }

    public int getTrendLv() {
        return trendLv;
    }

    public void setTrendLv(int trendLv) {
        this.trendLv = trendLv;
    }

    public Map<Integer, Integer> getTalent() {
        return talent;
    }

    public void setTalent(Map<Integer, Integer> talent) {
        this.talent = talent;
    }

    public int getTalentPoint() {
        return talentPoint;
    }

    public void setTalentPoint(int talentPoint) {
        this.talentPoint = talentPoint;
    }

    public long getTalentResetTime() {
        return talentResetTime;
    }

    public void setTalentResetTime(long talentResetTime) {
        this.talentResetTime = talentResetTime;
    }


}
