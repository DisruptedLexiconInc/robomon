package com.hive.robomon.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hive.robomon.fighter.Fighter;

public class BattleEndEvent {

    private Fighter winner;

    private Fighter loser;

    private long time;

    public BattleEndEvent() {
        time = System.currentTimeMillis();
    }

    public BattleEndEvent(Fighter winner, Fighter loser) {
        this();
        this.winner = winner;
        this.loser = loser;
    }

    public Fighter getWinner() {
        return winner;
    }

    public void setWinner(Fighter winner) {
        this.winner = winner;
    }

    public Fighter getLoser() {
        return loser;
    }

    public void setLoser(Fighter loser) {
        this.loser = loser;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
