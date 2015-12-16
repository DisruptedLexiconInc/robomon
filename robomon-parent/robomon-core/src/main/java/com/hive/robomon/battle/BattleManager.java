package com.hive.robomon.battle;

import com.hive.robomon.event.SimpleBattleEventListener;

public interface BattleManager extends Runnable {

    void initialize();

    void addListener(SimpleBattleEventListener listener);

    void removeListener(SimpleBattleEventListener listener);
}
