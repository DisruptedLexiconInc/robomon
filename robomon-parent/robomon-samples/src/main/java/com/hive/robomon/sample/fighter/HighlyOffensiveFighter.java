package com.hive.robomon.sample.fighter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hive.robomon.battle.SimpleBattleManager;
import com.hive.robomon.event.BattleEndEvent;
import com.hive.robomon.event.BattleStartEvent;
import com.hive.robomon.event.RoundEvent;
import com.hive.robomon.fighter.Fighter;
import com.hive.robomon.fighter.FightingType;
import com.hive.robomon.fighter.impl.SimpleFighterBase;

public class HighlyOffensiveFighter extends SimpleFighterBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(HighlyOffensiveFighter.class);

    private Fighter opponent;

    protected AtomicBoolean roundOver = new AtomicBoolean(false);

    public HighlyOffensiveFighter() {
        super("HighlyOffensiveFighter", new Random().nextInt(50) + 20, new Random().nextInt(500), FightingType.OFFENSIVE);
    }

    @Override
    public void run() {
        LOGGER.trace("Running this thread.");

        while (!roundOver.get()) {
            if (isFightStarted() && (opponent != null) && (getHealth() != 0)) {
                SimpleBattleManager simple = (SimpleBattleManager) getBattleManager();
                simple.hit(this, opponent);
            }

            if (getHealth() == 0) {
                LOGGER.trace("No health. Match is over.");
                roundOver.set(true);
            }
        }

    }

    @Override
    public void onBattleEnd(BattleEndEvent event) {
        LOGGER.trace("{}", (event.getWinner().equals(this) ? "I win!!!" : "Dammit I lost!"));
        roundOver.set(true);
    }

    @Override
    public void onBattleStart(BattleStartEvent event) {
        opponent = event.getFighter1().equals(this) ? event.getFighter2() : event.getFighter1();
        if (!isFightStarted()) {
            setFightStarted(true);
        }
    }

    @Override
    public void onRoundStart(RoundEvent event) {
        Fighter opponent = (event.getFighter1().equals(this)) ? event.getFighter2() : event.getFighter1();

        LOGGER.trace("About to start pulverizing {} with health {}", opponent.getName(), opponent.getType());
    }

}
