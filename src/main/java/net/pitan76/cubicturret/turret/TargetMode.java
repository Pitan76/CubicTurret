package net.pitan76.cubicturret.turret;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.pitan76.mcpitanlib.api.util.TextUtil;

import java.util.function.Function;

public class TargetMode {
    public static final TargetMode ANIMAL = new TargetMode("animal", (entity -> entity instanceof AnimalEntity));
    public static final TargetMode MONSTER = new TargetMode("monster", (entity -> entity.getType().getSpawnGroup() == SpawnGroup.MONSTER));
    public static final TargetMode PLAYER = new TargetMode("player", (entity -> entity instanceof PlayerEntity));
    public static final TargetMode NON_PLAYER = new TargetMode("non_player", (entity -> !(entity instanceof PlayerEntity)));
    public static final TargetMode PHANTOM_ONLY = new TargetMode("phantom_only", (entity -> entity instanceof PhantomEntity));
    public static final TargetMode ALL = new TargetMode("all", (entity -> true));
    public static final TargetMode NONE = new TargetMode("none", (entity -> false));

    public String name;
    public Function<Entity, Boolean> function;

    public TargetMode(String name, Function<Entity, Boolean> function) {
        this.name = name;
        this.function = function;
    }

    public Text getTranslation() {
        return TextUtil.translatable("targetmode.cubicturret." + name);
    }

    public boolean isTarget(Entity entity) {
        return function.apply(entity);
    }

    public TargetMode next() {
        if (this.equals(ANIMAL)) return MONSTER;
        if (this.equals(MONSTER)) return PLAYER;
        if (this.equals(PLAYER)) return NON_PLAYER;
        if (this.equals(NON_PLAYER)) return PHANTOM_ONLY;
        if (this.equals(PHANTOM_ONLY)) return ALL;
        if (this.equals(ALL)) return NONE;
        if (this.equals(NONE)) return ANIMAL;

        return MONSTER;
    }

    public static TargetMode getByName(String name) {
        switch (name) {
            case "animal":
                return ANIMAL;
            case "monster":
                return MONSTER;
            case "player":
                return PLAYER;
            case "non_player":
                return NON_PLAYER;
            case "phantom_only":
                return PHANTOM_ONLY;
            case "all":
                return ALL;
            case "none":
                return NONE;
            default:
                return null;
        }
    }
}
