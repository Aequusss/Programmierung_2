package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.Color;

public class Square extends GameObject {


    public Square(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(100, 100);
        speedInPixel = 5;
        width = 30;
        height = 30;
        size = 30;
    }

    @Override
    public void updateStatus() {
        if (position.getX() > GameView.WIDTH) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void updatePosition() {

        position.right(speedInPixel);
    }

    @Override
    public void addToCanvas() {

        gameView.addRectangleToCanvas(
                position.getX(),
                position.getY(),
                width,
                height,
                3, false,
                Color.RED
        );
    }
}