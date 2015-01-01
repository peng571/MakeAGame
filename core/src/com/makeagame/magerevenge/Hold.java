package com.makeagame.magerevenge;

import java.util.ArrayList;

import com.makeagame.core.component.Position;

public class Hold {
    String screen;
    boolean isStoreOpen;
    long currentTime;

    int money;
    int[] resource;
    SendCard[] sendcard;
    ArrayList<Unit> soldier;
    Unit[] castle;
    long powerApplyTime;
    float powerCD;

    public static class SendCard {
        float sendCD;
        boolean locked;
        int costMoney;
        int[] costResource;
        String type;
        int strongLevel;

    }

    public static class Unit {
        int group;
        Position pos;
        int stateRecord;
        long lastWalkTime;
        long lastPreparingTime;
        long lastAttackTime;
        long lastBackingTime;
        long lastDeathTime;
        float hpp;
        ArrayList<Hurt> hurtRecord;
        String type;
        int strongLevel;
    }

    public static class Hurt {
        long time;
        int amont;

        public Hurt(long time, int amont) {
            this.time = time;
            this.amont = amont;
        }
    }

}
