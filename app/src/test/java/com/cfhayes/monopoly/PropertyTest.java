package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    
    private Property regularProperty;
    private Property railroad;
    private Property utility;
    private Player owner;
    
    @BeforeEach
    void setUp() {
        regularProperty = new Property("Park Place", 37, 350, 35, Property.PropertyType.REGULAR, "Dark Blue");
        railroad = new Property("Reading Railroad", 5, 200, 25, Property.PropertyType.RAILROAD, "Railroad");
        utility = new Property("Electric Company", 12, 150, 0, Property.PropertyType.UTILITY, "Utility");
        owner = new Player("Owner", 2000);
    }
    
    @Test
    void testPropertyCreation() {
        assertEquals("Park Place", regularProperty.getName());
        assertEquals(37, regularProperty.getPosition());
        assertEquals(350, regularProperty.getPrice());
        assertEquals(Property.PropertyType.REGULAR, regularProperty.getType());
        assertEquals("Dark Blue", regularProperty.getColorGroup());
        assertNull(regularProperty.getOwner());
        assertFalse(regularProperty.isOwned());
        assertEquals(0, regularProperty.getHouses());
        assertFalse(regularProperty.hasHotel());
    }
    
    @Test
    void testOwnership() {
        assertFalse(regularProperty.isOwned());
        
        regularProperty.setOwner(owner);
        assertTrue(regularProperty.isOwned());
        assertEquals(owner, regularProperty.getOwner());
    }
    
    @Test
    void testHousesAndHotels() {
        // Test setting houses
        regularProperty.setHouses(2);
        assertEquals(2, regularProperty.getHouses());
        assertFalse(regularProperty.hasHotel());
        
        // Test maximum houses (4)
        regularProperty.setHouses(5);
        assertEquals(4, regularProperty.getHouses()); // Should be capped at 4
        
        // Test minimum houses (0)
        regularProperty.setHouses(-1);
        assertEquals(0, regularProperty.getHouses()); // Should not go below 0
        
        // Test hotel
        regularProperty.setHouses(4);
        regularProperty.setHasHotel(true);
        assertTrue(regularProperty.hasHotel());
        assertEquals(0, regularProperty.getHouses()); // Houses removed when hotel built
        
        // Test removing hotel
        regularProperty.setHasHotel(false);
        assertFalse(regularProperty.hasHotel());
    }
    
    @Test
    void testRegularPropertyRentCalculation() {
        regularProperty.setOwner(owner);
        
        // Base rent (no monopoly, no houses)
        assertEquals(35, regularProperty.calculateRent());
        
        // Test rent with houses
        regularProperty.setHouses(1);
        assertEquals(140, regularProperty.calculateRent()); // 35 * 4^1
        
        regularProperty.setHouses(2);
        assertEquals(560, regularProperty.calculateRent()); // 35 * 4^2
        
        regularProperty.setHouses(3);
        assertEquals(2240, regularProperty.calculateRent()); // 35 * 4^3
        
        regularProperty.setHouses(4);
        assertEquals(8960, regularProperty.calculateRent()); // 35 * 4^4
        
        // Test hotel rent
        regularProperty.setHasHotel(true);
        assertEquals(1120, regularProperty.calculateRent()); // 35 * 32
    }
    
    @Test
    void testRailroadRentCalculation() {
        railroad.setOwner(owner);
        owner.addProperty(railroad); // Add railroad to owner's property list
        
        // One railroad owned
        assertEquals(25, railroad.calculateRent()); // base rent
        
        // Simulate owning multiple railroads by adding them to player
        Property railroad2 = new Property("Pennsylvania Railroad", 15, 200, 25, Property.PropertyType.RAILROAD, "Railroad");
        railroad2.setOwner(owner);
        owner.addProperty(railroad2);
        
        assertEquals(50, railroad.calculateRent()); // 25 * 2^(2-1) = 25 * 2
    }
    
    @Test
    void testUtilityRentCalculation() {
        utility.setOwner(owner);
        owner.addProperty(utility);
        
        // One utility owned (simplified calculation)
        assertEquals(28, utility.calculateRent()); // 4 * 7 (average dice roll)
        
        // Two utilities owned
        Property utility2 = new Property("Water Works", 28, 150, 0, Property.PropertyType.UTILITY, "Utility");
        utility2.setOwner(owner);
        owner.addProperty(utility2);
        
        assertEquals(70, utility.calculateRent()); // 10 * 7 (average dice roll)
    }
    
    @Test
    void testHandleLandingUnowned() {
        Player player = new Player("Test Player", 1500);
        
        // Capture output would require a more complex setup, so we'll just test that no exception is thrown
        assertDoesNotThrow(() -> regularProperty.handleLanding(player));
    }
    
    @Test
    void testHandleLandingOwned() {
        Player player = new Player("Test Player", 1500);
        regularProperty.setOwner(owner);
        owner.addProperty(regularProperty);
        
        int initialPlayerMoney = player.getMoney();
        int initialOwnerMoney = owner.getMoney();
        int rent = regularProperty.calculateRent();
        
        regularProperty.handleLanding(player);
        
        assertEquals(initialPlayerMoney - rent, player.getMoney());
        assertEquals(initialOwnerMoney + rent, owner.getMoney());
    }
    
    @Test
    void testHandleLandingOwnProperty() {
        // Player lands on their own property - should not pay rent
        regularProperty.setOwner(owner);
        owner.addProperty(regularProperty);
        
        int initialMoney = owner.getMoney();
        regularProperty.handleLanding(owner);
        
        assertEquals(initialMoney, owner.getMoney()); // Money should not change
    }
    
    @Test
    void testToString() {
        String expectedUnowned = "Property{name='Park Place', price=350, owner='Unowned'}";
        assertEquals(expectedUnowned, regularProperty.toString());
        
        regularProperty.setOwner(owner);
        String expectedOwned = "Property{name='Park Place', price=350, owner='Owner'}";
        assertEquals(expectedOwned, regularProperty.toString());
    }
    
    @Test
    void testPlayerCanAffordProperty() {
        Player richPlayer = new Player("Rich Player", 1000);
        Player poorPlayer = new Player("Poor Player", 100);
        
        Property expensiveProperty = new Property("Expensive Property", 1, 500, 50, Property.PropertyType.REGULAR, "Blue");
        Property cheapProperty = new Property("Cheap Property", 2, 80, 8, Property.PropertyType.REGULAR, "Brown");
        
        // Rich player can afford expensive property
        assertTrue(richPlayer.canAfford(expensiveProperty.getPrice()));
        assertTrue(richPlayer.canAfford(cheapProperty.getPrice()));
        
        // Poor player can only afford cheap property
        assertFalse(poorPlayer.canAfford(expensiveProperty.getPrice()));
        assertTrue(poorPlayer.canAfford(cheapProperty.getPrice()));
        
        // Exact money amount
        Player exactPlayer = new Player("Exact Player", 80);
        assertTrue(exactPlayer.canAfford(cheapProperty.getPrice()));
        assertFalse(exactPlayer.canAfford(expensiveProperty.getPrice()));
    }
}