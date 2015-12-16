package com.hive.robomon.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.fighter.Fighter;

public class BattleStartEvent {

    private Fighter fighter1;

    private Fighter fighter2;

    private long time;

    public BattleStartEvent() {
        time = System.currentTimeMillis();
    }

    public BattleStartEvent(Fighter fighter1, Fighter fighter2) {
        this();
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public Fighter getFighter1() {
        return fighter1;
    }

    public void setFighter1(Fighter fighter1) {
        this.fighter1 = fighter1;
    }

    public Fighter getFighter2() {
        return fighter2;
    }

    public void setFighter2(Fighter fighter2) {
        this.fighter2 = fighter2;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
}
