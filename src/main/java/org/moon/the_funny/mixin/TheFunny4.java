package org.moon.the_funny.mixin;

import net.minecraft.client.renderer.blockentity.BellRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BellRenderer.class)
public class TheFunny4 {

    @ModifyVariable(at = @At("STORE"), method = "render(Lnet/minecraft/world/level/block/entity/BellBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", ordinal = 4)
    private float l(float value) {
        return value * 200f;
    }
}
