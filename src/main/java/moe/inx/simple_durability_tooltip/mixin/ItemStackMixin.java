package moe.inx.simple_durability_tooltip.mixin;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.item.TooltipConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	abstract boolean isDamaged();

	@Shadow
	abstract int getMaxDamage();

	@Shadow
	abstract int getDamage();

	@Inject(
		method = "getTooltip",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/TooltipConfig;shouldShowAdvancedDetails()Z", ordinal = 1, shift = At.Shift.BEFORE)
	)
	public void simple_durability_tooltip$getTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipConfig config,
			CallbackInfoReturnable<List<Text>> ci, @Local List<Text> list) {
		if (!config.shouldShowAdvancedDetails()) {
			if (this.isDamaged()) {
				list.add(Text.translatable("item.durability",this.getMaxDamage() - this.getDamage(),this.getMaxDamage()));
			}
		}
	}
}
