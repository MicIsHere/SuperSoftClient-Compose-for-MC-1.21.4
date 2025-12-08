package com.xiamo.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.xiamo.utils.rotation.RotationManager;
import com.xiamo.utils.rotation.Rotation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    protected static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        throw new AssertionError();
    }


    @ModifyExpressionValue(
        method = "updateVelocity",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"
        )
    )
    private Vec3d hookVelocity(
        Vec3d original,
        @Local(argsOnly = true) Vec3d movementInput,
        @Local(argsOnly = true) float speed,
        @Local(argsOnly = true) float yaw
    ) {

        if ((Object) this != MinecraftClient.getInstance().player) {
            return original;
        }


        if (!RotationManager.INSTANCE.isActive()) {
            return original;
        }

        if (RotationManager.INSTANCE.getMoveFixMode() == RotationManager.MoveFixMode.OFF) {
            return original;
        }

        Rotation targetRotation = RotationManager.INSTANCE.getTargetRotation();
        if (targetRotation == null) {
            return original;
        }


        float serverYaw = RotationManager.INSTANCE.getServerYaw();
        Vec3d fixedVelocity = movementInputToVelocity(movementInput, speed, serverYaw);

        return fixedVelocity;
    }
}
