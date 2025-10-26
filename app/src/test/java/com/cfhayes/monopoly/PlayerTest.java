package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    
    private Player player;
    
    @BeforeEach
    void setUp() {
        player = new Player("Test Player", 1500);
    }
    
    @Test
    void testPlayerCreation() {
        assertEquals("Test Player", player.getName());
        assertEquals(1500, player.getMoney());
        assertEquals(0, player.getPosition());
        assertTrue(player.getProperties().isEmpty());
        assertFalse(player.isInJail());
        assertEquals(0, player.getJailTurns());
    }
    
    @Test
    void testAddMoney() {
        player.addMoney(200);
        assertEquals(1700, player.getMoney());
        
        player.addMoney(50);
        assertEquals(1750, player.getMoney());
    }
    
    @Test
    void testSubtractMoney() {
        player.subtractMoney(100);
        assertEquals(1400, player.getMoney());
        
        // Test preventing negative money
        player.subtractMoney(2000);
        assertEquals(0, player.getMoney());
    }
    
    @Test
    void testSetMoney() {
        player.setMoney(2000);
        assertEquals(2000, player.getMoney());
        
        player.setMoney(0);
        assertEquals(0, player.getMoney());
    }
    
    @Test
    void testPosition() {
        player.setPosition(15);
        assertEquals(15, player.getPosition());
        
        player.setPosition(39);
        assertEquals(39, player.getPosition());
    }
    
    @Test
    void testProperties() {
        Property property1 = new Property("Test Property 1", 1, 100, 10, Property.PropertyType.REGULAR, "Brown");
        Property property2 = new Property("Test Property 2", 2, 150, 15, Property.PropertyType.REGULAR, "Light Blue");
        
        player.addProperty(property1);
        assertEquals(1, player.getProperties().size());
        assertTrue(player.getProperties().contains(property1));
        
        player.addProperty(property2);
        assertEquals(2, player.getProperties().size());
        assertTrue(player.getProperties().contains(property2));
        
        player.removeProperty(property1);
        assertEquals(1, player.getProperties().size());
        assertFalse(player.getProperties().contains(property1));
        assertTrue(player.getProperties().contains(property2));
    }
    
    @Test
    void testJail() {
        assertFalse(player.isInJail());
        assertEquals(0, player.getJailTurns());
        
        player.setInJail(true);
        assertTrue(player.isInJail());
        
        player.incrementJailTurns();
        assertEquals(1, player.getJailTurns());
        
        player.incrementJailTurns();
        assertEquals(2, player.getJailTurns());
        
        player.setInJail(false);
        assertFalse(player.isInJail());
        assertEquals(0, player.getJailTurns()); // Should reset when leaving jail
    }
    
    @Test
    void testCanAfford() {
        assertTrue(player.canAfford(1500));
        assertTrue(player.canAfford(1000));
        assertTrue(player.canAfford(1));
        assertFalse(player.canAfford(1501));
        assertFalse(player.canAfford(2000));
    }
    
    @Test
    void testTotalAssets() {
        assertEquals(1500, player.getTotalAssets()); // Just starting money
        
        Property property = new Property("Test Property", 1, 200, 20, Property.PropertyType.REGULAR, "Brown");
        player.addProperty(property);
        
        assertEquals(1700, player.getTotalAssets()); // Money + property value
    }
    
    @Test
    void testIsBankrupt() {
        assertFalse(player.isBankrupt()); // Has money and no properties
        
        player.setMoney(0);
        assertTrue(player.isBankrupt()); // No money and no properties
        
        Property property = new Property("Test Property", 1, 200, 20, Property.PropertyType.REGULAR, "Brown");
        player.addProperty(property);
        assertFalse(player.isBankrupt()); // No money but has properties
        
        player.setMoney(100);
        assertFalse(player.isBankrupt()); // Has money and properties
    }
    
    @Test
    void testEqualsAndHashCode() {
        Player player1 = new Player("John", 1500);
        Player player2 = new Player("John", 2000); // Same name, different money
        Player player3 = new Player("Jane", 1500);
        
        assertEquals(player1, player2); // Same name
        assertNotEquals(player1, player3); // Different name
        assertEquals(player1.hashCode(), player2.hashCode()); // Same hash for same name
    }
    
    @Test
    void testToString() {
        String expected = "Player{name='Test Player', money=1500, position=0, properties=0}";
        assertEquals(expected, player.toString());
        
        player.setPosition(10);
        player.setMoney(2000);
        Property property = new Property("Test Property", 1, 200, 20, Property.PropertyType.REGULAR, "Brown");
        player.addProperty(property);
        
        String expectedModified = "Player{name='Test Player', money=2000, position=10, properties=1}";
        assertEquals(expectedModified, player.toString());
    }
}