package net.runelite.client.plugins.ToAUniqueAlert;

import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.InventoryID;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@PluginDescriptor(
        name = "ToA Unique Item Alert",
        description = "Plays a sound when a unique item drops in the Tombs of Amascut",
        tags = {"tombs", "amascut", "sound", "drop"}
)
public class ToAUniqueAlert extends Plugin {

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ToAUniqueAlertConfig config;

    private Set<Integer> uniqueItemIds;

    @Override
    protected void startUp() {
        System.out.println("ToAUniqueAlert plugin started!");
        loadUniqueItemIds();
    }

    @Override
    protected void shutDown() {
        uniqueItemIds.clear();
    }

    @Provides
    ToAUniqueAlertConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ToAUniqueAlertConfig.class);
    }

    private void loadUniqueItemIds() {
        // Load unique item IDs from the config or any other source
        // For example, you can store them in your config class as a list
        String uniqueItemIdsString = config.getUniqueItemIds();

        // Check if the string is not empty before parsing
        if (!uniqueItemIdsString.isEmpty()) {
            // Parse the comma-separated string into a set of integers
            uniqueItemIds = Arrays.stream(uniqueItemIdsString.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            System.out.println("Unique item IDs: " + uniqueItemIds);
        } else {
            // Log a warning or handle the empty string case as appropriate for your application
            System.out.println("Warning: Unique item IDs string is empty.");
            // You may choose to use a default set of item IDs in this case or handle it differently.
        }

        // Add the provided unique item IDs
        uniqueItemIds.add(27277);
        uniqueItemIds.add(26219);
        uniqueItemIds.add(25975);
        uniqueItemIds.add(25985);
        uniqueItemIds.add(27226);
        uniqueItemIds.add(27229);
        uniqueItemIds.add(27232);
        uniqueItemIds.add(1519);
        System.out.println("Updated Unique item IDs: " + uniqueItemIds);
    }

    @Subscribe
    public void onItemSpawned(ItemSpawned event) {
        int itemId = event.getItem().getId();
        checkAndPlaySound(itemId);
    }

    private Set<Integer> previousInventoryState = new HashSet<>();
    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
            Set<Integer> currentInventoryState = Arrays.stream(event.getItemContainer().getItems())
                    .map(Item::getId)
                    .collect(Collectors.toSet());

            // Find the new items that were not present in the previous state
            Set<Integer> newItems = new HashSet<>(currentInventoryState);
            newItems.removeAll(previousInventoryState);

            // Iterate through the new items and check if they are unique
            for (int newItemId : newItems) {
                if (uniqueItemIds.contains(newItemId)) {
                    checkAndPlaySound(newItemId);
                }
            }

            // Update the previous inventory state
            previousInventoryState = currentInventoryState;
        }

        // Check if the change occurred in the inventory
//        if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
//            // Iterate through the items in the inventory
//            for(int ui : uniqueItemIds){
//                System.out.println("Unique ID Iteration " + ui);
//                if(event.getItemContainer().contains(ui)){
//                    System.out.println("Unique ID that triggered the call: " + ui);
//                    checkAndPlaySound(ui);
//                }
//            }----------
//            for (Item item : event.getItemContainer().getItems()) {
//                System.out.println("Item: " + item);
//                int itemId = item.getId();
//                System.out.println("Item ID: " + itemId);
//                for(int ui : uniqueItemIds){
//                    if(itemId == (ui)){
//                        checkAndPlaySound(itemId);
//                    }
//                }
//            }
        }


    private void checkAndPlaySound(int itemId) {
        if (uniqueItemIds.contains(itemId)) {
            playUniqueItemSound();
        }
    }

    private void playUniqueItemSound() {
        // Implement sound playing logic
        // You can use Java Sound API or any other sound library
        // For simplicity, we'll print a message to the chat for testing
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Unique item dropped! Playing sound...", null);
    }
}
