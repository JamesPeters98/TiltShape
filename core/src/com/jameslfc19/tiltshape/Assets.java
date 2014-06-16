package com.jameslfc19.tiltshape;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	
	static TextureAtlas atlas;
	static AssetManager manager;
	
	public static class Fonts {
		public static BitmapFont Bebas72;
	}
	
	private static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static void load(){
		manager = new AssetManager();
		manager.load("TiltShape.atlas", TextureAtlas.class);
		BitmapFontParameter param = new BitmapFontParameter();
		param.minFilter = TextureFilter.Linear;
		param.magFilter = TextureFilter.Linear;
		manager.load("fonts/72Bebas.fnt", BitmapFont.class, param);
	}
	
	public static boolean doneLoading(){
		if(manager != null){
		if(manager.update()){
			atlas = manager.get("TiltShape.atlas");
			Fonts.Bebas72 = manager.get("fonts/72Bebas.fnt");
			Fonts.Bebas72.setColor(0.75f, 0.75f, 0.75f, 1);
			return true;
		}
		}
		return false;
	}
	
	public static AssetManager getManager(){
		return manager;
	}
	
	public static Sprite getSprite(String path){
		if(sprites.get(path) == null){
			sprites.put(path, atlas.createSprite(path));
		}
		return new Sprite(sprites.get(path));
	}


}
