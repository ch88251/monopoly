package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    
    private Dice dice;
    
    @BeforeEach
    void setUp() {
        // Use a seed for predictable testing
        dice = new Dice(12345);
    }
    
    @Test
    void testDiceCreation() {
        Dice newDice = new Dice();
        assertNotNull(newDice);
        assertEquals(0, newDice.getDoublesCount());
    }
    
    @Test
    void testRollReturnsValidValues() {
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertTrue(roll >= 2 && roll <= 12, "Roll should be between 2 and 12, got: " + roll);
            
            int die1 = dice.getDie1();
            int die2 = dice.getDie2();
            assertTrue(die1 >= 1 && die1 <= 6, "Die 1 should be between 1 and 6, got: " + die1);
            assertTrue(die2 >= 1 && die2 <= 6, "Die 2 should be between 1 and 6, got: " + die2);
            
            assertEquals(die1 + die2, roll, "Total should equal sum of individual dice");
            assertEquals(die1 + die2, dice.getTotal(), "getTotal() should match sum");
        }
    }
    
    @Test
    void testDoublesDetection() {
        // Test with a specific seed to ensure we get some doubles
        Dice testDice = new Dice(42);
        
        boolean foundDoubles = false;
        boolean foundNonDoubles = false;
        
        for (int i = 0; i < 50; i++) {
            testDice.roll();
            if (testDice.isDoubles()) {
                foundDoubles = true;
                assertEquals(testDice.getDie1(), testDice.getDie2(), "Doubles should have equal dice values");
            } else {
                foundNonDoubles = true;
                assertNotEquals(testDice.getDie1(), testDice.getDie2(), "Non-doubles should have different dice values");
            }
        }
        
        // With enough rolls, we should find both doubles and non-doubles
        assertTrue(foundDoubles, "Should have found at least one double");
        assertTrue(foundNonDoubles, "Should have found at least one non-double");
    }
    
    @Test
    void testDoublesCount() {
        assertEquals(0, dice.getDoublesCount());
        
        // Simulate rolling doubles by checking the state
        // Since we can't force doubles with our current implementation,
        // we'll test the count logic separately
        
        // Test reset functionality
        dice.resetDoublesCount();
        assertEquals(0, dice.getDoublesCount());
    }
    
    @Test
    void testDoublesCountIncrement() {
        // This test checks the internal logic by simulating multiple rolls
        
        // Roll multiple times and check that doubles count behaves correctly
        for (int i = 0; i < 20; i++) {
            int previousCount = dice.getDoublesCount();
            dice.roll();
            
            if (dice.isDoubles()) {
                assertTrue(dice.getDoublesCount() > previousCount, "Doubles count should increment on doubles");
            } else {
                assertEquals(0, dice.getDoublesCount(), "Doubles count should reset on non-doubles");
            }
        }
    }
    
    @Test
    void testShouldGoToJail() {
        assertFalse(dice.shouldGoToJail()); // Initially should not go to jail
        
        // The shouldGoToJail method checks if doublesCount >= 3
        // We can't easily force 3 consecutive doubles, but we can test the logic indirectly
        
        // Roll until we either get 3 doubles or confirm the method works
        for (int i = 0; i < 100; i++) {
            dice.roll();
            if (dice.getDoublesCount() >= 3) {
                assertTrue(dice.shouldGoToJail(), "Should go to jail after 3 consecutive doubles");
                break;
            } else {
                assertFalse(dice.shouldGoToJail(), "Should not go to jail with less than 3 doubles");
            }
        }
    }
    
    @Test
    void testToString() {
        dice.roll();
        String result = dice.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("Dice rolled:"));
        assertTrue(result.contains("" + dice.getDie1()));
        assertTrue(result.contains("" + dice.getDie2()));
        assertTrue(result.contains("" + dice.getTotal()));
        
        if (dice.isDoubles()) {
            assertTrue(result.contains("DOUBLES!"));
        }
    }
    
    @Test
    void testConsistentRolls() {
        // Test that the same seed produces consistent results
        Dice dice1 = new Dice(999);
        Dice dice2 = new Dice(999);
        
        for (int i = 0; i < 10; i++) {
            int roll1 = dice1.roll();
            int roll2 = dice2.roll();
            
            assertEquals(roll1, roll2, "Same seed should produce same sequence");
            assertEquals(dice1.getDie1(), dice2.getDie1(), "Die 1 should be same");
            assertEquals(dice1.getDie2(), dice2.getDie2(), "Die 2 should be same");
        }
    }
    
    @Test
    void testRandomness() {
        // Test that different seeds produce different results
        Dice dice1 = new Dice(123);
        Dice dice2 = new Dice(456);
        
        boolean foundDifference = false;
        
        for (int i = 0; i < 20; i++) {
            int roll1 = dice1.roll();
            int roll2 = dice2.roll();
            
            if (roll1 != roll2) {
                foundDifference = true;
                break;
            }
        }
        
        assertTrue(foundDifference, "Different seeds should eventually produce different results");
    }
}