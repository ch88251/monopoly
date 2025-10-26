package com.cfhayes.monopoly;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Monopoly game board with all 40 spaces.
 */
public class Board {
    private List<BoardSpace> spaces;
    
    public Board() {
        initializeSpaces();
    }
    
    /**
     * Initialize all 40 spaces on the Monopoly board
     */
    private void initializeSpaces() {
        spaces = new ArrayList<>(40);
        
        // Corner and special spaces
        spaces.add(new SpecialSpace("GO", 0));
        spaces.add(new Property("Mediterranean Avenue", 1, 60, 2, Property.PropertyType.REGULAR, "Brown"));
        spaces.add(new SpecialSpace("Community Chest", 2));
        spaces.add(new Property("Baltic Avenue", 3, 60, 4, Property.PropertyType.REGULAR, "Brown"));
        spaces.add(new SpecialSpace("Income Tax", 4));
        spaces.add(new Property("Reading Railroad", 5, 200, 25, Property.PropertyType.RAILROAD, "Railroad"));
        spaces.add(new Property("Oriental Avenue", 6, 100, 6, Property.PropertyType.REGULAR, "Light Blue"));
        spaces.add(new SpecialSpace("Chance", 7));
        spaces.add(new Property("Vermont Avenue", 8, 100, 6, Property.PropertyType.REGULAR, "Light Blue"));
        spaces.add(new Property("Connecticut Avenue", 9, 120, 8, Property.PropertyType.REGULAR, "Light Blue"));
        
        spaces.add(new SpecialSpace("Jail", 10));
        spaces.add(new Property("St. Charles Place", 11, 140, 10, Property.PropertyType.REGULAR, "Pink"));
        spaces.add(new Property("Electric Company", 12, 150, 0, Property.PropertyType.UTILITY, "Utility"));
        spaces.add(new Property("States Avenue", 13, 140, 10, Property.PropertyType.REGULAR, "Pink"));
        spaces.add(new Property("Virginia Avenue", 14, 160, 12, Property.PropertyType.REGULAR, "Pink"));
        spaces.add(new Property("Pennsylvania Railroad", 15, 200, 25, Property.PropertyType.RAILROAD, "Railroad"));
        spaces.add(new Property("St. James Place", 16, 180, 14, Property.PropertyType.REGULAR, "Orange"));
        spaces.add(new SpecialSpace("Community Chest", 17));
        spaces.add(new Property("Tennessee Avenue", 18, 180, 14, Property.PropertyType.REGULAR, "Orange"));
        spaces.add(new Property("New York Avenue", 19, 200, 16, Property.PropertyType.REGULAR, "Orange"));
        
        spaces.add(new SpecialSpace("Free Parking", 20));
        spaces.add(new Property("Kentucky Avenue", 21, 220, 18, Property.PropertyType.REGULAR, "Red"));
        spaces.add(new SpecialSpace("Chance", 22));
        spaces.add(new Property("Indiana Avenue", 23, 220, 18, Property.PropertyType.REGULAR, "Red"));
        spaces.add(new Property("Illinois Avenue", 24, 240, 20, Property.PropertyType.REGULAR, "Red"));
        spaces.add(new Property("B&O Railroad", 25, 200, 25, Property.PropertyType.RAILROAD, "Railroad"));
        spaces.add(new Property("Atlantic Avenue", 26, 260, 22, Property.PropertyType.REGULAR, "Yellow"));
        spaces.add(new Property("Ventnor Avenue", 27, 260, 22, Property.PropertyType.REGULAR, "Yellow"));
        spaces.add(new Property("Water Works", 28, 150, 0, Property.PropertyType.UTILITY, "Utility"));
        spaces.add(new Property("Marvin Gardens", 29, 280, 24, Property.PropertyType.REGULAR, "Yellow"));
        
        spaces.add(new SpecialSpace("Go to Jail", 30));
        spaces.add(new Property("Pacific Avenue", 31, 300, 26, Property.PropertyType.REGULAR, "Green"));
        spaces.add(new Property("North Carolina Avenue", 32, 300, 26, Property.PropertyType.REGULAR, "Green"));
        spaces.add(new SpecialSpace("Community Chest", 33));
        spaces.add(new Property("Pennsylvania Avenue", 34, 320, 28, Property.PropertyType.REGULAR, "Green"));
        spaces.add(new Property("Short Line", 35, 200, 25, Property.PropertyType.RAILROAD, "Railroad"));
        spaces.add(new SpecialSpace("Chance", 36));
        spaces.add(new Property("Park Place", 37, 350, 35, Property.PropertyType.REGULAR, "Dark Blue"));
        spaces.add(new SpecialSpace("Luxury Tax", 38));
        spaces.add(new Property("Boardwalk", 39, 400, 50, Property.PropertyType.REGULAR, "Dark Blue"));
    }
    
    /**
     * Get the board space at a specific position
     */
    public BoardSpace getSpace(int position) {
        if (position < 0 || position >= spaces.size()) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        return spaces.get(position);
    }
    
    /**
     * Get all spaces on the board
     */
    public List<BoardSpace> getAllSpaces() {
        return new ArrayList<>(spaces);
    }
    
    /**
     * Get all properties on the board
     */
    public List<Property> getAllProperties() {
        return spaces.stream()
            .filter(space -> space instanceof Property)
            .map(space -> (Property) space)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get properties by color group
     */
    public List<Property> getPropertiesByColorGroup(String colorGroup) {
        return getAllProperties().stream()
            .filter(property -> colorGroup.equals(property.getColorGroup()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get the total number of spaces
     */
    public int getSize() {
        return spaces.size();
    }
}