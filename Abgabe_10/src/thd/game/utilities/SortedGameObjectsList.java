package thd.game.utilities;

import thd.gameobjects.base.GameObject;

import java.util.LinkedList;

/**
 * A custom LinkedList that automatically sorts {@link GameObject}s by their
 * distance to the background whenever a new object is added.
 */
public class SortedGameObjectsList extends LinkedList<GameObject> {

    /**
     * Inserts the specified GameObject at the correct position to maintain
     * the ascending sort order of distanceToBackground.
     *
     * @param toAdd the GameObject to be added
     * @return {@code true} (as specified by {@link java.util.Collection#add})
     */
    @Override
    public boolean add(GameObject toAdd) {
        int indexToSortIn = 0;
        for (GameObject gameObject : this) {
            if (gameObject.getDistanceToBackground() >= toAdd.getDistanceToBackground()) {
                break;
            }
            indexToSortIn++;
        }
        super.add(indexToSortIn, toAdd);
        return true;
    }
}
