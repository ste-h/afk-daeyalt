package com.afkdaeyalt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class ActiveEssenceOverlay extends Overlay
{
	private final Client client;
	private final AfkDaeyaltPlugin plugin;
	private final AfkDaeyaltConfig config;

	@Inject
	ActiveEssenceOverlay(Client client, AfkDaeyaltPlugin plugin, AfkDaeyaltConfig config)
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
		GameObject activeDaeyaltEssence = plugin.getActiveDaeyaltEssence();

		if (!config.highlightActiveEssence() || !plugin.isPlayerInMines() || activeDaeyaltEssence == null)
		{
			return null;
		}

		Point mousePosition = client.getMouseCanvasPosition();

		Shape daeyaltClickbox = activeDaeyaltEssence.getCanvasTilePoly();

		if (daeyaltClickbox != null)
		{
			if (daeyaltClickbox.contains(mousePosition.getX(), mousePosition.getY()))
			{
				graphics.setColor(Color.YELLOW.darker());
			}
			else
			{
				graphics.setColor(Color.YELLOW);
			}
			graphics.draw(daeyaltClickbox);
			graphics.setColor(new Color(255, 255, 0, 15));
			graphics.fill(daeyaltClickbox);
		}

		return null;
	}
}
