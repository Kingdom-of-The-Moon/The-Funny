package org.moon.the_funny.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class TheFunny {

    @Shadow private float progressOld;
    @Shadow private float progress;
    @Shadow private ShulkerBoxBlockEntity.AnimationStatus animationStatus;

    @Shadow
    private static void doNeighborUpdates(Level world, BlockPos pos, BlockState state) {}

    @Shadow protected abstract void moveCollidedEntities(Level world, BlockPos pos, BlockState state);

    @Inject(at = @At("HEAD"), method = "updateAnimation", cancellable = true)
    private void updateAnimation(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
        ci.cancel();

        this.progressOld = this.progress;
        switch (this.animationStatus) {
            case CLOSED:
                this.progress = 0.0F;
                break;
            case OPENING:
                this.progress += 0.1F;
                if (this.progress >= 1.0F) {
                    this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.OPENED;
                    //this.progress = 1.0F;
                    doNeighborUpdates(world, pos, state);
                }

                this.moveCollidedEntities(world, pos, state);
                break;
            case CLOSING:
                this.progress -= 0.1F;
                if (this.progress <= 0.0F) {
                    this.animationStatus = ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    doNeighborUpdates(world, pos, state);
                }
                break;
            case OPENED:
                this.progress += 0.1F;
        }
    }
}
