package net.makozort.advancedages.networking.packet;

import net.makozort.advancedages.content.vfx.BombEffectEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HellbombPacket {


    private BlockPos pos;

    private float scalar;

    private boolean iSpawnSmoke;

    private boolean iFlash;

    public HellbombPacket(BlockPos pos,float scalar,boolean iSpawnSmoke, boolean iFlash) {
        this.pos = pos;
        this.scalar = scalar;
        this.iSpawnSmoke = iSpawnSmoke;
        this.iFlash = iFlash;
    }

    public HellbombPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.scalar = buf.readFloat();
        this.iSpawnSmoke = buf.readBoolean();
        this.iFlash = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeFloat(this.scalar);
        buf.writeBoolean(this.iSpawnSmoke);
        buf.writeBoolean(this.iFlash);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BombEffectEngine.spawn(Minecraft.getInstance().player.level(),this.pos,this.scalar,this.iSpawnSmoke,this.iFlash);
        });
    return true;
    }
}
