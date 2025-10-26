package com.cfhayes.monopoly;

/**
 * Money-related cards that add or subtract money from the player
 */
public class MoneyCard extends Card {
    private int amount;
    
    public MoneyCard(String description, CardType type, int amount) {
        super(description, type);
        this.amount = amount;
    }
    
    @Override
    public void execute(Player player, Game game) {
        if (amount > 0) {
            player.addMoney(amount);
            System.out.println("You received $" + amount + "!");
        } else {
            int actualAmount = Math.min(Math.abs(amount), player.getMoney());
            player.subtractMoney(Math.abs(amount));
            System.out.println("You paid $" + actualAmount + "!");
        }
        System.out.println("Your balance is now $" + player.getMoney());
    }
    
    public int getAmount() {
        return amount;
    }
}