package com.hive.robomon.fighter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.battle.BattleManager;
import com.hive.robomon.battle.SimpleBattleManager;
import com.hive.robomon.fighter.FightingType;

public abstract class SimpleFighterBase implements SimpleFighter {

    private String name;

    private volatile int health;

    private int attackPower;

    private FightingType type;

    private volatile SimpleBattleManager battleManager;

    private boolean fightStarted = false;

    public SimpleFighterBase(String name, int health, int attackPower, FightingType type) {
        this.name = name;
        this.health = health;
        this.type = type;
        this.attackPower = attackPower;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized int getHealth() {
        return health;
    }

    @Override
    public synchronized void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public FightingType getType() {
        return type;
    }

    @Override
    public void setType(FightingType type) {
        this.type = type;
    }

    @Override
    public BattleManager getBattleManager() {
        return battleManager;
    }

    @Override
    public void setBattleManager(BattleManager battleManager) {
        this.battleManager = (SimpleBattleManager) battleManager;
    }

    @Override
    public boolean isFightStarted() {
        return fightStarted;
    }

    @Override
    public void setFightStarted(boolean fightStarted) {
        this.fightStarted = fightStarted;
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
