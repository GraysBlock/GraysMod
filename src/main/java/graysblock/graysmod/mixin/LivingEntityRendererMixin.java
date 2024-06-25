package graysblock.graysmod.mixin;

import graysblock.graysmod.entity.effect.GraysModStatusEffects;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @ModifyConstant(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", constant = @Constant(intValue = 654311423))
    private int renderColorChangedModel(int constant, LivingEntity livingEntity) {
        if(livingEntity.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM)) {
            return 2101026093;
        }
        return constant;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("STORE"), name = "bl2")
    private boolean makeModelTransparent(boolean original, LivingEntity livingEntity) {
        return original || livingEntity.hasStatusEffect(GraysModStatusEffects.SHADOW_FORM);
    }

}
