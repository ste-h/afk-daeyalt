package com.afkdaeyalt;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AfkDaeyaltPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(AfkDaeyaltPlugin.class);
		RuneLite.main(args);
	}
}