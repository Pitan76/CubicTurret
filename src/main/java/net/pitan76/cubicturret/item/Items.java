package net.pitan76.cubicturret.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.ItemUtil;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Items {

    public static RegistryResult<Item> TURRET_BASE_ITEM;

    public static RegistryResult<Item> CUBIC_TURRET_ITEM;
    public static RegistryResult<Item> DOUBLE_CUBIC_TURRET_ITEM;

    public static void init() {
        TURRET_BASE_ITEM = registry.registerItem(id("turret_base"), () -> create(Blocks.TURRET_BASE.getOrNull()));

        CUBIC_TURRET_ITEM = registry.registerItem(id("cubic_turret"), () -> create(Blocks.CUBIC_TURRET_BLOCK.getOrNull()));
        DOUBLE_CUBIC_TURRET_ITEM = registry.registerItem(id("double_cubic_turret"), () -> create(Blocks.DOUBLE_CUBIC_TURRET_BLOCK.getOrNull()));
    }

    public static Item create(Block block, CompatibleItemSettings settings) {
        return ItemUtil.ofBlock(block, settings);
    }

    public static Item create(Block block) {
        return create(block, CompatibleItemSettings
                .of()
                .addGroup(DefaultItemGroups.FUNCTIONAL)
        );
    }
}
