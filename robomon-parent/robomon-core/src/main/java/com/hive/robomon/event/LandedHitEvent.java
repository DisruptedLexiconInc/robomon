package com.hive.robomon.event;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LandedHitEvent {

    private String targetName;

    private int targetOldHealth;

    private int targetNewHealth;

    private long time;

    public LandedHitEvent() {
        time = System.currentTimeMillis();
    }

    public LandedHitEvent(String name, int oldHealth, int newHealth) {
        this();
        targetName = name;
        targetOldHealth = oldHealth;
        targetNewHealth = newHealth;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getTargetOldHealth() {
        return targetOldHealth;
    }

    public void setTargetOldHealth(int targetOldHealth) {
        this.targetOldHealth = targetOldHealth;
    }

    public int getTargetNewHealth() {
        return targetNewHealth;
    }

    public void setTargetNewHealth(int targetNewHealth) {
        this.targetNewHealth = targetNewHealth;
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
