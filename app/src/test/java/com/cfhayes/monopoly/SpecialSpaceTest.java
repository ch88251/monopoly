package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpecialSpaceTest {
    
    private Player player;
    
    @BeforeEach
    void setUp() {
        player = new Player("Test Player", 1500);
    }
    
    @Test
    void testGoSpace() {
        SpecialSpace go = new SpecialSpace("GO", 0);
        assertEquals("GO", go.getName());
        assertEquals(0, go.getPosition());
        
        int initialMoney = player.getMoney();
        go.handleLanding(player);
        
        // Should collect $200 for landing on GO
        assertEquals(initialMoney + 200, player.getMoney());
    }
    
    @Test
    void testIncomeTaxSpace() {
        SpecialSpace incomeTax = new SpecialSpace("Income Tax", 4);
        assertEquals("Income Tax", incomeTax.getName());
        assertEquals(4, incomeTax.getPosition());
        
        int initialMoney = player.getMoney();
        incomeTax.handleLanding(player);
        
        // Should pay $200 income tax
        assertEquals(initialMoney - 200, player.getMoney());
    }
    
    @Test
    void testLuxuryTaxSpace() {
        SpecialSpace luxuryTax = new SpecialSpace("Luxury Tax", 38);
        assertEquals("Luxury Tax", luxuryTax.getName());
        assertEquals(38, luxuryTax.getPosition());
        
        int initialMoney = player.getMoney();
        luxuryTax.handleLanding(player);
        
        // Should pay $100 luxury tax
        assertEquals(initialMoney - 100, player.getMoney());
    }
    
    @Test
    void testGoToJailSpace() {
        SpecialSpace goToJail = new SpecialSpace("Go to Jail", 30);
        assertEquals("Go to Jail", goToJail.getName());
        assertEquals(30, goToJail.getPosition());
        
        player.setPosition(25); // Start somewhere else
        assertFalse(player.isInJail());
        
        goToJail.handleLanding(player);
        
        // Should be sent to jail
        assertEquals(10, player.getPosition()); // Jail position
        assertTrue(player.isInJail());
    }
    
    @Test
    void testJailSpace() {
        SpecialSpace jail = new SpecialSpace("Jail", 10);
        assertEquals("Jail", jail.getName());
        assertEquals(10, jail.getPosition());
        
        int initialMoney = player.getMoney();
        int initialPosition = player.getPosition();
        boolean initialJailStatus = player.isInJail();
        
        jail.handleLanding(player);
        
        // Just visiting jail - no changes should occur
        assertEquals(initialMoney, player.getMoney());
        assertEquals(initialPosition, player.getPosition());
        assertEquals(initialJailStatus, player.isInJail());
    }
    
    @Test
    void testFreeParkingSpace() {
        SpecialSpace freeParking = new SpecialSpace("Free Parking", 20);
        assertEquals("Free Parking", freeParking.getName());
        assertEquals(20, freeParking.getPosition());
        
        int initialMoney = player.getMoney();
        int initialPosition = player.getPosition();
        
        freeParking.handleLanding(player);
        
        // Free parking - no changes should occur in standard rules
        assertEquals(initialMoney, player.getMoney());
        assertEquals(initialPosition, player.getPosition());
    }
    
    @Test
    void testCommunityChestSpace() {
        SpecialSpace communityChest = new SpecialSpace("Community Chest", 2);
        assertEquals("Community Chest", communityChest.getName());
        assertEquals(2, communityChest.getPosition());
        
        int initialMoney = player.getMoney();
        
        // Should not throw exception (cards not implemented yet)
        assertDoesNotThrow(() -> communityChest.handleLanding(player));
        
        // Money should not change since cards aren't implemented
        assertEquals(initialMoney, player.getMoney());
    }
    
    @Test
    void testChanceSpace() {
        SpecialSpace chance = new SpecialSpace("Chance", 7);
        assertEquals("Chance", chance.getName());
        assertEquals(7, chance.getPosition());
        
        int initialMoney = player.getMoney();
        
        // Should not throw exception (cards not implemented yet)
        assertDoesNotThrow(() -> chance.handleLanding(player));
        
        // Money should not change since cards aren't implemented
        assertEquals(initialMoney, player.getMoney());
    }
    
    @Test
    void testUnknownSpecialSpace() {
        SpecialSpace unknownSpace = new SpecialSpace("Unknown Space", 99);
        assertEquals("Unknown Space", unknownSpace.getName());
        assertEquals(99, unknownSpace.getPosition());
        
        int initialMoney = player.getMoney();
        int initialPosition = player.getPosition();
        
        // Should not throw exception for unknown spaces
        assertDoesNotThrow(() -> unknownSpace.handleLanding(player));
        
        // Should not change player state for unknown spaces
        assertEquals(initialMoney, player.getMoney());
        assertEquals(initialPosition, player.getPosition());
    }
    
    @Test
    void testCaseInsensitiveSpaceNames() {
        // Test that space names are handled case-insensitively
        SpecialSpace goUpper = new SpecialSpace("GO", 0);
        SpecialSpace goLower = new SpecialSpace("go", 0);
        SpecialSpace goMixed = new SpecialSpace("Go", 0);
        
        int initialMoney = player.getMoney();
        
        // All variations should work the same way
        goUpper.handleLanding(player);
        int afterFirst = player.getMoney();
        assertEquals(initialMoney + 200, afterFirst);
        
        goLower.handleLanding(player);
        int afterSecond = player.getMoney();
        assertEquals(afterFirst + 200, afterSecond);
        
        goMixed.handleLanding(player);
        int afterThird = player.getMoney();
        assertEquals(afterSecond + 200, afterThird);
    }
    
    @Test
    void testTaxesWithInsufficientFunds() {
        // Set player to have less money than taxes
        player.setMoney(50);
        
        SpecialSpace incomeTax = new SpecialSpace("Income Tax", 4);
        incomeTax.handleLanding(player);
        
        // Player should have $0 (not negative) due to subtractMoney protection
        assertEquals(0, player.getMoney());
        
        // Reset and test luxury tax
        player.setMoney(50);
        SpecialSpace luxuryTax = new SpecialSpace("Luxury Tax", 38);
        luxuryTax.handleLanding(player);
        
        // Should pay what they can and end up with $0
        assertEquals(0, player.getMoney());
    }
}