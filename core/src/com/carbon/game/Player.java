package com.carbon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class Player extends FreeRoam {
    public final GameScreen screen;
    //constants
    public static final int TRAIN_CARBON = 2;
    public static final int BUS_CARBON = 3;

    public static int carbon = 0;
    public static int energy;
    public static int score = 0;
    public boolean hide = false;
    public int mode = 1; // 1-walking, 2-bike, 3-car
    public float exhausted = 1;
    //texture
    private final Texture sprite = new Texture(Gdx.files.internal("testShapes/square.png"));
    public Texture img = sprite;
    public Transit transit = null;
    public Car car = null;

    public Player(GameScreen screen, int e, int x, int y) {
        super(x, y);
        this.screen = screen;
        energy = e;
        Timer timer = new Timer();
        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run () {
                changeEnergy(1);
            }
        }, 0, 1);
    }

    public void collectGem(Gem gem) {
        score += gem.value;
        gem.dispose();
        System.out.println("Gem Score +".concat(String.valueOf(gem.value)));
    }

    public void transitCost(boolean train) {
        if (train) {
            carbon += TRAIN_CARBON;
        } else {
            carbon += BUS_CARBON;
        }
    }

    //MOVEMENT FUNCTIONS
    public void arriveAtTarget() {
        if (energy > 0 && mode != 3) {
            energy--;
        }
        super.arriveAtTarget();
    }

    public void nextCell() {
        arriveAtTarget();
        path.remove(0);
        if (path.isEmpty()) {
            arrived();
            return;
        }
        setTargets();
    }

    private void arrived() {
        if (mode == 1) {
            gemCheck();
        }
        if (mode == 3) {
            mode = 1;
            car.dropOff();
            img = sprite;
            screen.allowClick();
        }
    }

    public void exit() {
        screen.metroVision = false;
        screen.building = null;
        hide = false;
        transit = null;
    }

    private void gemCheck() {
        int i = 0;
        for (Gem gem : screen.gemList) {
            if (cellX == gem.cellCoords[0] && cellY == gem.cellCoords[1]) {
                screen.gemList.remove(i);
                collectGem(gem);
                return;
            }
            i++;
        }
    }

    public void changeEnergy(int num) {
        if (energy < 100) {
            energy += num;
        }
        if (energy <= 10) {
            exhausted = (float) 0.2;
        }
        if (exhausted < 1 && energy >= 10) {
            exhausted = 1;
        }
    }

    public void dispose() {
        img.dispose();
    }
}