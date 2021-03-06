package com.hive.robomon.fighter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.battle.BattleManager;
import com.hive.robomon.battle.SimpleBattleManager;
import com.hive.robomon.event.LandedHitEvent;
import com.hive.robomon.fighter.FightingType;

public abstract class SimpleFighterBase implements SimpleFighter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFighterBase.class);

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
    public void onLandedHit(LandedHitEvent event) {
        LOGGER.trace("Landed a hit on {} for {} hit points.", event.getTargetName(), event.getTargetOldHealth() - event.getTargetNewHealth());

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

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
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
