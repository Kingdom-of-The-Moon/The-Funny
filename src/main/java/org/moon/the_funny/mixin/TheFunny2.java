package org.moon.the_funny.mixin;

import net.minecraft.world.level.block.entity.ChestLidController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestLidController.class)
public class TheFunny2 {

    @Shadow private float oOpenness;
    @Shadow private float openness;
    @Shadow private boolean shouldBeOpen;

    @Inject(at = @At("HEAD"), method = "tickLid", cancellable = true)
    private void tickLid(CallbackInfo ci) {
        ci.cancel();

        this.oOpenness = this.openness;
        if (!this.shouldBeOpen && this.openness > 0f)
            this.openness = Math.max(this.openness - 0.1f, 0f);
        else if (this.shouldBeOpen && this.openness < 20f)
            this.openness = Math.min(this.openness + 0.1f, 20f);
    }
}
