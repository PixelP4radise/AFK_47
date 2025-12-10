package pt.codered.afk_47.model.data;

import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public record Tree(String name, List<BlockPos> logPositions, List<BlockPos> rootPositions) {

    private static final Random RANDOM = new Random();

    /**
     * Selects a random log block from the tree.
     * Use this to determine which specific block to aim at and break.
     *
     * @return A random BlockPos from logPositions, or null if empty.
     */
    public BlockPos getRandomLog() {
        return getRandomElement(logPositions);
    }

    /**
     * Selects a random root position.
     * Use this as the Baritone Goal (GoalBlock) to walk towards the tree.
     *
     * @return A random BlockPos from rootPositions, or null if empty.
     */
    public BlockPos getRandomRoot() {
        return getRandomElement(rootPositions);
    }

    /**
     * Helper to safely get a random element from a list.
     */
    private BlockPos getRandomElement(List<BlockPos> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(RANDOM.nextInt(list.size()));
    }
}
