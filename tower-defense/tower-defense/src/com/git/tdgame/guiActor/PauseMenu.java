package com.git.tdgame.guiActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PauseMenu extends Actor{
	
	ShapeRenderer shapeRenderer;
	
	public PauseMenu() {
		shapeRenderer = new ShapeRenderer();
	}
	
	public void draw (SpriteBatch batch, float parentAlpha)
    {
		batch.end();
    	Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
	    shapeRenderer.begin(ShapeType.FilledRectangle);
   		shapeRenderer.setColor(new Color(0.7f, 0.7f, 0.4f, 0.5f));
   		shapeRenderer.filledRect(0, 0, getStage().getWidth(), getStage().getHeight());
   		shapeRenderer.end();
   		
   		Gdx.gl.glDisable(GL10.GL_BLEND);
        batch.begin();
    }
}
