package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;


public class Ball extends Actor
{
    public enum Direction {UP, RIGHT, DOWN, LEFT};

    // Actor variables
    private int speed = 3;
    private Direction direction;
    
    // Path variables
    private int[][] path;
    private int currentX = 0;
    private int currentY = 0;
    private int finalX = 0;
    private int finalY = 0;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;

    public Ball (int currentX, int currentY, int[][]path, int finalX, int finalY)
    {
    	this.finalX = finalX;
    	this.finalY = finalY;
    	this.currentX = currentX;
    	this.currentY = currentY;
    	this.path = path;
    	
    	setPosition(currentX*32, 1024-(currentY+1)*32);
    	
    	texture = new Texture(Gdx.files.internal("data/game/ball.png"));
    	numberOfFrames = texture.getWidth()/32;
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,32,32);
        
        findNewPath();
    }
    
    public void moveAction()
    {
    	// Create actions
        SequenceAction sequenceAction = new SequenceAction();
        MoveToAction moveToAction = new MoveToAction();

        // Set action parameters according to direction and speed
        if(direction == Direction.LEFT)
        {
            moveToAction.setPosition(getX()-32, getY());
            currentX--;
        } else if(direction == Direction.RIGHT)
        {
            moveToAction.setPosition(getX()+32, getY());
            currentX++;
        } else if(direction == Direction.DOWN)
        {
            moveToAction.setPosition(getX(), getY()-32);
            currentY++;
        } else if(direction == Direction.UP)
        {
            moveToAction.setPosition(getX(), getY()+32);
            currentY--;
        }
        moveToAction.setDuration(0.7f / speed);

        // Add actions to sequence
        // After moveToAction finishes, runnable action calls findNewPath method
        sequenceAction.addAction(moveToAction);
        sequenceAction.addAction(new RunnableAction(){
            @Override
            public void run() {
            	findNewPath();
            }});
        this.addAction(sequenceAction);
    }

	public void draw (SpriteBatch batch, float parentAlpha)
	{
		// Move sprite draw region
		spritePos = (spritePos+0.2) % numberOfFrames;
		
		sprite.setPosition(getX(), getY());
		sprite.setRegion((int)spritePos*32, 0, 32, 32);
	    sprite.draw(batch);
    }

	private void findNewPath()
	{
		// Path finder yazýlacak!!!
		// Bu kod deðiþtirilecek!!!
		
        if(currentX == finalX && currentY == finalY)
        {
    		// Actor reached to final point
        	this.remove();
        }
        else
        {
        	// Select new direction in path
			if(path[currentY][currentX+1] != 0 && direction != Direction.LEFT && currentX+1 < 32)
			{
		    	direction = Direction.RIGHT;
			} else if(path[currentY+1][currentX] != 0 && direction != Direction.UP && currentY+1 < 32)
			{
		    	direction = Direction.DOWN;
			} else if(path[currentY-1][currentX] != 0 && direction != Direction.DOWN && currentY-1 >= 0)
			{
		    	direction = Direction.UP;
			} else if(path[currentY][currentX-1] != 0 && direction != Direction.RIGHT && currentX-1 >= 0)
			{
		    	direction = Direction.LEFT;
			}
	    	moveAction();
        }
	}
}
