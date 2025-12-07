package com.xiamo.utils.render

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.ShaderProgram
import net.minecraft.client.gl.ShaderProgramKey
import net.minecraft.client.gl.ShaderProgramKeys
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.render.entity.LivingEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

object RenderUtils {
    fun drawBox3D(
        matrixStack: MatrixStack,
        box: Box,
        width: Float,
        r: Float = 0.5f,
        g: Float = 1.0f,
        b: Float = 1.0f,
        a: Float = 1.0f,
    ) {

        val matrix4f = matrixStack.peek().positionMatrix

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR)
        val minX = box.minX.toFloat()
        val minY = box.minY.toFloat()
        val minZ = box.minZ.toFloat()
        val maxX = box.maxX.toFloat()
        val maxY = box.maxY.toFloat()
        val maxZ = box.maxZ.toFloat()

        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.disableDepthTest()
        RenderSystem.lineWidth(width)
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR)

        buffer.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a)

        buffer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a)

        buffer.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a)


        buffer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a)

        buffer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a)

        buffer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a)

        buffer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a)


        buffer.vertex(matrix4f, minX, minY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a)

        buffer.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a)

        buffer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a)

        buffer.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a)
        buffer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a)

        val builtBuffer = buffer.end()

        BufferRenderer.drawWithGlobalProgram(builtBuffer)
        RenderSystem.enableDepthTest()
        RenderSystem.disableBlend()
    }




}