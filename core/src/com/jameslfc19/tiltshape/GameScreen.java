package com.jameslfc19.tiltshape;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jameslfc19.tiltshape.Assets.Fonts;

public class GameScreen implements Screen{
	Game game;
	SpriteBatch batch;
	Random random;
	
	protected OrthographicCamera camera;
	float height = 1700;
	float width = 1100;
	private float physicalWidth = Gdx.graphics.getWidth();
	private float physicalHeight = Gdx.graphics.getHeight();
	protected float viewportHeight;
	protected float viewportWidth;

	int score;
	float timelimit;
	int colour;
	int shape;
	
	float x;
	float y;
	
	//Sprites
	Sprite grid;
	Sprite scoreBox;
	Sprite highscoreBox;
	Sprite red;
	Sprite blue;
	Sprite green;
	Sprite[] circle_dark = new Sprite[3];
	Sprite[] triangle_dark = new Sprite[3];
	Sprite[] square_dark = new Sprite[3];
	Sprite circle_white;
	Sprite triangle_white;
	Sprite square_white;
	
	public GameScreen(Game game){
		this.game = game;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		random = new Random();
		camera.setToOrtho(false, viewportWidth, viewportHeight);
		score = 0;
		timelimit = 5;
		x = viewportWidth/2;
		y = viewportHeight/2;
	}

	@Override
	public void render(float delta) {
		gameLogic(delta);
		timelimit -= delta;
		if(timelimit <= 0){
			timelimit = 5;
			change();
		}
		Gdx.gl.glClearColor(0.04f, 0.04f, 0.04f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		grid.draw(batch);
		scoreBox.draw(batch);
		highscoreBox.draw(batch);
		for(int i=0;i<=2;i++){
			square_dark[i].draw(batch);
			triangle_dark[i].draw(batch);
			circle_dark[i].draw(batch);
		}
		switch(colour){
			default: System.out.println("No colour");
			case 0: red.draw(batch);
			break;
			case 1: blue.draw(batch);
			break;
			case 2: green.draw(batch);
			break;
		}
		switch(shape){
		default: System.out.println("No shape");
		case 0: square_white.draw(batch);
				square_white.setPosition(x, y);
		break;
		case 1: triangle_white.draw(batch);
				triangle_white.setPosition(x, y);
		break;
		case 2: circle_white.draw(batch);
				circle_white.setPosition(x, y);
		break;
	}
		Fonts.Bebas72.draw(batch, "Colour", grid.getX()+grid.getWidth()-Fonts.Bebas72.getBounds("Colour").width, grid.getY()+grid.getHeight()+325);
		Fonts.Bebas72.draw(batch, "Time Limit", grid.getX(), grid.getY()+grid.getHeight()+325);
		Fonts.Bebas72.draw(batch, ""+(double)Math.round(timelimit * 10) / 10, grid.getX(), grid.getY()+grid.getHeight()+225);
		Fonts.Bebas72.draw(batch, "Score", grid.getX(), grid.getY()-50);
		Fonts.Bebas72.draw(batch, "Highscore", grid.getX()+grid.getWidth()-Fonts.Bebas72.getBounds("Highscore").width, grid.getY()-50);
		batch.end();
	}
	
	public void initImages(){
		grid = Assets.getSprite("grid");
		grid.setPosition(viewportWidth/2-grid.getWidth()/2, viewportHeight/2-grid.getHeight()/2);
		scoreBox = Assets.getSprite("scorebox");
		scoreBox.setPosition(grid.getX(), grid.getY()-325);
		highscoreBox = Assets.getSprite("scorebox");
		highscoreBox.setPosition(grid.getX()+grid.getWidth()-highscoreBox.getWidth(), grid.getY()-325);
		red = Assets.getSprite("red");
		red.setPosition(grid.getX()+grid.getWidth()-red.getWidth(), grid.getY()+grid.getHeight()+30);
		blue = Assets.getSprite("blue");
		blue.setPosition(red.getX(), red.getY());
		green = Assets.getSprite("green");
		green.setPosition(red.getX(), red.getY());
		for(int i=0;i<=2;i++){
			square_dark[i] = Assets.getSprite("square_dark");
			square_dark[i].setPosition(grid.getX()+115, grid.getY()+106+(307*(i)));
			triangle_dark[i] = Assets.getSprite("triangle_dark");
			triangle_dark[i].setPosition(grid.getX()+413, grid.getY()+106+(307*(i)));
			circle_dark[i] = Assets.getSprite("circle_dark");
			circle_dark[i].setPosition(grid.getX()+720, grid.getY()+106+(307*(i)));
		}
		square_white = Assets.getSprite("square_white");
		triangle_white = Assets.getSprite("triangle_white");
		circle_white = Assets.getSprite("circle_white");
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
	
	private void change(){
		int randomInt;
		do {
			randomInt = random.nextInt(3);
			System.out.println("Same colour");
		} while(colour == randomInt);
		colour = randomInt;
		
		int randomInt2;
		do {
			randomInt2 = random.nextInt(3);
			System.out.println("Same colour");
		} while(shape == randomInt2);
		shape = randomInt;
		
		System.out.println("Changing colour: "+colour);
	}
	
	private void gameLogic(float delta){
		x = Gdx.input.getX();
		y = Gdx.input.getX()+50;
		if(x<=grid.getX()+20)x=grid.getX()+20;
		if(x+currentShape().getWidth()>=grid.getX()+grid.getWidth()-20)x=grid.getX()+grid.getWidth()-20-currentShape().getWidth();
		if(y<=grid.getY()+20)y=grid.getY()+20;
	}
	
	
	public Sprite currentShape(){
		switch(shape){
		default: System.out.println("No shape");
		case 0: return square_white;
		case 1: return triangle_white;
		case 2: return circle_white;
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
