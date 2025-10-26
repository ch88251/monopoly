package com.cfhayes.monopoly;

public class DebugTest {
    public static void main(String[] args) {
        Player owner = new Player("Owner", 2000);
        Property railroad = new Property("Reading Railroad", 5, 200, 25, Property.PropertyType.RAILROAD, "Railroad");
        
        // Test 1: One railroad
        railroad.setOwner(owner);
        owner.addProperty(railroad);
        
        System.out.println("One railroad:");
        System.out.println("Owner properties count: " + owner.getProperties().size());
        System.out.println("Railroads owned: " + owner.getProperties().stream()
            .filter(p -> p.getType() == Property.PropertyType.RAILROAD)
            .count());
        System.out.println("Railroad rent: " + railroad.calculateRent());
        
        // Test 2: Two railroads
        Property railroad2 = new Property("Pennsylvania Railroad", 15, 200, 25, Property.PropertyType.RAILROAD, "Railroad");
        railroad2.setOwner(owner);
        owner.addProperty(railroad2);
        
        System.out.println("\nTwo railroads:");
        System.out.println("Owner properties count: " + owner.getProperties().size());
        System.out.println("Railroads owned: " + owner.getProperties().stream()
            .filter(p -> p.getType() == Property.PropertyType.RAILROAD)
            .count());
        System.out.println("Railroad1 rent: " + railroad.calculateRent());
        System.out.println("Railroad2 rent: " + railroad2.calculateRent());
    }
}