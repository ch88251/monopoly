package com.cfhayes.monopoly;

/**
 * Abstract base class for all Monopoly cards (Chance and Community Chest)
 */
public abstract class Card {
    protected String description;
    protected CardType type;
    
    public enum CardType {
        CHANCE, COMMUNITY_CHEST
    }
    
    public Card(String description, CardType type) {
        this.description = description;
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public CardType getType() {
        return type;
    }
    
    /**
     * Execute the card's action on the given player and game
     */
    public abstract void execute(Player player, Game game);
    
    @Override
    public String toString() {
        return String.format("%s Card: %s", type, description);
    }
}