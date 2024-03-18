package net.makozort.advancedages.networking.packet;

import net.makozort.advancedages.content.vfx.BombEffectEngine;
import net.makozort.advancedages.content.vfx.SphereRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BombPacket {


    private BlockPos pos;

    private float scalar;

    private boolean iSpawnSmoke;

    private boolean iFlash;

    private boolean iSphere;

    public BombPacket(BlockPos pos, float scalar, boolean iSpawnSmoke, boolean iFlash,boolean iSphere) {
        this.pos = pos;
        this.scalar = scalar;
        this.iSpawnSmoke = iSpawnSmoke;
        this.iFlash = iFlash;
        this.iSphere = iSphere;
    }

    public BombPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.scalar = buf.readFloat();
        this.iSpawnSmoke = buf.readBoolean();
        this.iFlash = buf.readBoolean();
        this.iSphere = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeFloat(this.scalar);
        buf.writeBoolean(this.iSpawnSmoke);
        buf.writeBoolean(this.iFlash);
        buf.writeBoolean(this.iSphere);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BombEffectEngine.spawn(Minecraft.getInstance().player.level(),this.pos,this.scalar,this.iSpawnSmoke,this.iFlash,this.iFlash);
        });
    return true;
    }
}
