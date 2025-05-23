package dev.lopyluna.dndecor.register.client;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import dev.lopyluna.dndecor.DnDecor;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DnDecorSpriteShifts {
    public static final Map<DyeColor, SpriteShiftEntry>
            DYED_BELTS = new EnumMap<>(DyeColor.class), DYED_OFFSET_BELTS = new EnumMap<>(DyeColor.class),
            DYED_DIAGONAL_BELTS = new EnumMap<>(DyeColor.class);

    private static final Map<DyeColor, Couple<CTSpriteShiftEntry>>
            COLORED_STORAGE_TOP = new HashMap<>(), COLORED_STORAGE_FRONT = new HashMap<>(),
            COLORED_STORAGE_SIDE = new HashMap<>(), COLORED_STORAGE_BOTTOM = new HashMap<>();

    public static final SpriteShiftEntry BELT = get("block/belt", "block/belt_scroll"),
            BELT_OFFSET = get("block/belt_offset", "block/belt_scroll"),
            BELT_DIAGONAL = get("block/belt_diagonal", "block/belt_diagonal_scroll");

    static {
        populateMaps();
    }

    private static void populateMaps() {
        for (DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            DYED_BELTS.put(color, get("block/belt", "block/belt/" + id + "_scroll"));
            DYED_OFFSET_BELTS.put(color, get("block/belt_offset", "block/belt/" + id + "_scroll"));
            DYED_DIAGONAL_BELTS.put(color, get("block/belt_diagonal", "block/belt/" + id + "_diagonal_scroll"));

            COLORED_STORAGE_TOP.put(color, storage("top", color));
            COLORED_STORAGE_FRONT.put(color, storage("front", color));
            COLORED_STORAGE_SIDE.put(color, storage("side", color));
            COLORED_STORAGE_BOTTOM.put(color, storage("bottom", color));
        }
    }

    public static CTSpriteShiftEntry getColoredStorageTop(DyeColor color, boolean small) {
        return COLORED_STORAGE_TOP.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageFront(DyeColor color, boolean small) {
        return COLORED_STORAGE_FRONT.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageSide(DyeColor color, boolean small) {
        return COLORED_STORAGE_SIDE.get(color).get(small);
    }
    public static CTSpriteShiftEntry getColoredStorageBottom(DyeColor color, boolean small) {
        return COLORED_STORAGE_BOTTOM.get(color).get(small);
    }

    private static Couple<CTSpriteShiftEntry> storage(String name, DyeColor color) {
        final String prefixed = "block/storage_container/" + color.getSerializedName() + "_storage_container_" + name;
        return Couple.createWithContext(medium -> CTSpriteShifter.getCT(AllCTTypes.RECTANGLE, DnDecor.loc(prefixed + "_small"),
                DnDecor.loc(medium ? prefixed + "_medium" : prefixed + "_large")));
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(DnDecor.loc(originalLocation), DnDecor.loc(targetLocation));
    }

    public static void init() {}
}
