package com.afkdaeyalt;

import java.awt.Color;
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
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isPlayerInMines())
		{
			return null;
		}

		if (plugin.isPlayerIdle())
		{
			tintWithColor(graphics, Color.ORANGE);
		}

		return null;
	}

	// TODO: Allow this to be customizeable
	private void tintWithColor(Graphics2D graphics, Color color)
	{
		long currentTime = System.currentTimeMillis();
		double opacity = 0.4 + 0.2 * Math.sin(currentTime / 1000.0);
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (opacity * 255));
		graphics.setColor(transparentColor);
		graphics.fillRect(0, 0, client.getCanvasWidth(), client.getCanvasHeight());
	}
}
