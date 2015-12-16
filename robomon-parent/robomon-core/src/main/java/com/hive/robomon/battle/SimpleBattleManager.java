package com.hive.robomon.battle;

import com.hive.robomon.fighter.impl.SimpleFighter;

public interface SimpleBattleManager extends BattleManager {

    void hit(SimpleFighter aggressor, SimpleFighter opponent);
}
