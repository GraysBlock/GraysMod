package graysblock.graysmod.mixin;

import graysblock.graysmod.data.server.advancement.CustomAdvancementGranter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
public class GrindstoneScreenHandlerOutputMixin {

    @Inject(method = "onTakeItem", at = @At("RETURN"))
    private void includeGoldenApple(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(stack.isOf(Items.GOLDEN_APPLE) && player instanceof ServerPlayerEntity serverPlayerEntity) {
            CustomAdvancementGranter.grantPureAbsurdityAdvancement(serverPlayerEntity);
        }
    }
}
