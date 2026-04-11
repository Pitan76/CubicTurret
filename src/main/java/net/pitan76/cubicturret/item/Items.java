package net.pitan76.cubicturret.item;

import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.item.v2.ItemSettingsBuilder;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.item.TypedItemWrapper;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Items {

    public static final ItemSettingsBuilder TURRET_SETTINGS = new ItemSettingsBuilder()
            .addGroup(ItemGroups.CUBIC_TURRET);

    public static ItemWrapper TURRET_BASE_ITEM;

    public static ItemWrapper CUBIC_TURRET_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_ITEM;

    public static ItemWrapper CUBIC_TURRET_LV2_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_LV2_ITEM;

    public static ItemWrapper CUBIC_TURRET_LV3_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_LV3_ITEM;

    public static ItemWrapper CUBIC_TURRET_LV4_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_LV4_ITEM;

    public static ItemWrapper CUBIC_TURRET_LV5_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_LV5_ITEM;

    public static ItemWrapper CUBIC_TURRET_INF_ITEM;
    public static ItemWrapper DOUBLE_CUBIC_TURRET_INF_ITEM;

    public static TypedItemWrapper<TurretBullet> TURRET_BULLET_ITEM;

    public static void init() {
        TURRET_BASE_ITEM = registry.registerBlockItem(_id("turret_base"), Blocks.TURRET_BASE, TURRET_SETTINGS);

        CUBIC_TURRET_ITEM = registry.registerBlockItem(_id("cubic_turret"), Blocks.CUBIC_TURRET_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_ITEM = registry.registerBlockItem(_id("double_cubic_turret"), Blocks.DOUBLE_CUBIC_TURRET_BLOCK, TURRET_SETTINGS);

        CUBIC_TURRET_LV2_ITEM = registry.registerBlockItem(_id("cubic_turret_lv2"), Blocks.CUBIC_TURRET_LV2_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_LV2_ITEM = registry.registerBlockItem(_id("double_cubic_turret_lv2"), Blocks.DOUBLE_CUBIC_TURRET_LV2_BLOCK, TURRET_SETTINGS);

        CUBIC_TURRET_LV3_ITEM = registry.registerBlockItem(_id("cubic_turret_lv3"), Blocks.CUBIC_TURRET_LV3_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_LV3_ITEM = registry.registerBlockItem(_id("double_cubic_turret_lv3"), Blocks.DOUBLE_CUBIC_TURRET_LV3_BLOCK, TURRET_SETTINGS);

        CUBIC_TURRET_LV4_ITEM = registry.registerBlockItem(_id("cubic_turret_lv4"), Blocks.CUBIC_TURRET_LV4_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_LV4_ITEM = registry.registerBlockItem(_id("double_cubic_turret_lv4"), Blocks.DOUBLE_CUBIC_TURRET_LV4_BLOCK, TURRET_SETTINGS);

        CUBIC_TURRET_LV5_ITEM = registry.registerBlockItem(_id("cubic_turret_lv5"), Blocks.CUBIC_TURRET_LV5_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_LV5_ITEM = registry.registerBlockItem(_id("double_cubic_turret_lv5"), Blocks.DOUBLE_CUBIC_TURRET_LV5_BLOCK, TURRET_SETTINGS);

        CUBIC_TURRET_INF_ITEM = registry.registerBlockItem(_id("cubic_turret_infinity"), Blocks.CUBIC_TURRET_INF_BLOCK, TURRET_SETTINGS);
        DOUBLE_CUBIC_TURRET_INF_ITEM = registry.registerBlockItem(_id("double_cubic_turret_infinity"), Blocks.DOUBLE_CUBIC_TURRET_INF_BLOCK, TURRET_SETTINGS);

        TURRET_BULLET_ITEM = registry.registerItem(_id("turret_bullet"), () ->
            new TurretBullet(ItemSettingsBuilder.of(_id("turret_bullet"))
                    .addGroup(ItemGroups.CUBIC_TURRET)
                    .build()
            ));
    }
}
