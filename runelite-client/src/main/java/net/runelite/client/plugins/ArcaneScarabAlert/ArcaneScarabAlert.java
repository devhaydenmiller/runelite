package net.runelite.client.plugins.ArcaneScarabAlert;

import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Arcane Scarab Alert",
        description = "Plays a sound when an Arcane Scarab spawns near your player",
        tags = {"arcane", "scarab", "sound", "spawn"}
)
public class ArcaneScarabAlert extends Plugin {

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ArcaneScarabAlertConfig config;

    @Override
    protected void startUp() {
        System.out.println("ArcaneScarabAlert plugin started!");
    }

    @Override
    protected void shutDown() {
    }

    @Provides
    ArcaneScarabAlertConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ArcaneScarabAlertConfig.class);
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event) {
        NPC npc = event.getNpc();
        int npcId = npc.getId();

        // Check if the spawned NPC is the Arcane Scarab
//        if (npcId ==  NpcID.ARCANE_SCARAB) {
//            playArcaneScarabSound();
//        }

        if(npcId == NpcID.CHICKEN){
            playArcaneScarabSound();
        }
    }

    private void playArcaneScarabSound() {
        // Implement sound playing logic
        // You can use Java Sound API or any other sound library
        // For simplicity, we'll print a message to the chat for testing
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Arcane Scarab spawned! Playing sound...", null);
    }
}
