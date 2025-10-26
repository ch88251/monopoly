package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GameTest {
    
    private Game game;
    
    @BeforeEach
    void setUp() {
        game = new Game();
    }
    
    @Test
    void testGameCreation() {
        assertNotNull(game);
        assertEquals(0, game.getPlayerCount());
        assertNotNull(game.getBoard());
        assertNotNull(game.getPlayers());
        assertTrue(game.getPlayers().isEmpty());
    }
    
    @Test
    void testAddPlayer() {
        game.addPlayer("Alice");
        assertEquals(1, game.getPlayerCount());
        
        List<Player> players = game.getPlayers();
        assertEquals(1, players.size());
        assertEquals("Alice", players.get(0).getName());
        assertEquals(1500, players.get(0).getMoney()); // Starting money
        assertEquals(0, players.get(0).getPosition()); // Start at GO
    }
    
    @Test
    void testAddMultiplePlayers() {
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        game.addPlayer("Charlie");
        
        assertEquals(3, game.getPlayerCount());
        
        List<Player> players = game.getPlayers();
        assertEquals("Alice", players.get(0).getName());
        assertEquals("Bob", players.get(1).getName());
        assertEquals("Charlie", players.get(2).getName());
    }
    
    @Test
    void testAddTooManyPlayers() {
        // Add maximum allowed players (8)
        for (int i = 1; i <= 8; i++) {
            game.addPlayer("Player " + i);
        }
        assertEquals(8, game.getPlayerCount());
        
        // Adding one more should throw an exception
        assertThrows(IllegalStateException.class, () -> game.addPlayer("Player 9"));
    }
    
    @Test
    void testStartGameWithInsufficientPlayers() {
        // Can't start with 0 players
        assertThrows(IllegalStateException.class, () -> game.start());
        
        // Can't start with 1 player
        game.addPlayer("Lonely Player");
        assertThrows(IllegalStateException.class, () -> game.start());
    }
    
    @Test
    void testGetCurrentPlayer() {
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        
        // Initially should be first player
        Player currentPlayer = game.getCurrentPlayer();
        assertEquals("Alice", currentPlayer.getName());
    }
    
    @Test
    void testGetBoard() {
        Board board = game.getBoard();
        assertNotNull(board);
        assertEquals(40, board.getSize()); // Standard Monopoly board
    }
    
    @Test
    void testGetPlayersReturnsCopy() {
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        
        List<Player> players1 = game.getPlayers();
        List<Player> players2 = game.getPlayers();
        
        // Should be equal but not the same object
        assertEquals(players1, players2);
        assertNotSame(players1, players2);
        
        // Modifying the returned list shouldn't affect the game
        players1.clear();
        assertEquals(2, game.getPlayerCount()); // Should still have 2 players
    }
    
    @Test
    void testPlayerStartingConditions() {
        game.addPlayer("TestPlayer");
        
        Player player = game.getPlayers().get(0);
        assertEquals("TestPlayer", player.getName());
        assertEquals(1500, player.getMoney()); // Standard starting money
        assertEquals(0, player.getPosition()); // Start at GO
        assertTrue(player.getProperties().isEmpty()); // No properties initially
        assertFalse(player.isInJail()); // Not in jail initially
        assertEquals(0, player.getJailTurns()); // No jail turns
    }
    
    @Test
    void testGameIntegrity() {
        // Test that game maintains proper state
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        
        assertEquals(2, game.getPlayerCount());
        assertEquals(2, game.getPlayers().size());
        
        // Current player should be valid
        Player currentPlayer = game.getCurrentPlayer();
        assertTrue(game.getPlayers().contains(currentPlayer));
    }
    
    // Note: Testing the actual game loop (start() method) is complex because it involves
    // user input (Scanner) and console output. In a real application, you'd want to
    // refactor the Game class to accept input/output streams for easier testing,
    // or create a separate GameEngine class that doesn't handle I/O directly.
    
    @Test 
    void testGameComponentsAreInitialized() {
        // Verify that all necessary game components are properly initialized
        assertNotNull(game.getBoard());
        assertNotNull(game.getPlayers());
        
        // Board should have all 40 spaces
        assertEquals(40, game.getBoard().getSize());
        
        // Should have proper mix of property types
        List<Property> properties = game.getBoard().getAllProperties();
        assertEquals(28, properties.size()); // 22 regular + 4 railroads + 2 utilities
        
        long regularProperties = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.REGULAR)
            .count();
        assertEquals(22, regularProperties);
        
        long railroads = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.RAILROAD)
            .count();
        assertEquals(4, railroads);
        
        long utilities = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.UTILITY)
            .count();
        assertEquals(2, utilities);
    }
    
    @Test
    void testPropertyPurchaseWithInsufficientFunds() {
        // Test that property purchase properly handles insufficient funds
        game.addPlayer("Poor Player");
        Player player = game.getPlayers().get(0);
        
        // Set player to have very little money
        player.setMoney(50);
        
        // Try to buy an expensive property 
        Property expensiveProperty = new Property("Expensive Property", 1, 400, 50, Property.PropertyType.REGULAR, "Blue");
        
        // Verify player cannot afford it
        assertFalse(player.canAfford(expensiveProperty.getPrice()));
        assertEquals(50, player.getMoney()); // Money should remain unchanged
        
        // Try to buy a property they can afford
        Property cheapProperty = new Property("Cheap Property", 2, 30, 3, Property.PropertyType.REGULAR, "Brown");
        assertTrue(player.canAfford(cheapProperty.getPrice()));
        
        // Simulate purchase
        if (player.canAfford(cheapProperty.getPrice())) {
            player.subtractMoney(cheapProperty.getPrice());
            cheapProperty.setOwner(player);
            player.addProperty(cheapProperty);
        }
        
        assertEquals(20, player.getMoney()); // Should have 50 - 30 = 20
        assertEquals(1, player.getProperties().size());
        assertEquals(cheapProperty, player.getProperties().get(0));
    }
}