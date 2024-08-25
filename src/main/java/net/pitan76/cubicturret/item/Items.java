package net.pitan76.cubicturret.item;

import net.minecraft.item.Item;
import net.pitan76.cubicturret.block.Blocks;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.ItemUtil;

import static net.pitan76.cubicturret.CubicTurret.id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Items {

    public static RegistryResult<Item> TURRET_ITEM;

    public static void init() {
        TURRET_ITEM = registry.registerItem(id("cubic_turret"), () -> ItemUtil.ofBlock(Blocks.TURRET_BLOCK.getOrNull(), CompatibleItemSettings
                .of()
                .addGroup(DefaultItemGroups.FUNCTIONAL)
        ));
    }
}
