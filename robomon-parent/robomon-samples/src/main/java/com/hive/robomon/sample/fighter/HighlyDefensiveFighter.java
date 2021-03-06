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
import com.hive.robomon.fighter.impl.SimpleFighter;
import com.hive.robomon.fighter.impl.SimpleFighterBase;

public class HighlyDefensiveFighter extends SimpleFighterBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(HighlyDefensiveFighter.class);

    private SimpleFighter opponent;

    protected AtomicBoolean roundOver = new AtomicBoolean(false);

    public HighlyDefensiveFighter() {
        super("HighlyDefensiveFighter", new Random().nextInt(1000) + 500, new Random().nextInt(20), FightingType.DEFENSIVE);
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
        opponent = event.getFighter1().equals(this) ? (SimpleFighter) event.getFighter2() : (SimpleFighter) event.getFighter1();

        if (!isFightStarted()) {
            setFightStarted(true);
        }
    }

    @Override
    public void onRoundStart(RoundEvent event) {
        Fighter opponent = (event.getFighter1().equals(this)) ? event.getFighter2() : event.getFighter1();
    }

}
