package com.jameslfc19.tiltshape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoadingScreen implements Screen {
	Game game;
	BitmapFont font;
	SpriteBatch batch;
	
	boolean isLoaded;
	
	public LoadingScreen(Game game){
		this.game = game;
		System.out.println("New Loading Screen");
		font = new BitmapFont(Gdx.files.internal("fonts/72Bebas.fnt"));
		batch = new SpriteBatch();
		Assets.load();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.14f, 0.14f, 0.14f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if(Assets.doneLoading() && !isLoaded) {
			System.out.println("Loaded");
			game.setScreen(new GameScreen(game));
			isLoaded = true;
		} else {
			font.draw(batch, "Loading "+Assets.getManager().getProgress(), 0, 0);
			System.out.println(""+Assets.getManager().getProgress());
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
