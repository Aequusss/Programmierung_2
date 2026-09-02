package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;


/**
 * Manages complex, classic arcade flight paths inspired by Silkworm.
 */
class HelicopterMovementPattern extends MovementPattern {
    protected enum FlightBehavior {
        SWOOP_AND_LOOP, STRAFING_GUNSHIP, ZIG_ZAG
    }

    private final FlightBehavior selectedBehavior;
    private double internalAngle; // Used for mathematical loops
    private int behaviorPhase;
    private long phaseTimer;

    HelicopterMovementPattern() {
        super();
        FlightBehavior[] behaviors = FlightBehavior.values();
        // Randomly assign one of the 3 archetypes on spawn
        this.selectedBehavior = behaviors[random.nextInt(behaviors.length)];
        this.behaviorPhase = 0;
        this.internalAngle = 0;
        this.phaseTimer = System.currentTimeMillis();
    }

    @Override
    protected Position startPosition() {
        // Spawn off-screen right at varying strategic heights
        if (selectedBehavior == FlightBehavior.SWOOP_AND_LOOP) {
            return new Position(GameView.WIDTH + 100, 50); // Enters high
        } else if (selectedBehavior == FlightBehavior.STRAFING_GUNSHIP) {
            return new Position(GameView.WIDTH + 100, 150); // Enters mid-air
        } else {
            return new Position(GameView.WIDTH + 100, 80); // Zig-zag start
        }
    }

    @Override
    protected Position nextPosition() {
        // Legacy requirement for base class; actual movement is handled via custom updates
        return new Position(-200, GameView.HEIGHT / 2.0);
    }

    void executeArcadeFlight(Position currentPos, double speed, long gameTime) {
        switch (selectedBehavior) {
            case SWOOP_AND_LOOP:
                runSwoopAndLoop(currentPos, speed);
                break;
            case STRAFING_GUNSHIP:
                runStrafingGunship(currentPos, speed, gameTime);
                break;
            case ZIG_ZAG:
                runZigZag(currentPos, speed);
                break;
        }
    }

    private void runSwoopAndLoop(Position pos, double speed) {
        if (behaviorPhase == 0) {
            // Phase 0: Dive down-left aggressively
            pos.left(speed * 1.5);
            pos.down(speed * 1.2);
            if (pos.getX() < GameView.WIDTH * 0.7) {
                behaviorPhase = 1; // Start the loop when reaching the trigger line
                internalAngle = 0;
            }
        } else if (behaviorPhase == 1) {
            // Phase 1: Execute a smooth 360-degree loop using trigonometry
            internalAngle += 0.07; // Control rotation speed of the loop
            double offsetX = Math.sin(internalAngle) * 6.0;
            double offsetY = -Math.cos(internalAngle) * 6.0;

            pos.updateCoordinates(pos.getX() - speed + offsetX, pos.getY() + offsetY);

            if (internalAngle >= Math.PI * 2) {
                behaviorPhase = 2; // Loop complete, enter retreat phase
            }
        } else {
            // Phase 2: Escape up and out to the left
            pos.left(speed * 1.8);
            pos.up(speed * 0.5);
        }
    }

    private void runStrafingGunship(Position pos, double speed, long gameTime) {
        if (behaviorPhase == 0) {
            // Phase 0: Rush onto the right edge of the screen
            pos.left(speed * 1.5);
            if (pos.getX() <= GameView.WIDTH - 250) {
                behaviorPhase = 1;
                phaseTimer = System.currentTimeMillis();
            }
        } else if (behaviorPhase == 1) {
            // Phase 1: Hold ground horizontally and bob up/down smoothly while firing
            double bobbing = Math.sin(gameTime * 0.006) * 3.0;
            pos.down(bobbing);

            // Stand ground for 3.5 seconds
            if (System.currentTimeMillis() - phaseTimer > 3500) {
                behaviorPhase = 2;
            }
        } else {
            // Phase 2: Retreat upward and backward off-screen
            pos.left(speed * 2.0);
            pos.up(speed * 0.8);
        }
    }

    private void runZigZag(Position pos, double speed) {
        pos.left(speed * 1.2);
        if (behaviorPhase == 0) {
            pos.down(speed * 0.8);
            if (pos.getY() > GameView.HEIGHT * 0.5) {
                behaviorPhase = 1; // Bounce up
            }
        } else {
            pos.up(speed * 0.8);
            if (pos.getY() < 50) {
                behaviorPhase = 0; // Bounce down
            }
        }
    }
}