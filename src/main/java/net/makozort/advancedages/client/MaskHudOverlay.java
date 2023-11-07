package net.makozort.advancedages.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.reg.Allitems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MaskHudOverlay {
    protected static final ResourceLocation MASK_HUD = new ResourceLocation(AdvancedAges.MOD_ID,
            "textures/misc/mask_overlay.png");

    protected static void renderTextureOverlay(ResourceLocation p_168709_, float p_168710_, int x, int y) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, p_168710_);
        RenderSystem.setShaderTexture(0, p_168709_);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, y, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(x, y, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(x, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static final IGuiOverlay HUD_MASK = ((gui, poseStack, partialTick, width, height) -> {
        int x = width;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MASK_HUD);


        for (int i = 0; i < 10; i++) {
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                ItemStack itemstack = Minecraft.getInstance().player.getInventory().getArmor(3);
                if (itemstack.is(Allitems.POLLUTION_MASK.get())) {
                    renderTextureOverlay(MASK_HUD, 1.0F, x, y);
                }
            }
        }

    });
}
