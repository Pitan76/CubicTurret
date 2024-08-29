package net.pitan76.cubicturret.block;

import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;

public class CubicTurretBlock extends AbstractCubicTurretBlock {

    public int level;

    public CubicTurretBlock(CompatibleBlockSettings settings) {
        this(settings, 1);
    }

    public CubicTurretBlock(CompatibleBlockSettings settings, int level) {
        super(settings);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isInfinite() {
        return level == -1;
    }
}
