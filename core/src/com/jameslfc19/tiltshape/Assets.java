package com.jameslfc19.tiltshape;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	
	static TextureAtlas atlas;
	static AssetManager manager;
	
	private static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static void load(){
		manager = new AssetManager();
		manager.load("TiltShape.atlas", TextureAtlas.class);
	}
	
	public static boolean doneLoading(){
		if(manager != null){
		if(manager.update()){
			atlas = manager.get("TiltShape.atlas");
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
