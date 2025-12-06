package pt.codered.afk_47.model.data;

import net.minecraft.client.MinecraftClient;

import java.util.function.Predicate;

public record SeaCreature(
        String name,
        String chatMessage,
        Predicate<MinecraftClient> spawnCondition
) {
}
