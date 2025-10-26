package com.cfhayes.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BoardTest {
    
    private Board board;
    
    @BeforeEach
    void setUp() {
        board = new Board();
    }
    
    @Test
    void testBoardCreation() {
        assertNotNull(board);
        assertEquals(40, board.getSize());
    }
    
    @Test
    void testGetSpace() {
        // Test corner spaces
        BoardSpace go = board.getSpace(0);
        assertEquals("GO", go.getName());
        assertEquals(0, go.getPosition());
        assertTrue(go instanceof SpecialSpace);
        
        BoardSpace jail = board.getSpace(10);
        assertEquals("Jail", jail.getName());
        assertEquals(10, jail.getPosition());
        assertTrue(jail instanceof SpecialSpace);
        
        BoardSpace freeParking = board.getSpace(20);
        assertEquals("Free Parking", freeParking.getName());
        assertEquals(20, freeParking.getPosition());
        assertTrue(freeParking instanceof SpecialSpace);
        
        BoardSpace goToJail = board.getSpace(30);
        assertEquals("Go to Jail", goToJail.getName());
        assertEquals(30, goToJail.getPosition());
        assertTrue(goToJail instanceof SpecialSpace);
    }
    
    @Test
    void testGetSpaceInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> board.getSpace(-1));
        assertThrows(IllegalArgumentException.class, () -> board.getSpace(40));
        assertThrows(IllegalArgumentException.class, () -> board.getSpace(100));
    }
    
    @Test
    void testProperties() {
        // Test some well-known properties
        BoardSpace mediterraneanAve = board.getSpace(1);
        assertTrue(mediterraneanAve instanceof Property);
        Property mediterranean = (Property) mediterraneanAve;
        assertEquals("Mediterranean Avenue", mediterranean.getName());
        assertEquals(60, mediterranean.getPrice());
        assertEquals("Brown", mediterranean.getColorGroup());
        assertEquals(Property.PropertyType.REGULAR, mediterranean.getType());
        
        BoardSpace boardwalk = board.getSpace(39);
        assertTrue(boardwalk instanceof Property);
        Property boardwalkProperty = (Property) boardwalk;
        assertEquals("Boardwalk", boardwalkProperty.getName());
        assertEquals(400, boardwalkProperty.getPrice());
        assertEquals("Dark Blue", boardwalkProperty.getColorGroup());
        assertEquals(Property.PropertyType.REGULAR, boardwalkProperty.getType());
    }
    
    @Test
    void testRailroads() {
        // Test railroads
        Property readingRR = (Property) board.getSpace(5);
        assertEquals("Reading Railroad", readingRR.getName());
        assertEquals(Property.PropertyType.RAILROAD, readingRR.getType());
        assertEquals(200, readingRR.getPrice());
        
        Property pennsylvaniaRR = (Property) board.getSpace(15);
        assertEquals("Pennsylvania Railroad", pennsylvaniaRR.getName());
        assertEquals(Property.PropertyType.RAILROAD, pennsylvaniaRR.getType());
        
        Property boRR = (Property) board.getSpace(25);
        assertEquals("B&O Railroad", boRR.getName());
        assertEquals(Property.PropertyType.RAILROAD, boRR.getType());
        
        Property shortLine = (Property) board.getSpace(35);
        assertEquals("Short Line", shortLine.getName());
        assertEquals(Property.PropertyType.RAILROAD, shortLine.getType());
    }
    
    @Test
    void testUtilities() {
        Property electricCompany = (Property) board.getSpace(12);
        assertEquals("Electric Company", electricCompany.getName());
        assertEquals(Property.PropertyType.UTILITY, electricCompany.getType());
        assertEquals(150, electricCompany.getPrice());
        
        Property waterWorks = (Property) board.getSpace(28);
        assertEquals("Water Works", waterWorks.getName());
        assertEquals(Property.PropertyType.UTILITY, waterWorks.getType());
        assertEquals(150, waterWorks.getPrice());
    }
    
    @Test
    void testGetAllSpaces() {
        List<BoardSpace> allSpaces = board.getAllSpaces();
        assertEquals(40, allSpaces.size());
        
        // Test that it returns a copy (modification doesn't affect original)
        allSpaces.clear();
        assertEquals(40, board.getSize()); // Original should still have 40 spaces
    }
    
    @Test
    void testGetAllProperties() {
        List<Property> properties = board.getAllProperties();
        
        // Count expected properties: 28 total (22 regular + 4 railroads + 2 utilities)
        assertEquals(28, properties.size());
        
        // Verify all items are actually properties
        for (BoardSpace space : properties) {
            assertTrue(space instanceof Property);
        }
    }
    
    @Test
    void testGetPropertiesByColorGroup() {
        // Test brown properties (2 properties)
        List<Property> brownProperties = board.getPropertiesByColorGroup("Brown");
        assertEquals(2, brownProperties.size());
        assertTrue(brownProperties.stream().anyMatch(p -> p.getName().equals("Mediterranean Avenue")));
        assertTrue(brownProperties.stream().anyMatch(p -> p.getName().equals("Baltic Avenue")));
        
        // Test light blue properties (3 properties)
        List<Property> lightBlueProperties = board.getPropertiesByColorGroup("Light Blue");
        assertEquals(3, lightBlueProperties.size());
        
        // Test dark blue properties (2 properties)
        List<Property> darkBlueProperties = board.getPropertiesByColorGroup("Dark Blue");
        assertEquals(2, darkBlueProperties.size());
        assertTrue(darkBlueProperties.stream().anyMatch(p -> p.getName().equals("Park Place")));
        assertTrue(darkBlueProperties.stream().anyMatch(p -> p.getName().equals("Boardwalk")));
        
        // Test railroads (4 properties)
        List<Property> railroads = board.getPropertiesByColorGroup("Railroad");
        assertEquals(4, railroads.size());
        
        // Test utilities (2 properties)
        List<Property> utilities = board.getPropertiesByColorGroup("Utility");
        assertEquals(2, utilities.size());
        
        // Test non-existent color group
        List<Property> nonExistent = board.getPropertiesByColorGroup("Purple");
        assertTrue(nonExistent.isEmpty());
    }
    
    @Test
    void testSpecialSpaces() {
        // Test tax spaces
        BoardSpace incomeTax = board.getSpace(4);
        assertEquals("Income Tax", incomeTax.getName());
        assertTrue(incomeTax instanceof SpecialSpace);
        
        BoardSpace luxuryTax = board.getSpace(38);
        assertEquals("Luxury Tax", luxuryTax.getName());
        assertTrue(luxuryTax instanceof SpecialSpace);
        
        // Test card spaces
        BoardSpace communityChest1 = board.getSpace(2);
        assertEquals("Community Chest", communityChest1.getName());
        assertTrue(communityChest1 instanceof SpecialSpace);
        
        BoardSpace chance1 = board.getSpace(7);
        assertEquals("Chance", chance1.getName());
        assertTrue(chance1 instanceof SpecialSpace);
    }
    
    @Test
    void testBoardIntegrity() {
        // Verify that every position from 0-39 has a space
        for (int i = 0; i < 40; i++) {
            BoardSpace space = board.getSpace(i);
            assertNotNull(space, "Space at position " + i + " should not be null");
            assertEquals(i, space.getPosition(), "Space position should match index");
        }
    }
    
    @Test
    void testPropertyPrices() {
        // Test that property prices are reasonable and increasing generally
        List<Property> properties = board.getAllProperties();
        
        // Filter to just regular properties and check some price relationships
        List<Property> regularProperties = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.REGULAR)
            .toList();
        
        // Mediterranean Avenue should be cheapest regular property
        Property mediterranean = regularProperties.stream()
            .filter(p -> p.getName().equals("Mediterranean Avenue"))
            .findFirst()
            .orElse(null);
        assertNotNull(mediterranean);
        assertEquals(60, mediterranean.getPrice());
        
        // Boardwalk should be most expensive regular property
        Property boardwalk = regularProperties.stream()
            .filter(p -> p.getName().equals("Boardwalk"))
            .findFirst()
            .orElse(null);
        assertNotNull(boardwalk);
        assertEquals(400, boardwalk.getPrice());
        
        // Railroads should all cost the same
        List<Property> railroads = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.RAILROAD)
            .toList();
        assertEquals(4, railroads.size());
        assertTrue(railroads.stream().allMatch(p -> p.getPrice() == 200));
        
        // Utilities should all cost the same
        List<Property> utilities = properties.stream()
            .filter(p -> p.getType() == Property.PropertyType.UTILITY)
            .toList();
        assertEquals(2, utilities.size());
        assertTrue(utilities.stream().allMatch(p -> p.getPrice() == 150));
    }
}