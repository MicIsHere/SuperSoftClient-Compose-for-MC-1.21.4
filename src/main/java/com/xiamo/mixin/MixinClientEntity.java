package com.xiamo.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.xiamo.utils.rotation.RotationManager;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientEntity {


    @Inject(method = "tick", at = @At("HEAD"))
    private void onTickHead(CallbackInfo ci) {
        RotationManager.INSTANCE.tick();
    }


    @ModifyExpressionValue(
        method = {"sendMovementPackets", "tick"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F")
    )
    private float hookSilentRotationYaw(float original) {
        Float serverYaw = RotationManager.INSTANCE.getServerYawNeeded();
        return serverYaw != null ? serverYaw : original;
    }


    @ModifyExpressionValue(
        method = {"sendMovementPackets", "tick"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F")
    )
    private float hookSilentRotationPitch(float original) {
        Float serverPitch = RotationManager.INSTANCE.getServerPitchNeeded();
        return serverPitch != null ? serverPitch : original;
    }
}
