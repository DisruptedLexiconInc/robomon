package com.hive.robomon.battle;

import com.hive.robomon.fighter.Fighter;

public interface SimpleBattleManager extends BattleManager {

    void hit(Fighter aggressor, Fighter opponent);
}
