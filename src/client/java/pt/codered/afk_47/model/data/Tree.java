package pt.codered.afk_47.model.data;

import java.util.List;
import java.util.Random;

// 1. The Main Tree Record
public record Tree(String name, List<TreePosition> positions) {

    private static final Random RANDOM = new Random();

    /**
     * Pick a random standing position (root) to walk to.
     */
    public TreePosition getRandomPosition() {
        if (positions == null || positions.isEmpty()) return null;
        return positions.get(RANDOM.nextInt(positions.size()));
    }
}
