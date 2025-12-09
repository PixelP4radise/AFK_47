package pt.codered.afk_47.util;

import pt.codered.afk_47.model.data.SeaCreature;

public final class FishingMessages {
    private FishingMessages() {
    }

    // ||||||||||||||||||||||||||||||||||||||||||||
    // ||                                        ||
    // ||           LOGIC & AUTOMATION           ||
    // ||                                        ||
    // ||||||||||||||||||||||||||||||||||||||||||||

    private static final java.util.List<SeaCreature> ALL_CREATURES = new java.util.ArrayList<>();

    // This static block runs ONCE when the game starts.
    // It automatically finds every 'SeaCreature' variable in this class and adds it to the list.
    static {
        try {
            for (java.lang.reflect.Field field : FishingMessages.class.getFields()) {
                // Check if the variable is a SeaCreature
                if (field.getType() == SeaCreature.class) {
                    // Get the value (null because it's static) and add to list
                    SeaCreature creature = (SeaCreature) field.get(null);
                    ALL_CREATURES.add(creature);
                }
            }
        } catch (IllegalAccessException e) {
            pt.codered.afk_47.util.ModLogger.error("Failed to load Sea Creatures list!", e);
        }
    }

    /**
     * @return The complete catalog of every defined Sea Creature.
     */
    public static java.util.List<SeaCreature> getAll() {
        return java.util.Collections.unmodifiableList(ALL_CREATURES);
    }

    /**
     * Filters the catalog to find which mobs can spawn RIGHT NOW.
     * Useful for overlays or debug.
     */
    public static java.util.List<SeaCreature> getAvailableMobs(net.minecraft.client.MinecraftClient client) {
        if (client.player == null || client.world == null) return java.util.Collections.emptyList();

        return ALL_CREATURES.stream()
                .filter(creature -> creature.spawnCondition().test(client)) //todo: ter a certeza que o client passa todas as informações necessárias
                .toList(); // Java 16+ method (Fabric 1.21 uses Java 21)
    }

    // ||||||||||||||||||||||||||||||||||||||||||||
    // ||                                        ||
    // ||            WATER CREATURES             ||
    // ||                                        ||
    // ||||||||||||||||||||||||||||||||||||||||||||

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

    public static final SeaCreature SeaGuardian = new SeaCreature("Sea Guardian", "You stumbled upon a Sea Guardian.", Conditions.minLevel(5));

    public static final SeaCreature SeaWitch = new SeaCreature("Sea Witch", "It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious!", Conditions.minLevel(7));

    public static final SeaCreature SeaArcher = new SeaCreature("Sea Archer", "You reeled in a Sea Archer.", Conditions.minLevel(9));

    public static final SeaCreature RiderOfTheDeep = new SeaCreature("Rider of the Deep", "The Rider of the Deep has emerged.", Conditions.minLevel(11));

    public static final SeaCreature CatFish = new SeaCreature("Catfish", "Huh? A Catfish!", Conditions.minLevel(13));

    public static final SeaCreature CarrotKing = new SeaCreature("Carrot King", "Is this even a fish? It's the Carrot King!", Conditions.minLevel(14).and(Conditions.hasBait("Carrot Bait")));

    public static final SeaCreature Agarimoo = new SeaCreature("Agarimoo", "Your Chumcap Bucket trembles, it's an Agarimoo.", Conditions.minLevel(15)); //todo: add condition of chumcap bucket

    public static final SeaCreature SeaLeech = new SeaCreature("Sea Leech", "Gross! A Sea Leech!", Conditions.minLevel(16));

    public static final SeaCreature GuardianDefender = new SeaCreature("Guardian Defender", "You've discovered a Guardian Defender of the sea.", Conditions.minLevel(17));

    public static final SeaCreature DeepSeaProtector = new SeaCreature("Deep Sea Protector", "You have awoken the Deep Sea Protector, prepare for a battle!", Conditions.minLevel(18));

    public static final SeaCreature WaterHydra = new SeaCreature("Water Hydra", "The Water Hydra has come to test your Strength.", Conditions.minLevel(19));

    // Oasis

    public static final SeaCreature OasisRabbit = new SeaCreature("Oasis Rabbit", "An Oasis Rabbit appears from the water", Conditions.minLevel(10).and(Conditions.onIsland("Oasis")));

    public static final SeaCreature OasisSheep = new SeaCreature("Oasis Sheep", "An Oasis Sheep appears from the water", Conditions.minLevel(10).and(Conditions.onIsland("Oasis")));

    // Crystal Hollows

    public static final SeaCreature WaterWorm = new SeaCreature("Water Worm", "A Water Worm surfaces!", Conditions.minLevel(15).and(Conditions.onIsland("Goblin Holdout")));

    public static final SeaCreature PoisonedWaterWorm = new SeaCreature("Poisoned Water Worm", "A Poisoned Water Worm surfaces!", Conditions.minLevel(17).and(Conditions.onIsland("Goblin Holdout")));

    public static final SeaCreature AbyssalMiner = new SeaCreature("Abyssal Miner", "An Abyssal Miner breaks out of the water!", Conditions.minLevel(24).and(Conditions.onIsland("Mithril Deposits").or(Conditions.onIsland("Precursor Remnants").or(Conditions.onIsland("Jungle Ruins")))));


    // Spooky Festival todo: adicionar maneira para verificar se é spooky festival

    public static final SeaCreature Scarecrow = new SeaCreature("Scarecrow", "Phew! It's only a scarecrow.", Conditions.minLevel(9));

    public static final SeaCreature Nightmare = new SeaCreature("Nightmare", "You hear trotting from beneath the waves, you caught a Nightmare", Conditions.minLevel(14));

    public static final SeaCreature Werewolf = new SeaCreature("Werewolf", "It must be a full moon, it's a Werewolf!", Conditions.minLevel(17));

    public static final SeaCreature PhantomFisher = new SeaCreature("Phatom Fisher", "The spirit of a long lost Phantom Fisher has come to haunt you.", Conditions.minLevel(21));

    public static final SeaCreature GrimReaper = new SeaCreature("Grim Reaper", "This can't be! The manifestation of death himself!", Conditions.minLevel(26));

    // Jerry's Worshop

    public static final SeaCreature FrozenSteve = new SeaCreature("Frozen Steve", "Frozen Steve fell into the pond long ago, never to resurface... until now!", Conditions.minLevel(5).and(Conditions.onIsland("Jerry's Workshop")));

    public static final SeaCreature Frosty = new SeaCreature("Frosty", "Its a Snowman! It looks harmless.", Conditions.minLevel(6).and(Conditions.onIsland("Jerry's Workshop")));

    public static final SeaCreature Grinch = new SeaCreature("Grinch", "The Grinch stole Jerry's Gifts...get them back!", Conditions.minLevel(13).and(Conditions.onIsland("Jerry's Workshop")));

    public static final SeaCreature Nutcracker = new SeaCreature("Nutcracker", "You found a forgotten Nutcracker laying beneath the ice.", Conditions.minLevel(28).and(Conditions.onIsland("Jerry's Workshop")));

    public static final SeaCreature Yeti = new SeaCreature("Yeti", "What is this creature!?", Conditions.minLevel(25).and(Conditions.onIsland("Jerry's Workshop")));

    public static final SeaCreature Reindrake = new SeaCreature("Reindrake", "A Reindrake forms from the depths.", Conditions.minLevel(35).and(Conditions.onIsland("Jerry's Workshop")));

    // Fishing Festival todo: adicionar maneira para verificar se é fishing festival

    public static final SeaCreature NurseShark = new SeaCreature("Nurse Shark", "A tiny fin emerges from the water, you've caught a Nurse Shark...", Conditions.minLevel(5));

    public static final SeaCreature BlueShark = new SeaCreature("Blue Shark", "You spot a fin as blue as the water it came from, it's a Blue Shark.", Conditions.minLevel(10));

    public static final SeaCreature TigerShark = new SeaCreature("Tiger Shark", "A striped beast bounds from the depths, the wild Tiger Shark!", Conditions.minLevel(18));

    public static final SeaCreature GreatTigerShark = new SeaCreature("Great Tiger Shark", "Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!", Conditions.minLevel(24));

    // BackWater Bayou

    public static final SeaCreature TrashGobbler = new SeaCreature("Trash Gobbler", "The Trash Gobbler is hungry for you!", Conditions.minLevel(5).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature DumpsterDiver = new SeaCreature("Dumpster Diver", "A Dumpster Diver has emerged from the swamp!", Conditions.minLevel(5).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature Banshee = new SeaCreature("Banshee", "The desolate wail of a Banshee breaks the silence.", Conditions.minLevel(10).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature BayouSludge = new SeaCreature("Bayou Sludge", "A swampy mass of slime emerges, the Bayou Sludge!", Conditions.minLevel(15).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature Alligator = new SeaCreature("Alligator", "A long snout breaks the surface of the water. It's an Alligator!", Conditions.minLevel(20).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature Titanoboa = new SeaCreature("Titanoboa", "A massive Titanoboa surfaces. It's body stretches as far as the eye can see.", Conditions.minLevel(30).and(Conditions.onIsland("Backwater Bayou")));

    //HotSpot

    public static final SeaCreature FrogMan = new SeaCreature("Frog Man", "Is it a frog? Is it a man? Well, yes, sorta, IT'S FROG MAN!!!!!!", Conditions.minLevel(5).and(Conditions.onIsland("Backwater Bayou"))); //todo: Add condition to check for hotspot

    public static final SeaCreature SnappingTurtle = new SeaCreature("Snapping Turtle", "A Snapping Turtle is coming your way, and it's ANGRY!", Conditions.minLevel(10).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature BlueRingedOctopus = new SeaCreature("Blue Ringed Octopus", "A garish set of tentacles arise. It's a Blue Ringed Octopus!", Conditions.minLevel(35).and(Conditions.onIsland("Backwater Bayou")));

    public static final SeaCreature WikiTiki = new SeaCreature("Wiki Tiki", "The water bubbles and froths. A massive form emerges- you have disturbed the Wiki Tiki! You shall pay the price.", Conditions.minLevel(40).and(Conditions.onIsland("Backwater Bayou")));

    //Galatea

    public static final SeaCreature Bogged = new SeaCreature("Bogged", "You've hooked a Bogged!", Conditions.minLevel(5).and(Conditions.onIsland("Moonglade Marsh").or(Conditions.onIsland("Torrhus Canyon").or(Conditions.onIsland("Lunarise")))));

    public static final SeaCreature WetWing = new SeaCreature("Wetwing", "Look! A Wetwing emerges!", Conditions.minLevel(7).and(Conditions.onIsland("Moonglade Marsh").or(Conditions.onIsland("Torrhus Canyon").or(Conditions.onIsland("Lunarise")))));

    public static final SeaCreature Tadgang = new SeaCreature("Tadgang", "A gang of Liltads!", Conditions.minLevel(9).and(Conditions.onIsland("Moonglade Marsh").or(Conditions.onIsland("Torrhus Canyon").or(Conditions.onIsland("Lunarise")))));

    public static final SeaCreature Ent = new SeaCreature("Ent", "You've hooked an Ent, as ancient as the forest itself.", Conditions.minLevel(12).and(Conditions.onIsland("Moonglade Marsh").or(Conditions.onIsland("Torrhus Canyon").or(Conditions.onIsland("Lunarise")))));

    public static final SeaCreature TheLochEmperor = new SeaCreature("The Loch Emperor", "The Loch Emperor arises from the depths.", Conditions.minLevel(20).and(Conditions.onIsland("Moonglade Marsh").or(Conditions.onIsland("Torrhus Canyon").or(Conditions.onIsland("Lunarise")))));

    // ||||||||||||||||||||||||||||||||||||||||||||
    // ||                                        ||
    // ||             LAVA CREATURES             ||
    // ||                                        ||
    // ||||||||||||||||||||||||||||||||||||||||||||

    // Crystal Hollows

    public static final SeaCreature FlamingWorm = new SeaCreature("Flaming Worm", "A Flaming Worm surfaces from the depths!", Conditions.minLevel(19).and(Conditions.onIsland("Precursor Remnants")));

    public static final SeaCreature LavaBlaze = new SeaCreature("Lava Blaze", "A Lava Blaze has surfaced from the depths!", Conditions.minLevel(20).and(Conditions.onIsland("Magma Fields")));

    public static final SeaCreature LavaPigman = new SeaCreature("Lava Pigman", "A Lava Pigman arose from the depths!", Conditions.minLevel(22).and(Conditions.onIsland("Magma Fields")));

    // Crimson Isle

    public static final SeaCreature MagmaSlug = new SeaCreature("Magma Slug", "From Beneath the lava appears a Magma Slug.", Conditions.minLevel(27).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature Moogma = new SeaCreature("Moogma", "You hear a faint Moo from the lava... A Moogma appears.", Conditions.minLevel(28).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature LavaLeech = new SeaCreature("Lava Leech", "A small but fearsome Lava Leech emerges.", Conditions.minLevel(30).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature PyroclasticWorm = new SeaCreature("Pyroclastic Worm", "You feel the heat radiating as a Pyroclastic Worm surfaces.", Conditions.minLevel(31).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature LavaFlame = new SeaCreature("Lava Flame", "A Lava Flame flies out from beneath the lava.", Conditions.minLevel(33).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature FireEel = new SeaCreature("Fire Eel", "A Fire Eel slithers out from the depths.", Conditions.minLevel(34).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature Taurus = new SeaCreature("Taurus", "Taurus and his steed emerge.", Conditions.minLevel(35).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature Plhlegblast = new SeaCreature("Plhlegblast", "WOAH! A Plhlegblast appeared.", Conditions.minLevel(36).and(Conditions.onIsland("Crimson Isle")));

    // todo: verificar trophy corrente
    public static final SeaCreature Thunder = new SeaCreature("Thunder", "You hear a massive rumble as Thunder emerges.", Conditions.minLevel(36).and(Conditions.onIsland("Crimson Isle"))); //todo: Novice Trophy Fisher

    public static final SeaCreature LordJawbus = new SeaCreature("Lord Jawbus", "You have angered a legendary creature...Lord Jawbus has arrived", Conditions.minLevel(45).and(Conditions.onIsland("Crimson Isle"))); //todo: Adept Trophy Fisher

    // Hotspot
    public static final SeaCreature FriedChicken = new SeaCreature("Fried Chicken", "Smells of burning. Must be a Fried Chicken.", Conditions.minLevel(14).and(Conditions.onIsland("Crimson Isle"))); // todo: Add condition to check for hotspot

    public static final SeaCreature FireproofWitch = new SeaCreature("Fireproof Witch", "Trouble's brewing, it's a Fireproof Witch!.", Conditions.minLevel(16).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature FieryScuttler = new SeaCreature("Fiery Scuttler", "A Fiery Scuttler inconspicuously waddles up to you, friends in tow.", Conditions.minLevel(37).and(Conditions.onIsland("Crimson Isle")));

    public static final SeaCreature Ragnarok = new SeaCreature("Ragnarok", "The sky darkens and the air thickens. The end times are upon us: Ragnarok is here.", Conditions.minLevel(47).and(Conditions.onIsland("Crimson Isle")));
}
