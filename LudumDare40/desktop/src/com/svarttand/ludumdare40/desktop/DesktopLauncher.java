package com.svarttand.ludumdare40.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.svarttand.ludumdare40.Application;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 800;
		config.title = "WorldDomenation";
		config.addIcon("Icon.png", FileType.Internal);
		new LwjglApplication(new Application(), config);
	}
}
