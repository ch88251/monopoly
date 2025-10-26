package com.cfhayes.monopoly;

/**
 * Special action cards with unique behaviors
 */
public class ActionCard extends Card {
    private ActionType actionType;
    
    public enum ActionType {
        GET_OUT_OF_JAIL_FREE,
        GO_TO_JAIL,
        ADVANCE_TO_GO,
        GO_BACK_3_SPACES,
        REPAIRS, // Pay for house/hotel repairs
        PAY_EACH_PLAYER, // Pay money to each other player
        COLLECT_FROM_EACH_PLAYER // Collect money from each other player
    }
    
    public ActionCard(String description, CardType type, ActionType actionType) {
        super(description, type);
        this.actionType = actionType;
    }
    
    @Override
    public void execute(Player player, Game game) {
        switch (actionType) {
            case GET_OUT_OF_JAIL_FREE:
                // TODO: Implement jail card holding (for now just give money equivalent)
                player.addMoney(50);
                System.out.println("Get Out of Jail Free card! (Worth $50 for now)");
                break;
                
            case GO_TO_JAIL:
                player.setPosition(10); // Jail position
                player.setInJail(true);
                System.out.println("Go directly to Jail! Do not pass GO, do not collect $200");
                break;
                
            case ADVANCE_TO_GO:
                player.setPosition(0);
                player.addMoney(200);
                System.out.println("Advanced to GO! Collected $200");
                break;
                
            case GO_BACK_3_SPACES:
                int newPosition = (player.getPosition() - 3 + 40) % 40;
                player.setPosition(newPosition);
                System.out.println("Went back 3 spaces to " + game.getBoard().getSpace(newPosition).getName());
                
                // Handle landing on new space
                BoardSpace space = game.getBoard().getSpace(newPosition);
                if (space instanceof Property) {
                    Property property = (Property) space;
                    // For card-triggered moves, just show the property info, don't force purchase
                    if (property.getOwner() == null) {
                        System.out.println("You landed on " + property.getName() + " (costs $" + property.getPrice() + ")");
                    } else if (!property.getOwner().equals(player)) {
                        // Still need to pay rent if owned by someone else
                        int rent = property.getRent();
                        player.subtractMoney(rent);
                        property.getOwner().addMoney(rent);
                        System.out.println("Paid $" + rent + " rent to " + property.getOwner().getName());
                    }
                }
                break;
                
            case REPAIRS:
                // Simplified: Pay $25 per house, $100 per hotel (assume player has some buildings)
                int repairCost = 100; // Simplified fixed cost
                player.subtractMoney(repairCost);
                System.out.println("Paid $" + repairCost + " for general repairs");
                break;
                
            case PAY_EACH_PLAYER:
                int payAmount = 50;
                for (Player otherPlayer : game.getPlayers()) {
                    if (!otherPlayer.equals(player)) {
                        int actualPay = Math.min(payAmount, player.getMoney());
                        player.subtractMoney(actualPay);
                        otherPlayer.addMoney(actualPay);
                    }
                }
                System.out.println("Paid $" + payAmount + " to each other player");
                break;
                
            case COLLECT_FROM_EACH_PLAYER:
                int collectAmount = 50;
                for (Player otherPlayer : game.getPlayers()) {
                    if (!otherPlayer.equals(player)) {
                        int actualCollect = Math.min(collectAmount, otherPlayer.getMoney());
                        otherPlayer.subtractMoney(actualCollect);
                        player.addMoney(actualCollect);
                    }
                }
                System.out.println("Collected $" + collectAmount + " from each other player");
                break;
        }
        
        System.out.println("Your balance is now $" + player.getMoney());
    }
    
    public ActionType getActionType() {
        return actionType;
    }
}