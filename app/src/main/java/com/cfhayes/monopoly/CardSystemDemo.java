package com.cfhayes.monopoly;

/**
 * Demo class to showcase the card system functionality
 */
public class CardSystemDemo {
    public static void main(String[] args) {
        System.out.println("=== Monopoly Card System Demo ===\n");
        
        // Create game components
        Game game = new Game();
        Player player = new Player("Demo Player", 1500);
        
        // Show initial player state
        System.out.println("Initial Player State:");
        System.out.println("Position: " + player.getPosition());
        System.out.println("Money: $" + player.getMoney());
        System.out.println();
        
        // Demo Chance cards
        System.out.println("=== Chance Cards Demo ===");
        for (int i = 0; i < 5; i++) {
            Card chanceCard = game.getCardDeck().drawChanceCard();
            System.out.println("Drew: " + chanceCard);
            
            // Save state before card
            int oldMoney = player.getMoney();
            int oldPosition = player.getPosition();
            
            // Execute card
            chanceCard.execute(player, game);
            
            // Show changes
            int moneyChange = player.getMoney() - oldMoney;
            int positionChange = player.getPosition() - oldPosition;
            
            if (moneyChange != 0) {
                System.out.println("  Money changed by: $" + moneyChange);
            }
            if (positionChange != 0) {
                System.out.println("  Position changed by: " + positionChange + " (now at " + player.getPosition() + ")");
            }
            System.out.println("  New state: Position " + player.getPosition() + ", Money $" + player.getMoney());
            System.out.println();
        }
        
        System.out.println("=== Community Chest Cards Demo ===");
        for (int i = 0; i < 5; i++) {
            Card communityCard = game.getCardDeck().drawCommunityChestCard();
            System.out.println("Drew: " + communityCard);
            
            // Save state before card
            int oldMoney = player.getMoney();
            int oldPosition = player.getPosition();
            
            // Execute card
            communityCard.execute(player, game);
            
            // Show changes
            int moneyChange = player.getMoney() - oldMoney;
            int positionChange = player.getPosition() - oldPosition;
            
            if (moneyChange != 0) {
                System.out.println("  Money changed by: $" + moneyChange);
            }
            if (positionChange != 0) {
                System.out.println("  Position changed by: " + positionChange + " (now at " + player.getPosition() + ")");
            }
            System.out.println("  New state: Position " + player.getPosition() + ", Money $" + player.getMoney());
            System.out.println();
        }
        
        System.out.println("=== Final Player State ===");
        System.out.println("Position: " + player.getPosition() + " (" + game.getBoard().getSpace(player.getPosition()).getName() + ")");
        System.out.println("Money: $" + player.getMoney());
    }
}