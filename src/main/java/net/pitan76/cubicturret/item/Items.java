package net.pitan76.cubicturret.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v2.ItemSettingsBuilder;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.item.ItemUtil;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Items {

    public static final ItemSettingsBuilder TURRET_SETTINGS = new ItemSettingsBuilder()
            .addGroup(ItemGroups.CUBIC_TURRET);

    public static RegistryResult<Item> TURRET_BASE_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_LV2_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_LV2_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_LV3_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_LV3_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_LV4_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_LV4_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_LV5_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_LV5_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_INF_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_INF_ITEM;

    public static RegistryResult<Item> TURRET_BULLET_ITEM;

    public static void init() {
        TURRET_BASE_ITEM = registry.registerItem(_id("turret_base"), () -> create(Blocks.TURRET_BASE.getOrNull(), TURRET_SETTINGS.build(_id("turret_base"))));

        CUBIC_TURRET_ITEM = registry.registerItem(_id("cubic_turret"), () -> create(Blocks.CUBIC_TURRET_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret"))));
        DOUBLE_CUBIC_TURRET_ITEM = registry.registerItem(_id("double_cubic_turret"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret"))));

        CUBIC_TURRET_LV2_ITEM = registry.registerItem(_id("cubic_turret_lv2"), () -> create(Blocks.CUBIC_TURRET_LV2_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret_lv2"))));
        DOUBLE_CUBIC_TURRET_LV2_ITEM = registry.registerItem(_id("double_cubic_turret_lv2"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_LV2_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret_lv2"))));

        CUBIC_TURRET_LV3_ITEM = registry.registerItem(_id("cubic_turret_lv3"), () -> create(Blocks.CUBIC_TURRET_LV3_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret_lv3"))));
        DOUBLE_CUBIC_TURRET_LV3_ITEM = registry.registerItem(_id("double_cubic_turret_lv3"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_LV3_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret_lv3"))));

        CUBIC_TURRET_LV4_ITEM = registry.registerItem(_id("cubic_turret_lv4"), () -> create(Blocks.CUBIC_TURRET_LV4_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret_lv4"))));
        DOUBLE_CUBIC_TURRET_LV4_ITEM = registry.registerItem(_id("double_cubic_turret_lv4"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_LV4_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret_lv4"))));

        CUBIC_TURRET_LV5_ITEM = registry.registerItem(_id("cubic_turret_lv5"), () -> create(Blocks.CUBIC_TURRET_LV5_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret_lv5"))));
        DOUBLE_CUBIC_TURRET_LV5_ITEM = registry.registerItem(_id("double_cubic_turret_lv5"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_LV5_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret_lv5"))));

        CUBIC_TURRET_INF_ITEM = registry.registerItem(_id("cubic_turret_infinity"), () -> create(Blocks.CUBIC_TURRET_INF_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("cubic_turret_infinity"))));
        DOUBLE_CUBIC_TURRET_INF_ITEM = registry.registerItem(_id("double_cubic_turret_infinity"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_INF_BLOCK.getOrNull(), TURRET_SETTINGS.build(_id("double_cubic_turret_infinity"))));

        TURRET_BULLET_ITEM = registry.registerItem(_id("turret_bullet"), () -> ItemUtil.create(CompatibleItemSettings.of(_id("turret_bullet")).addGroup(ItemGroups.CUBIC_TURRET)));
    }

    public static Item create(Block block, CompatibleItemSettings settings) {
        return ItemUtil.create(block, settings);
    }
}
