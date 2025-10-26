package com.cfhayes.monopoly;

/**
 * Main class for the Monopoly game application.
 * Manages the game flow and coordinates between different components.
 */
public class Monopoly {
    private Game game;
    
    public static void main(String[] args) {
        Monopoly monopoly = new Monopoly();
        monopoly.start();
    }
    
    public void start() {
        System.out.println("Welcome to Monopoly!");
        
        // Initialize game with default settings (2-4 players)
        game = new Game();
        
        // For now, just create a simple 2-player game
        game.addPlayer("Player 1");
        game.addPlayer("Player 2");
        
        System.out.println("Game initialized with " + game.getPlayerCount() + " players");
        
        // Start the game loop
        game.start();
    }
}