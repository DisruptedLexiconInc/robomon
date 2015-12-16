package com.hive.robomon.event;

public interface SimpleBattleEventListener {

    void onBattleStart(BattleStartEvent event);

    void onRoundStart(RoundEvent event);

    void onBattleEnd(BattleEndEvent event);
}
