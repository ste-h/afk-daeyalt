package com.afkdaeyalt;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.ObjectID;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(name = "AFK Daeyalt")
public class AfkDaeyaltPlugin extends Plugin
{
	private static final int DAEYALT_MINES_REGION = 14744;
	@Inject
	ActiveEssenceOverlay overlay;

	@Getter
	private GameObject activeDaeyaltEssence;

	@Getter
	private boolean isPlayerInMines;

	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private AfkDaeyaltConfig config;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		isPlayerInMines = checkIfPlayerInMines();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			activeDaeyaltEssence = null;
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject object = event.getGameObject();
		if (object.getId() == ObjectID.DAEYALT_ESSENCE_39095)
		{
			activeDaeyaltEssence = object;
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		GameObject object = event.getGameObject();
		if (object == activeDaeyaltEssence)
		{
			activeDaeyaltEssence = null;
		}
	}

	@Provides
	AfkDaeyaltConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AfkDaeyaltConfig.class);
	}

	private boolean checkIfPlayerInMines()
	{
		return client.getLocalPlayer() != null && DAEYALT_MINES_REGION == client.getLocalPlayer().getWorldLocation().getRegionID();
	}
}
