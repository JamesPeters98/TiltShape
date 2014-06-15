package com.jameslfc19.tiltshape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen{
	Game game;
	SpriteBatch batch;
	
	protected OrthographicCamera camera;
	float height = 2048;
	float width = 1200;
	private float physicalWidth = Gdx.graphics.getWidth();
	private float physicalHeight = Gdx.graphics.getHeight();
	protected float viewportHeight;
	protected float viewportWidth;

	
	//Sprites
	Sprite grid;
	
	public GameScreen(Game game){
		this.game = game;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, viewportWidth, viewportHeight);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.14f, 0.14f, 0.14f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		grid.draw(batch);
		batch.end();
	}
	
	public void initImages(){
		grid = Assets.getSprite("grid");
		grid.setPosition(viewportWidth/2-grid.getWidth()/2, viewportHeight/2-grid.getHeight()/2);
	}

	@Override
	public void resize(int width, int height) {
		physicalWidth = Gdx.graphics.getWidth();
		physicalHeight = Gdx.graphics.getHeight();
		calculateAspectRatio();
		camera.setToOrtho(false, viewportWidth, viewportHeight); 
		initImages();
	}
	
	private void calculateAspectRatio(){
		float aspect = width / height;

		if (physicalWidth / physicalHeight >= aspect) {
			viewportHeight = height;
			viewportWidth = viewportHeight * physicalWidth / physicalHeight;
		} else {
			viewportWidth = width;
			viewportHeight = viewportWidth * physicalHeight / physicalWidth;
		}
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
