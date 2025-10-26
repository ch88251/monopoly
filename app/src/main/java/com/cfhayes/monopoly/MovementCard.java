package com.cfhayes.monopoly;

/**
 * Movement cards that change the player's position on the board
 */
public class MovementCard extends Card {
    private int targetPosition;
    private boolean collectGoMoney;
    
    /**
     * Create a movement card that moves to a specific position
     */
    public MovementCard(String description, CardType type, int targetPosition, boolean collectGoMoney) {
        super(description, type);
        this.targetPosition = targetPosition;
        this.collectGoMoney = collectGoMoney;
    }
    
    @Override
    public void execute(Player player, Game game) {
        int oldPosition = player.getPosition();
        
        // Check if player passes GO when moving
        if (collectGoMoney && (targetPosition < oldPosition || targetPosition == 0)) {
            player.addMoney(200);
            System.out.println("You passed GO and collected $200!");
        }
        
        player.setPosition(targetPosition);
        System.out.println("You moved to " + game.getBoard().getSpace(targetPosition).getName());
        
        // Handle landing on the new space
        BoardSpace space = game.getBoard().getSpace(targetPosition);
        
        // Special handling for "Go to Jail" cards
        if (targetPosition == 10 && description.toLowerCase().contains("jail")) {
            player.setInJail(true);
            System.out.println("You are now in jail!");
        } else {
            // Handle normal space landing (but avoid recursive card drawing)
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
            } else if (space instanceof SpecialSpace) {
                SpecialSpace specialSpace = (SpecialSpace) space;
                // Only handle non-card spaces to avoid infinite recursion
                if (!specialSpace.getName().toLowerCase().contains("chance") && 
                    !specialSpace.getName().toLowerCase().contains("community chest")) {
                    specialSpace.handleLanding(player);
                }
            }
        }
    }
    
    public int getTargetPosition() {
        return targetPosition;
    }
    
    public boolean shouldCollectGoMoney() {
        return collectGoMoney;
    }
}