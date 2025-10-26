package com.cfhayes.monopoly;

import java.util.*;

/**
 * Manages decks of Chance and Community Chest cards
 */
public class CardDeck {
    private List<Card> chanceCards;
    private List<Card> communityChestCards;
    private Random random;
    
    public CardDeck() {
        this.random = new Random();
        initializeCards();
    }
    
    /**
     * Constructor for testing with specific seed
     */
    public CardDeck(long seed) {
        this.random = new Random(seed);
        initializeCards();
    }
    
    private void initializeCards() {
        initializeChanceCards();
        initializeCommunityChestCards();
    }
    
    private void initializeChanceCards() {
        chanceCards = new ArrayList<>();
        
        // Money cards
        chanceCards.add(new MoneyCard("Bank pays you dividend of $50", Card.CardType.CHANCE, 50));
        chanceCards.add(new MoneyCard("Pay poor tax of $15", Card.CardType.CHANCE, -15));
        chanceCards.add(new MoneyCard("Your building loan matures - collect $150", Card.CardType.CHANCE, 150));
        chanceCards.add(new MoneyCard("Speeding fine $50", Card.CardType.CHANCE, -50));
        
        // Movement cards
        chanceCards.add(new MovementCard("Advance to GO", Card.CardType.CHANCE, 0, true));
        chanceCards.add(new MovementCard("Advance to Illinois Avenue", Card.CardType.CHANCE, 24, true));
        chanceCards.add(new MovementCard("Take a trip to Reading Railroad", Card.CardType.CHANCE, 5, true));
        chanceCards.add(new MovementCard("Advance to Boardwalk", Card.CardType.CHANCE, 39, true));
        
        // Action cards
        chanceCards.add(new ActionCard("Go to Jail", Card.CardType.CHANCE, ActionCard.ActionType.GO_TO_JAIL));
        chanceCards.add(new ActionCard("Get Out of Jail Free", Card.CardType.CHANCE, ActionCard.ActionType.GET_OUT_OF_JAIL_FREE));
        chanceCards.add(new ActionCard("Go Back 3 Spaces", Card.CardType.CHANCE, ActionCard.ActionType.GO_BACK_3_SPACES));
        chanceCards.add(new ActionCard("Pay each player $50", Card.CardType.CHANCE, ActionCard.ActionType.PAY_EACH_PLAYER));
        chanceCards.add(new ActionCard("Make general repairs: Pay $25 per house, $100 per hotel", Card.CardType.CHANCE, ActionCard.ActionType.REPAIRS));
        
        // Shuffle the deck
        Collections.shuffle(chanceCards, random);
    }
    
    private void initializeCommunityChestCards() {
        communityChestCards = new ArrayList<>();
        
        // Money cards
        communityChestCards.add(new MoneyCard("Bank error in your favor - collect $200", Card.CardType.COMMUNITY_CHEST, 200));
        communityChestCards.add(new MoneyCard("Doctor's fees - pay $50", Card.CardType.COMMUNITY_CHEST, -50));
        communityChestCards.add(new MoneyCard("From sale of stock you get $50", Card.CardType.COMMUNITY_CHEST, 50));
        communityChestCards.add(new MoneyCard("Holiday fund matures - receive $100", Card.CardType.COMMUNITY_CHEST, 100));
        communityChestCards.add(new MoneyCard("Income tax refund - collect $20", Card.CardType.COMMUNITY_CHEST, 20));
        communityChestCards.add(new MoneyCard("Life insurance matures - collect $100", Card.CardType.COMMUNITY_CHEST, 100));
        communityChestCards.add(new MoneyCard("Hospital fees - pay $100", Card.CardType.COMMUNITY_CHEST, -100));
        communityChestCards.add(new MoneyCard("School fees - pay $50", Card.CardType.COMMUNITY_CHEST, -50));
        
        // Movement cards
        communityChestCards.add(new MovementCard("Advance to GO", Card.CardType.COMMUNITY_CHEST, 0, true));
        
        // Action cards
        communityChestCards.add(new ActionCard("Go to Jail", Card.CardType.COMMUNITY_CHEST, ActionCard.ActionType.GO_TO_JAIL));
        communityChestCards.add(new ActionCard("Get Out of Jail Free", Card.CardType.COMMUNITY_CHEST, ActionCard.ActionType.GET_OUT_OF_JAIL_FREE));
        communityChestCards.add(new ActionCard("Collect $50 from every player for opening night seats", Card.CardType.COMMUNITY_CHEST, ActionCard.ActionType.COLLECT_FROM_EACH_PLAYER));
        
        // Shuffle the deck
        Collections.shuffle(communityChestCards, random);
    }
    
    /**
     * Draw a Chance card
     */
    public Card drawChanceCard() {
        if (chanceCards.isEmpty()) {
            initializeChanceCards(); // Reshuffle when deck is empty
        }
        return chanceCards.remove(chanceCards.size() - 1);
    }
    
    /**
     * Draw a Community Chest card
     */
    public Card drawCommunityChestCard() {
        if (communityChestCards.isEmpty()) {
            initializeCommunityChestCards(); // Reshuffle when deck is empty
        }
        return communityChestCards.remove(communityChestCards.size() - 1);
    }
    
    /**
     * Get remaining Chance cards count
     */
    public int getChanceCardsRemaining() {
        return chanceCards.size();
    }
    
    /**
     * Get remaining Community Chest cards count
     */
    public int getCommunityChestCardsRemaining() {
        return communityChestCards.size();
    }
}