package pt.codered.afk_47.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Island(String name, List<Region> regions, String warpCommand) {

    /**
     * Gets the list of regions for this island.
     * You usually process these in order (Region 1 -> Region 2).
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * Returns a NEW list containing all regions in this island,
     * shuffled randomly.
     */
    public List<Region> getShuffledRegions() {
        List<Region> shuffled = new ArrayList<>(regions); // Copy the list
        Collections.shuffle(shuffled); // Randomize order
        return shuffled;
    }
}
