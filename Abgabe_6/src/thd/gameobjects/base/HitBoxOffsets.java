package thd.gameobjects.base;


/**
 * Stores the four offset values that define a hitbox relative to a game object.
 * This record is used as a simple, immutable data carrier.
 *
 * @param offsetX      horizontal offset from the object's x-coordinate
 * @param offsetY      vertical offset from the object's y-coordinate
 * @param offsetWidth  additional width (positive = wider, negative = narrower)
 * @param offsetHeight additional height (positive = taller, negative = shorter)
 */
public record HitBoxOffsets(double offsetX, double offsetY, double offsetWidth, double offsetHeight) {

    public static final HitBoxOffsets DEFAULT = new HitBoxOffsets(0, 0, 0, 0);
}
