package com.hive.robomon.battle.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.battle.SimpleBattleManager;
import com.hive.robomon.event.BattleEndEvent;
import com.hive.robomon.event.BattleStartEvent;
import com.hive.robomon.event.RoundEvent;
import com.hive.robomon.event.SimpleBattleEventListener;
import com.hive.robomon.fighter.Fighter;
import com.hive.robomon.fighter.impl.SimpleFighter;

public class SimpleBattleManagerImpl implements SimpleBattleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleBattleManagerImpl.class);

    private final List<SimpleBattleEventListener> battleListenerList = new ArrayList<SimpleBattleEventListener>();

    private volatile AtomicBoolean fightOver = new AtomicBoolean(false);

    private volatile SimpleFighter fighter1;

    private volatile SimpleFighter fighter2;

    private volatile RoundContext currentRound;

    Thread thread1;
    Thread thread2;

    public SimpleBattleManagerImpl(SimpleFighter fighter1, SimpleFighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;

    }

    @Override
    public void initialize() {

        LOGGER.info("Starting battle between {} and {}", fighter1.toString(), fighter2.toString());

        thread1 = new Thread(fighter1);
        thread2 = new Thread(fighter2);
        thread1.setName("FighterThread1");
        thread2.setName("FighterThread2");

        thread1.start();
        thread2.start();

        fighter1.setBattleManager(this);
        fighter2.setBattleManager(this);
        addListener(fighter1);
        addListener(fighter2);

    }

    @Override
    public void run() {

        initialize();

        currentRound = new RoundContext();
        long roundStartTime = System.currentTimeMillis();

        alertBattleStart();

        while (!fightOver.get()) {

            LOGGER.info("Starting round: at {}", roundStartTime);
            LOGGER.trace("Fighter1: {}\nFighter2: {}", fighter1.toString(), fighter2.toString());

            RoundEvent event = new RoundEvent(fighter1, fighter2);

            for (SimpleBattleEventListener listener : battleListenerList) {
                listener.onRoundStart(event);
            }

            boolean roundComplete = false;

            while (!roundComplete) {

                roundComplete = completeRound(roundStartTime);
                LOGGER.trace("RoundComplete?: {}", roundComplete);
            }

            currentRound.reset();
            roundStartTime = System.currentTimeMillis();
        }

        alertBattleOver();
    }

    protected boolean completeRound(long roundStartTime) {
        LOGGER.trace("CurrentRound: {}", currentRound.toString());
        return (currentRound.isFighter1Attacked() && currentRound.isFighter2Attacked()) || ((System.currentTimeMillis() - roundStartTime) > 10000)
                        || fightOver.get();
    }

    protected void alertBattleStart() {
        BattleStartEvent event = new BattleStartEvent(fighter1, fighter2);

        for (SimpleBattleEventListener listener : battleListenerList) {
            listener.onBattleStart(event);
        }
    }

    protected void alertBattleOver() {

        SimpleFighter winner;
        SimpleFighter loser;

        if (fighter1.getHealth() == 0) {
            winner = fighter2;
            loser = fighter1;
        } else {
            winner = fighter1;
            loser = fighter2;
        }

        BattleEndEvent event = new BattleEndEvent(winner, loser);

        for (SimpleBattleEventListener listener : battleListenerList) {
            listener.onBattleEnd(event);
        }
    }

    @Override
    public void addListener(SimpleBattleEventListener listener) {
        if (!battleListenerList.contains(listener)) {
            battleListenerList.add(listener);
        }

    }

    @Override
    public void removeListener(SimpleBattleEventListener listener) {
        battleListenerList.remove(listener);
    }

    @Override
    public synchronized void hit(Fighter aggressor, Fighter opponent) {
        if ((aggressor != opponent) && (aggressor.getHealth() != 0)) {

            if (aggressor.equals(fighter1)) {

                if (!currentRound.isFighter1Attacked()) {
                    LOGGER.debug("{} is hitting {}", aggressor.getName(), opponent.getName());

                    currentRound.setFighter1Attacked(true);
                    if (opponent.getHealth() <= aggressor.getAttackPower()) {
                        opponent.setHealth(0);
                        LOGGER.debug("{} has killed {}", aggressor.getName(), opponent.getName());
                        LOGGER.debug("{} remaining hp is {}", opponent.getName(), opponent.getHealth());

                        fightOver.set(true);
                    } else {
                        opponent.setHealth(opponent.getHealth() - aggressor.getAttackPower());
                        LOGGER.debug("{} remaining hp is {}", opponent.getName(), opponent.getHealth());
                    }
                }

            } else {

                if (!currentRound.isFighter2Attacked()) {
                    LOGGER.debug("{} is hitting {}.", aggressor.getName(), opponent.getName());

                    currentRound.setFighter2Attacked(true);
                    if (opponent.getHealth() <= aggressor.getAttackPower()) {
                        opponent.setHealth(0);
                        LOGGER.debug("{} has killed {}", aggressor.getName(), opponent.getName());
                        LOGGER.debug("{} remaining hp is {}", opponent.getName(), opponent.getHealth());

                        fightOver.set(true);
                    } else {
                        opponent.setHealth(opponent.getHealth() - aggressor.getAttackPower());
                        LOGGER.debug("{} remaining hp is {}", opponent.getName(), opponent.getHealth());
                    }
                }

            }

        }
    }
}

class RoundContext {

    private volatile AtomicBoolean fighter1Attacked = new AtomicBoolean(false);

    private volatile AtomicBoolean fighter2Attacked = new AtomicBoolean(false);

    public synchronized boolean isFighter1Attacked() {
        return fighter1Attacked.get();
    }

    public void reset() {
        fighter1Attacked.set(false);
        fighter2Attacked.set(false);

    }

    public synchronized void setFighter1Attacked(boolean fighter1Attacked) {
        this.fighter1Attacked.set(fighter1Attacked);
    }

    public synchronized boolean isFighter2Attacked() {
        return fighter2Attacked.get();
    }

    public synchronized void setFighter2Attacked(boolean fighter2Attacked) {
        this.fighter2Attacked.set(fighter2Attacked);
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
