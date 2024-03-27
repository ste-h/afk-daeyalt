package com.afkdaeyalt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class IdlePulseOverlay extends Overlay
{
	private final Client client;
	private final AfkDaeyaltPlugin plugin;
	private final AfkDaeyaltConfig config;

	@Inject
	IdlePulseOverlay(Client client, AfkDaeyaltPlugin plugin, AfkDaeyaltConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics2D)
	{
		if (!plugin.isPlayerInMines())
		{
			return null;
		}

		return null;
	}
}
