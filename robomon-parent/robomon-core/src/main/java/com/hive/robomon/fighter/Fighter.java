package com.hive.robomon.fighter;

import com.hive.robomon.battle.BattleManager;

public interface Fighter extends Runnable {

    String getName();

    void setName(String name);

    int getHealth();

    void setHealth(int health);

    public int getAttackPower();

    public void setAttackPower(int attackPower);

    FightingType getType();

    void setType(FightingType type);

    BattleManager getBattleManager();

    void setBattleManager(BattleManager battleManager);

    boolean isFightStarted();

    void setFightStarted(boolean fightStarted);
}
