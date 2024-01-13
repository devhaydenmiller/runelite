package net.runelite.client.plugins.ArcaneScarabAlert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("arcaneScarabAlert")
public interface ArcaneScarabAlertConfig extends Config {

    @ConfigItem(
            keyName = "enabled",
            name = "Enabled",
            description = "Enables/Disables the Arcane Scarab alert",
            position = 1
    )
    default boolean enabled() {
        return true;
    }
}
