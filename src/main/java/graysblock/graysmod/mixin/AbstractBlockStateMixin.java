package graysblock.graysmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("RETURN"))
    private VoxelShape shadowFormPhaseAbility(VoxelShape original, BlockView blockView, BlockPos blockPos, ShapeContext context) {
        //This prevents accidentally clipping through the world until I create a proper fix for this.
        return context.isHolding(Items.ACACIA_BOAT) ? VoxelShapes.empty() : original;
    }

    @WrapWithCondition(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V"))
    private boolean preventEntityCollisionChecks(Block instance, BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        return entity instanceof LivingEntity livingEntity && !livingEntity.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM);
    }
}
