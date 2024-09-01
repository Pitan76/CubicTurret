package net.pitan76.cubicturret.item;

import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class ItemGroups {
    public static CreativeTabBuilder CUBIC_TURRET = CreativeTabBuilder.create(_id("turrets"))
            .setIcon(() -> ItemStackUtil.getDefaultStack(Items.CUBIC_TURRET_ITEM.getOrNull()));

    public static void init() {
        registry.registerItemGroup(CUBIC_TURRET);
    }
}
