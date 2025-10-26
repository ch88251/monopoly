package com.cfhayes.monopoly;

import java.util.Random;

/**
 * Represents a pair of dice for the Monopoly game.
 */
public class Dice {
    private Random random;
    private int lastRoll1;
    private int lastRoll2;
    private int doublesCount;
    
    public Dice() {
        this.random = new Random();
        this.doublesCount = 0;
    }
    
    /**
     * Constructor for testing with a specific seed
     */
    public Dice(long seed) {
        this.random = new Random(seed);
        this.doublesCount = 0;
    }
    
    /**
     * Roll both dice and return the total
     */
    public int roll() {
        lastRoll1 = random.nextInt(6) + 1; // 1-6
        lastRoll2 = random.nextInt(6) + 1; // 1-6
        
        if (isDoubles()) {
            doublesCount++;
        } else {
            doublesCount = 0;
        }
        
        return getTotal();
    }
    
    /**
     * Get the total of the last roll
     */
    public int getTotal() {
        return lastRoll1 + lastRoll2;
    }
    
    /**
     * Get the value of the first die from last roll
     */
    public int getDie1() {
        return lastRoll1;
    }
    
    /**
     * Get the value of the second die from last roll
     */
    public int getDie2() {
        return lastRoll2;
    }
    
    /**
     * Check if the last roll was doubles
     */
    public boolean isDoubles() {
        return lastRoll1 == lastRoll2;
    }
    
    /**
     * Get the number of consecutive doubles rolled
     */
    public int getDoublesCount() {
        return doublesCount;
    }
    
    /**
     * Reset the doubles count (called when player doesn't roll doubles)
     */
    public void resetDoublesCount() {
        this.doublesCount = 0;
    }
    
    /**
     * Check if player should go to jail for rolling three doubles
     */
    public boolean shouldGoToJail() {
        return doublesCount >= 3;
    }
    
    @Override
    public String toString() {
        return String.format("Dice rolled: %d + %d = %d%s", 
                           lastRoll1, lastRoll2, getTotal(),
                           isDoubles() ? " (DOUBLES!)" : "");
    }
}