package com.afkdaeyalt;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("AfkDaeyalt")

public interface AfkDaeyaltConfig extends Config
{
	@ConfigItem(
		keyName = "enableIdleGlow",
		name = "Idle Pulse",
		description = "Adds a pulsing effect when idle"
	)
	default boolean enableIdleGlow()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightActiveEssence",
		name = "Highlight Active Essence",
		description = "Highlights the tile of the active daeyalt essence"
	)
	default boolean highlightActiveEssence()
	{
		return true;
	}
}
