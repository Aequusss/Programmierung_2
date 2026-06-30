package thd.game.managers;


import thd.game.level.Level;
import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;


import thd.gameobjects.unmovable.*;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

abstract class UserControlledGameObjectPool {


    protected final GameView gameView;
    protected PlayerHelicopter playerHelicopter;
    protected Level level;


    /**
     * Constructs a new UserControlledGameObjectPool.
     *
     * @param gameView the game view for rendering and keyboard input
     */
    UserControlledGameObjectPool(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Processes keyboard input for the current frame.
     * Queries all currently pressed keys and delegates to {@link #processKeyCode(int)}.
     */
    protected void processFrame() {
        // Reset shooting state each frame so it only shows while space is held
        if (playerHelicopter != null) {
            playerHelicopter.resetShot();
        }

        ArrayList<Integer> pressedKeys = new ArrayList<>(Arrays.asList(gameView.keyCodesOfCurrentlyPressedKeys()));
        for (int keyCode : pressedKeys) {
            processKeyCode(keyCode);
        }
    }

    /**
     * Helper method to evaluate a single key press and trigger corresponding actions.
     * Supports WASD, arrow keys, and spacebar.
     *
     * @param keyCode the key code of the pressed key
     */
    private void processKeyCode(int keyCode) {
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            playerHelicopter.left();
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            playerHelicopter.right();
        } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            playerHelicopter.up();
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            playerHelicopter.down();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            playerHelicopter.shoot();
        }
    }
}
