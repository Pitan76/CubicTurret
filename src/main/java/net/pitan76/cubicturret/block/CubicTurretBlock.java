package net.pitan76.cubicturret.block;

import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;

public class CubicTurretBlock extends AbstractCubicTurretBlock {

    public CubicTurretBlock(CompatibleBlockSettings settings) {
        this(settings, 1);
    }

    public CubicTurretBlock(CompatibleBlockSettings settings, int level) {
        super(settings);
    }
}
