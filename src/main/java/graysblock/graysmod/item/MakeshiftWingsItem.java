package graysblock.graysmod.item;

import graysblock.graysmod.data.server.advancement.CustomAdvancementGranter;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;

public class MakeshiftWingsItem extends ElytraItem implements FabricElytraItem {

    public MakeshiftWingsItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.HONEYCOMB);
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        if(ElytraItem.isUsable(chestStack)) {
            if (tickElytra) {
                doVanillaElytraTick(entity, chestStack);

                if (entity instanceof ServerPlayerEntity serverPlayer) {
                    CustomAdvancementGranter.grantIcarusAdvancement(serverPlayer);
                }
            }
            return true;
        }
        return false;
    }
}
