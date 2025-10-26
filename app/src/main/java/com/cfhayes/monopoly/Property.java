package com.cfhayes.monopoly;

/**
 * Represents a property that can be owned by players in Monopoly.
 * Includes regular properties, railroads, and utilities.
 */
public class Property extends BoardSpace {
    private int price;
    private int baseRent;
    private Player owner;
    private PropertyType type;
    private String colorGroup;
    private int houses;
    private boolean hasHotel;
    
    public enum PropertyType {
        REGULAR, RAILROAD, UTILITY
    }
    
    public Property(String name, int position, int price, int baseRent, PropertyType type, String colorGroup) {
        super(name, position);
        this.price = price;
        this.baseRent = baseRent;
        this.type = type;
        this.colorGroup = colorGroup;
        this.owner = null;
        this.houses = 0;
        this.hasHotel = false;
    }
    
    @Override
    public void handleLanding(Player player) {
        if (owner == null) {
            // Property is available for purchase
            System.out.println(name + " is available for $" + price);
        } else if (!owner.equals(player)) {
            // Player must pay rent
            int rent = calculateRent();
            player.subtractMoney(rent);
            owner.addMoney(rent);
            System.out.println("Paid $" + rent + " rent to " + owner.getName());
        }
    }
    
    /**
     * Calculate rent based on property type and development
     */
    public int calculateRent() {
        if (type == PropertyType.RAILROAD) {
            // Rent depends on number of railroads owned
            long railroadsOwned = owner.getProperties().stream()
                .filter(p -> p.getType() == PropertyType.RAILROAD)
                .count();
            return baseRent * (int) Math.pow(2, railroadsOwned - 1);
        } else if (type == PropertyType.UTILITY) {
            // Utility rent is typically based on dice roll (simplified here)
            long utilitiesOwned = owner.getProperties().stream()
                .filter(p -> p.getType() == PropertyType.UTILITY)
                .count();
            return utilitiesOwned == 1 ? 4 * 7 : 10 * 7; // Assuming average dice roll of 7
        } else {
            // Regular property
            if (hasHotel) {
                return baseRent * 32; // Hotel multiplier
            } else if (houses > 0) {
                return baseRent * (int) Math.pow(4, houses);
            } else if (ownsMonopoly()) {
                return baseRent * 2; // Double rent for monopoly
            } else {
                return baseRent;
            }
        }
    }
    
    /**
     * Check if owner has a monopoly on this color group
     */
    private boolean ownsMonopoly() {
        if (colorGroup == null || owner == null) return false;
        
        long propertiesInGroup = owner.getProperties().stream()
            .filter(p -> colorGroup.equals(p.getColorGroup()))
            .count();
            
        // This is simplified - in a real game you'd check total properties in the color group
        return propertiesInGroup >= getPropertiesInColorGroup();
    }
    
    /**
     * Get number of properties in this color group (simplified)
     */
    private int getPropertiesInColorGroup() {
        switch (colorGroup) {
            case "Brown":
            case "Dark Blue": 
                return 2;
            default: 
                return 3;
        }
    }
    
    // Getters and Setters
    public int getPrice() {
        return price;
    }
    
    public int getBaseRent() {
        return baseRent;
    }
    
    public int getRent() {
        return calculateRent();
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    public PropertyType getType() {
        return type;
    }
    
    public String getColorGroup() {
        return colorGroup;
    }
    
    public int getHouses() {
        return houses;
    }
    
    public void setHouses(int houses) {
        this.houses = Math.max(0, Math.min(4, houses));
        if (houses == 0) {
            this.hasHotel = false;
        }
    }
    
    public boolean hasHotel() {
        return hasHotel;
    }
    
    public void setHasHotel(boolean hasHotel) {
        this.hasHotel = hasHotel;
        if (hasHotel) {
            this.houses = 0; // Remove houses when hotel is built
        }
    }
    
    public boolean isOwned() {
        return owner != null;
    }
    
    @Override
    public String toString() {
        String ownerInfo = owner != null ? owner.getName() : "Unowned";
        return String.format("Property{name='%s', price=%d, owner='%s'}", name, price, ownerInfo);
    }
}