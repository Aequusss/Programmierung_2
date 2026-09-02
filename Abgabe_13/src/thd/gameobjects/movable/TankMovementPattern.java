package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

/**
 * Implements a tactical stop-and-go combat rhythm for ground tanks.
 */
class TankMovementPattern extends MovementPattern {
    private enum TankState {
        ADVANCING, AIMING, LUNGING
    }

    private TankState currentState;
    private long stateTimer;

    TankMovementPattern() {
        super();
        this.currentState = TankState.ADVANCING;
        this.stateTimer = System.currentTimeMillis();
    }

    @Override
    protected Position startPosition() {

        return new Position(GameView.WIDTH + 100, 600);
    }

    @Override
    protected Position nextPosition() {
        return new Position(-200, 600);
    }

    void executeTankTactics(Position pos, double normalSpeed) {
        long currentMs = System.currentTimeMillis();

        switch (currentState) {
            case ADVANCING:
                // Roll left at standard speed
                pos.left(normalSpeed);

                // Every 3 seconds, halt to lock onto the player
                if (currentMs - stateTimer > 3000) {
                    currentState = TankState.AIMING;
                    stateTimer = currentMs;
                }
                break;

            case AIMING:
                // HALT: Stand ground completely to concentrate fire accuracy.
                // This gives the tank a heavy, deliberate combat presence.
                if (currentMs - stateTimer > 1200) { // Stay stationary for 1.2 seconds
                    currentState = TankState.LUNGING;
                    stateTimer = currentMs;
                }
                break;

            case LUNGING:
                // SURGE: Lunge forward at double speed to catch the player off guard
                pos.left(normalSpeed * 2.2);

                if (currentMs - stateTimer > 1500) { // Surge duration
                    currentState = TankState.ADVANCING;
                    stateTimer = currentMs;
                }
                break;
        }
    }
}