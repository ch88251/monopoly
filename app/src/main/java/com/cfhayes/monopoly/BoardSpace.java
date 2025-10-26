package com.cfhayes.monopoly;

/**
 * Abstract base class for all board spaces in Monopoly.
 */
public abstract class BoardSpace {
    protected String name;
    protected int position;
    
    public BoardSpace(String name, int position) {
        this.name = name;
        this.position = position;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPosition() {
        return position;
    }
    
    /**
     * Handle what happens when a player lands on this space
     */
    public abstract void handleLanding(Player player);
}