package com.hive.robomon.test;

import com.hive.robomon.battle.SimpleBattleManager;
import com.hive.robomon.battle.impl.SimpleBattleManagerImpl;
import com.hive.robomon.fighter.impl.SimpleFighter;
import com.hive.robomon.sample.fighter.HighlyDefensiveFighter;
import com.hive.robomon.sample.fighter.HighlyOffensiveFighter;

public class SimpleBattleRunner {

    public static void main(String[] args) {

        SimpleFighter fighter1 = new HighlyOffensiveFighter();

        SimpleFighter fighter2 = new HighlyDefensiveFighter();

        SimpleBattleManager manager = new SimpleBattleManagerImpl(fighter1, fighter2);

        Thread managerThread = new Thread(manager);
        managerThread.setName("manager");
        managerThread.start();
    }
}
