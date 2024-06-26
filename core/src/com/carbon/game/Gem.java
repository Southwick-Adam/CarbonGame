package com.carbon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Gem extends GridLogic{
    static GemSpawner spawner;
    public int value;
    public int[] cellCoords;
    public Texture img; //= new Texture(Gdx.files.internal("testShapes/gem.png"));
    public Vector2 position;

    public Gem(int v, int[] c, GemSpawner gs) {
        Gem.spawner = gs;
        value = v;
        switch(value){
            case 100:
                img = new Texture(Gdx.files.internal("testShapes/gem4.png"));
                break;
            case 200:
                img = new Texture(Gdx.files.internal("testShapes/gem3.png"));
                break;
            case 300:
                img = new Texture(Gdx.files.internal("testShapes/gem2.png"));
                break;
            case 400:
                img = new Texture(Gdx.files.internal("testShapes/gem1.png"));
                break;
            case 500:
                img = new Texture(Gdx.files.internal("testShapes/gem1.png"));
                break;
        }
        cellCoords = c;
        position = v_cellToWorld(c[0], c[1]);
    }

    public void dispose() {
        spawner.collected(cellCoords);
        img.dispose();
    }
}
