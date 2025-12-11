package pt.codered.afk_47.model.data;

import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;

public record TreePosition(BlockPos root, List<BlockPos> logs) {

    private static final Random RANDOM = new Random();

    /**
     * Gets a random log that can be broken from THIS specific root.
     */
    public BlockPos getRandomLog() {
        if (logs == null || logs.isEmpty()) return null;
        return logs.get(RANDOM.nextInt(logs.size()));
    }
}
