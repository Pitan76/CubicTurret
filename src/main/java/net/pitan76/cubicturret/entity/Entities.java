package net.pitan76.cubicturret.entity;

import net.pitan76.mcpitanlib.api.entity.EntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.cubicturret.CubicTurret._id;
import static net.pitan76.cubicturret.CubicTurret.registry;

public class Entities {
    public static RegistryResult<EntityType<?>> BULLET_ENTITY;

    public static void init() {
        BULLET_ENTITY = registry.registerEntity(_id("bullet"), () -> EntityTypeBuilder.create(SpawnGroup.MISC, BulletEntity::new).build());
    }
}