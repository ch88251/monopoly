package com.cfhayes.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main game controller that manages the game state, players, and game flow.
 */
public class Game {
    private List<Player> players;
    private Board board;
    private Dice dice;
    private int currentPlayerIndex;
    private boolean gameRunning;
    private Scanner scanner;
    
    public Game() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameRunning = false;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Add a player to the game
     */
    public void addPlayer(String name) {
        if (players.size() >= 8) {
            throw new IllegalStateException("Maximum 8 players allowed");
        }
        players.add(new Player(name, 1500)); // Starting money: $1500
    }
    
    /**
     * Get the number of players in the game
     */
    public int getPlayerCount() {
        return players.size();
    }
    
    /**
     * Start the game
     */
    public void start() {
        if (players.size() < 2) {
            throw new IllegalStateException("At least 2 players required to start the game");
        }
        
        gameRunning = true;
        System.out.println("Game started!");
        
        // Game loop
        while (gameRunning && !isGameOver()) {
            playTurn();
            nextPlayer();
        }
        
        endGame();
    }
    
    /**
     * Play one turn for the current player
     */
    private void playTurn() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println("\n" + currentPlayer.getName() + "'s turn");
        System.out.println("Current position: " + currentPlayer.getPosition());
        System.out.println("Current money: $" + currentPlayer.getMoney());
        
        System.out.println("Press Enter to roll dice...");
        scanner.nextLine();
        
        // Roll dice and move player
        int roll = dice.roll();
        System.out.println("Rolled: " + roll);
        
        int newPosition = (currentPlayer.getPosition() + roll) % 40;
        currentPlayer.setPosition(newPosition);
        
        // Check if player passed GO
        if (newPosition < currentPlayer.getPosition()) {
            currentPlayer.addMoney(200);
            System.out.println("Passed GO! Collected $200");
        }
        
        System.out.println("Moved to position: " + newPosition);
        
        // Handle landing on space
        BoardSpace space = board.getSpace(newPosition);
        handleSpaceLanding(currentPlayer, space);
    }
    
    /**
     * Handle what happens when a player lands on a space
     */
    private void handleSpaceLanding(Player player, BoardSpace space) {
        System.out.println("Landed on: " + space.getName());
        
        if (space instanceof Property) {
            Property property = (Property) space;
            handlePropertyLanding(player, property);
        }
        // Additional space types can be handled here (taxes, chance, etc.)
    }
    
    /**
     * Handle landing on a property
     */
    private void handlePropertyLanding(Player player, Property property) {
        if (property.getOwner() == null) {
            // Property is unowned, offer to buy
            System.out.println("This property costs $" + property.getPrice() + ". Buy it? (y/n)");
            String response = scanner.nextLine().toLowerCase();
            
            if (response.equals("y")) {
                if (player.getMoney() >= property.getPrice()) {
                    player.subtractMoney(property.getPrice());
                    property.setOwner(player);
                    player.addProperty(property);
                    System.out.println("You bought " + property.getName() + "!");
                } else {
                    System.out.println("You don't have enough money to buy " + property.getName() + 
                                     "! You have $" + player.getMoney() + " but it costs $" + property.getPrice() + ".");
                }
            } else if (response.equals("n")) {
                System.out.println("You declined to buy " + property.getName() + ".");
            }
        } else if (!property.getOwner().equals(player)) {
            // Property is owned by someone else, pay rent
            int rent = property.getRent();
            player.subtractMoney(rent);
            property.getOwner().addMoney(rent);
            System.out.println("Paid $" + rent + " rent to " + property.getOwner().getName());
        }
    }
    
    /**
     * Move to the next player
     */
    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    /**
     * Get the current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    /**
     * Check if the game is over
     */
    private boolean isGameOver() {
        long playersWithMoney = players.stream()
            .filter(p -> p.getMoney() > 0)
            .count();
        return playersWithMoney <= 1;
    }
    
    /**
     * End the game and determine winner
     */
    private void endGame() {
        gameRunning = false;
        
        Player winner = players.stream()
            .filter(p -> p.getMoney() > 0)
            .findFirst()
            .orElse(null);
            
        if (winner != null) {
            System.out.println("\nGame Over! " + winner.getName() + " wins!");
        } else {
            System.out.println("\nGame Over! No winner.");
        }
        
        scanner.close();
    }
    
    /**
     * Get all players (for testing)
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    /**
     * Get the game board (for testing)
     */
    public Board getBoard() {
        return board;
    }
}