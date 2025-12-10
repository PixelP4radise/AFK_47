package pt.codered.afk_47.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Region(String name, List<Tree> treeList) {
    /**
     * Returns a NEW list containing all trees in this region,
     * but shuffled randomly so the bot doesn't look predictable.
     */
    public List<Tree> getShuffledTrees() {
        List<Tree> shuffled = new ArrayList<>(treeList); // Copy the list
        Collections.shuffle(shuffled); // Randomize order
        return shuffled;
    }
}
