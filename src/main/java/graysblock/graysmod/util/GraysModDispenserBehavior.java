package graysblock.graysmod.util;

import graysblock.graysmod.entity.projectile.WindBoltEntity;
import graysblock.graysmod.item.GraysModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class GraysModDispenserBehavior {

    public static void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(GraysModItems.WIND_BOLT, new ProjectileDispenserBehavior(GraysModItems.WIND_BOLT) {
            private ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return new WindBoltEntity(world, position.getX(), position.getY(), position.getZ(), stack.copyWithCount(1), null);
            }
        });
    }
}
