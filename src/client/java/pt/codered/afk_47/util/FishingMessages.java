package pt.codered.afk_47.util;

import pt.codered.afk_47.model.data.SeaCreature;

public final class FishingMessages {
    private FishingMessages() {
    }

    public static final SeaCreature Squid = new SeaCreature("Squid", "A Squid appeared.", Conditions.minLevel(1));

    public static final SeaCreature SeaWalker = new SeaCreature("Sea Walker", "You caught a Sea Walker.", Conditions.minLevel(1));

    public static final SeaCreature NIGHT_SQUID = new SeaCreature(
            "Night Squid",
            "Pitch darkness reveals a Night Squid",
            Conditions.minLevel(3).and(
                    // Condition A: Night AND Dark Bait
                    Conditions.isNight().and(Conditions.hasBait("Dark Bait"))
                            // OR Condition B: Park AND Raining
                            .or(Conditions.onIsland("The Park").and(Conditions.isRaining()))
            )
    );
}
