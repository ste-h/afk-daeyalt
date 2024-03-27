package com.afkdaeyalt;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.ObjectID;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
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
	private static final long IDLE_DELAY = 1000;
	private final int MINING_ANIMATION = 8347;
	@Inject
	ActiveEssenceOverlay activeEssenceOverlay;
	@Inject
	IdlePulseOverlay idlePulseOverlay;
	private WorldPoint lastPosition;
	private boolean notifyPosition = false;
	@Getter
	private GameObject activeDaeyaltEssence;
	@Getter
	private boolean isPlayerInMines;
	@Getter
	private boolean isPlayerIdle;
	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private AfkDaeyaltConfig config;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(activeEssenceOverlay);
		overlayManager.add(idlePulseOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(activeEssenceOverlay);
		overlayManager.add(idlePulseOverlay);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		final Player local = client.getLocalPlayer();

		isPlayerInMines = checkIfPlayerInMines(local);
		isPlayerIdle = checkIdle(local);
		log.info(String.valueOf(nextToActiveDaeyalt(local)));
//		log.info(String.valueOf(isPlayerIdle));
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

	private boolean checkIfPlayerInMines(Player local)
	{
		return local != null && DAEYALT_MINES_REGION == client.getLocalPlayer().getWorldLocation().getRegionID();
	}

	private boolean checkMoving(Player local)
	{
		if (lastPosition == null)
		{
			lastPosition = local.getWorldLocation();
			return false;
		}

		WorldPoint position = local.getWorldLocation();
		if (!lastPosition.equals(position))
		{
			lastPosition = position;
			return true;
		}

		return false;
	}

	private int nextToActiveDaeyalt(Player local)
	{
		return local.getWorldLocation().distanceTo(activeDaeyaltEssence.getWorldLocation());
	}

	private boolean checkIdle(Player local)
	{
		if (activeDaeyaltEssence == null)
		{
			return false;
		}

		return local.getAnimation() != MINING_ANIMATION &&
			(!checkMoving(local) && nextToActiveDaeyalt(local) > 2);
	}

}

