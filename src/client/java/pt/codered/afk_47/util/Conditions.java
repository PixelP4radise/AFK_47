package pt.codered.afk_47.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class Conditions {
    public static Predicate<MinecraftClient> always() {
        return client -> true;
    }

    public static Predicate<MinecraftClient> isRaining() {
        return client -> {
            if (client.world == null) return false;
            return client.world.isRaining();
        };
    }

    public static Predicate<MinecraftClient> isThundering() {
        return client -> {
            if (client.world == null) return false;
            return client.world.isThundering();
        };
    }

    public static Predicate<MinecraftClient> minLevel(int level) {
        return client -> {
            return false;

            //todo: get a way to check player fishing level
        };
    }

    public static Predicate<MinecraftClient> onIsland(String islandName) {
        return client -> ScoreboardUtils.getLocationString().equalsIgnoreCase(islandName);
    }

    public static Predicate<MinecraftClient> isNight() {
        return client -> {
            int hour = ScoreboardUtils.getSkyblockTime24h();
            if (hour == -1) return false; // Time not visible

            // Skyblock Night is 7 PM (19) to 6 AM (6)
            return hour >= 19 || hour < 6;
        };
    }

    public static Predicate<MinecraftClient> hasBait(String baitName) {
        return client -> {
            if (client.player == null) return false;
            for (ItemStack stack : client.player.getInventory().getMainStacks()) {
                String name = stack.getName().getString().replaceAll("§[0-9a-fk-or]", "");
                if (name.contains(baitName)) return true;
            }
            return false;
        };
    }
}
