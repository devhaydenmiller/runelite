package net.runelite.client.plugins.ToAUniqueAlert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("toauniquealert")
public interface ToAUniqueAlertConfig extends Config {

    // Other config items...

    @ConfigItem(
            keyName = "uniqueItemIds",
            name = "Unique Item IDs",
            description = "Comma-separated list of unique item IDs",
            position = 1
    )
    default String getUniqueItemIds() {
        return "27277,26219,25975,25985,27226,27229,27232,1519";
    }

    @ConfigItem(
            keyName = "enableAlerts",
            name = "Enable Alerts",
            description = "Enable or disable unique item alerts",
            position = 2
    )
    default boolean enableAlerts() {
        return true;
    }

    // Other config items...
}

