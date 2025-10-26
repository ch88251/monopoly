package com.cfhayes.monopoly;

/**
 * Represents special board spaces like GO, Jail, Free Parking, taxes, and card spaces.
 */
public class SpecialSpace extends BoardSpace {
    
    public SpecialSpace(String name, int position) {
        super(name, position);
    }
    
    @Override
    public void handleLanding(Player player) {
        switch (name.toLowerCase()) {
            case "go":
                // Collect $200 for landing on GO (in addition to passing GO)
                player.addMoney(200);
                System.out.println("Landed on GO! Collected $200");
                break;
                
            case "income tax":
                // Pay $200 income tax
                player.subtractMoney(200);
                System.out.println("Paid $200 income tax");
                break;
                
            case "luxury tax":
                // Pay $100 luxury tax  
                player.subtractMoney(100);
                System.out.println("Paid $100 luxury tax");
                break;
                
            case "go to jail":
                // Send player directly to jail
                player.setPosition(10); // Jail position
                player.setInJail(true);
                System.out.println("Go to Jail! Do not pass GO, do not collect $200");
                break;
                
            case "jail":
                // Just visiting jail - no action needed
                System.out.println("Just visiting jail");
                break;
                
            case "free parking":
                // No action in standard rules
                System.out.println("Free Parking - just relaxing!");
                break;
                
            case "community chest":
                System.out.println("Community Chest - draw a card! (not implemented yet)");
                // TODO: Implement Community Chest cards
                break;
                
            case "chance":
                System.out.println("Chance - draw a card! (not implemented yet)");
                // TODO: Implement Chance cards
                break;
                
            default:
                System.out.println("Landed on " + name);
                break;
        }
    }
}