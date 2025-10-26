package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {
    
    private CardDeck cardDeck;
    
    @BeforeEach
    void setUp() {
        // Use seed for deterministic testing
        cardDeck = new CardDeck(12345);
    }
    
    @Test
    void testCardDeckInitialization() {
        assertNotNull(cardDeck);
        assertTrue(cardDeck.getChanceCardsRemaining() > 0);
        assertTrue(cardDeck.getCommunityChestCardsRemaining() > 0);
    }
    
    @Test
    void testDrawChanceCard() {
        int initialCount = cardDeck.getChanceCardsRemaining();
        
        Card card = cardDeck.drawChanceCard();
        
        assertNotNull(card);
        assertEquals(Card.CardType.CHANCE, card.getType());
        assertEquals(initialCount - 1, cardDeck.getChanceCardsRemaining());
    }
    
    @Test
    void testDrawCommunityChestCard() {
        int initialCount = cardDeck.getCommunityChestCardsRemaining();
        
        Card card = cardDeck.drawCommunityChestCard();
        
        assertNotNull(card);
        assertEquals(Card.CardType.COMMUNITY_CHEST, card.getType());
        assertEquals(initialCount - 1, cardDeck.getCommunityChestCardsRemaining());
    }
    
    @Test
    void testChanceDeckReshuffle() {
        // Draw all chance cards
        int initialCount = cardDeck.getChanceCardsRemaining();
        
        for (int i = 0; i < initialCount; i++) {
            cardDeck.drawChanceCard();
        }
        
        assertEquals(0, cardDeck.getChanceCardsRemaining());
        
        // Drawing another card should reshuffle the deck
        Card card = cardDeck.drawChanceCard();
        assertNotNull(card);
        assertTrue(cardDeck.getChanceCardsRemaining() > 0);
    }
    
    @Test
    void testCommunityChestDeckReshuffle() {
        // Draw all community chest cards
        int initialCount = cardDeck.getCommunityChestCardsRemaining();
        
        for (int i = 0; i < initialCount; i++) {
            cardDeck.drawCommunityChestCard();
        }
        
        assertEquals(0, cardDeck.getCommunityChestCardsRemaining());
        
        // Drawing another card should reshuffle the deck
        Card card = cardDeck.drawCommunityChestCard();
        assertNotNull(card);
        assertTrue(cardDeck.getCommunityChestCardsRemaining() > 0);
    }
    
    @Test
    void testDifferentCardTypes() {
        // Test that we get different types of cards
        boolean foundMoneyCard = false;
        boolean foundActionCard = false;
        
        // Draw several cards to check variety
        for (int i = 0; i < 10; i++) {
            Card chanceCard = cardDeck.drawChanceCard();
            Card communityCard = cardDeck.drawCommunityChestCard();
            
            if (chanceCard instanceof MoneyCard || communityCard instanceof MoneyCard) {
                foundMoneyCard = true;
            }
            if (chanceCard instanceof ActionCard || communityCard instanceof ActionCard) {
                foundActionCard = true;
            }
        }
        
        assertTrue(foundMoneyCard, "Should find at least one money card");
        assertTrue(foundActionCard, "Should find at least one action card");
    }
    
    @Test
    void testCardDescriptions() {
        // Test that cards have proper descriptions
        for (int i = 0; i < 5; i++) {
            Card chanceCard = cardDeck.drawChanceCard();
            Card communityCard = cardDeck.drawCommunityChestCard();
            
            assertNotNull(chanceCard.getDescription());
            assertFalse(chanceCard.getDescription().isEmpty());
            
            assertNotNull(communityCard.getDescription());
            assertFalse(communityCard.getDescription().isEmpty());
        }
    }
    
    @Test
    void testDeckSizes() {
        CardDeck newDeck = new CardDeck();
        
        // Chance deck should have multiple cards
        assertTrue(newDeck.getChanceCardsRemaining() >= 10, "Should have at least 10 Chance cards");
        
        // Community Chest deck should have multiple cards
        assertTrue(newDeck.getCommunityChestCardsRemaining() >= 10, "Should have at least 10 Community Chest cards");
    }
    
    @Test
    void testRandomnessWithDifferentSeeds() {
        CardDeck deck1 = new CardDeck(123);
        CardDeck deck2 = new CardDeck(456);
        
        Card card1 = deck1.drawChanceCard();
        Card card2 = deck2.drawChanceCard();
        
        // With different seeds, we should eventually get different cards
        // (This might occasionally fail due to randomness, but very unlikely)
        boolean foundDifference = false;
        if (!card1.getDescription().equals(card2.getDescription())) {
            foundDifference = true;
        }
        
        // Try a few more if first pair was the same
        for (int i = 0; i < 5 && !foundDifference; i++) {
            card1 = deck1.drawChanceCard();
            card2 = deck2.drawChanceCard();
            if (!card1.getDescription().equals(card2.getDescription())) {
                foundDifference = true;
            }
        }
        
        assertTrue(foundDifference, "Different seeds should eventually produce different card sequences");
    }
}