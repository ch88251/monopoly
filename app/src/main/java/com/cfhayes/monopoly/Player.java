package com.cfhayes.monopoly;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Monopoly game.
 * Manages player's money, properties, and position on the board.
 */
public class Player {
    private String name;
    private int money;
    private int position;
    private List<Property> properties;
    private boolean inJail;
    private int jailTurns;
    
    public Player(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
        this.position = 0; // Start at GO
        this.properties = new ArrayList<>();
        this.inJail = false;
        this.jailTurns = 0;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public void addMoney(int amount) {
        this.money += amount;
    }
    
    public void subtractMoney(int amount) {
        this.money -= amount;
        if (this.money < 0) {
            this.money = 0; // Prevent negative money
        }
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    public List<Property> getProperties() {
        return new ArrayList<>(properties);
    }
    
    public void addProperty(Property property) {
        properties.add(property);
    }
    
    public void removeProperty(Property property) {
        properties.remove(property);
    }
    
    public boolean isInJail() {
        return inJail;
    }
    
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        if (!inJail) {
            this.jailTurns = 0;
        }
    }
    
    public int getJailTurns() {
        return jailTurns;
    }
    
    public void incrementJailTurns() {
        this.jailTurns++;
    }
    
    /**
     * Check if player can afford a purchase
     */
    public boolean canAfford(int cost) {
        return money >= cost;
    }
    
    /**
     * Get total value of player's assets (money + property values)
     */
    public int getTotalAssets() {
        int propertyValue = properties.stream()
            .mapToInt(Property::getPrice)
            .sum();
        return money + propertyValue;
    }
    
    /**
     * Check if player is bankrupt
     */
    public boolean isBankrupt() {
        return money <= 0 && properties.isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Player{name='%s', money=%d, position=%d, properties=%d}", 
                           name, money, position, properties.size());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}