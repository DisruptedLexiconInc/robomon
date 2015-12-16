package com.hive.robomon.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.fighter.Fighter;

public class RoundEvent {

    private Fighter fighter1;

    private Fighter fighter2;

    private int counter;

    private long time;

    public RoundEvent() {
        time = System.currentTimeMillis();
    }

    public RoundEvent(Fighter fighter1, Fighter fighter2, int counter) {
        this();
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.counter = counter;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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
