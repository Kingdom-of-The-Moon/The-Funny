package org.moon.the_funny.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonMovingBlockEntity.class)
public abstract class TheFunny3 {

    @Shadow private float progressO;
    @Shadow private float progress;
    @Shadow private long lastTicked;

    @Shadow
    private static void moveCollidedEntities(Level world, BlockPos pos, float f, PistonMovingBlockEntity blockEntity) {}
    @Shadow
    private static void moveStuckEntities(Level world, BlockPos pos, float f, PistonMovingBlockEntity blockEntity) {}

    @Inject(at = @At("HEAD"), method = "getProgress", cancellable = true)
    private void getProgress(float tickDelta, CallbackInfoReturnable<Float> cir) {
        cir.cancel();
        cir.setReturnValue(Mth.lerp(tickDelta, this.progressO, this.progress));
    }

    @Inject(at = @At("HEAD"), method = "finalTick", cancellable = true)
    private void finalTick(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private static void tick(Level world, BlockPos pos, BlockState state, PistonMovingBlockEntity blockEntity, CallbackInfo ci) {
        ci.cancel();

        TheFunny3 the = (TheFunny3) (Object) blockEntity;

        the.lastTicked = world.getGameTime();
        the.progressO = the.progress;

        float f = the.progress + 0.5f;
        moveCollidedEntities(world, pos, f, blockEntity);
        moveStuckEntities(world, pos, f, blockEntity);
        the.progress = f;
    }
}
