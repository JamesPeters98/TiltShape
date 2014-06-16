package com.jameslfc19.tiltshape.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.jameslfc19.tiltshape.TiltShape;

public class DesktopLauncher {
	public static void main (String[] args) {
		if(args.length > 0){
			System.out.println(args[0]);
			if(args[0].equals("genImages")){
				Settings settings = new Settings();
				settings.maxWidth = 2048;
				settings.maxHeight = 2048;
				settings.paddingX = 2;
	        	settings.paddingY = 2;
	        	settings.filterMin = TextureFilter.Linear;
	        	settings.filterMag = TextureFilter.Linear;
				TexturePacker.process(settings, "../assets/raw-images", "../android/assets", "TiltShape");
			}
		}
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;
		config.width = 450;
		new LwjglApplication(new TiltShape(), config);
	}
}
