#!/bin/bash
echo "Testing Monopoly card system..."
echo "This will simulate landing on Chance and Community Chest spaces:"
echo ""

# Simulate some game turns that will likely hit card spaces
# Use enough Enter presses and 'n' responses to avoid getting stuck
echo -e "\n\nn\n\nn\n\nn\n\nn\n\nn\n\nn\n\nn\n\nn\nq" | timeout 20 ./gradlew run -q

echo ""
echo "Card system test completed!"