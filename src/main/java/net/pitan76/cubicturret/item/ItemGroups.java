package net.pitan76.cubicturret.item;

import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class ItemGroups {
    public static CreativeTabBuilder CUBIC_TURRET = CreativeTabBuilder.create(_id("turrets"))
            .setIcon(() -> Items.CUBIC_TURRET_ITEM.createStack().toMinecraft());

    public static void init() {
        registry.getCompatRegistry().registerItemGroup(CUBIC_TURRET);
    }
}
