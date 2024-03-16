package net.makozort.advancedages.networking.packet;

import net.makozort.advancedages.content.vfx.HellBombEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HellbombPacket {


    private BlockPos pos;

    private float scalar;

    public HellbombPacket(BlockPos pos) {
        this.pos = pos;
    }

    public HellbombPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }



    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            HellBombEffect.spawn(Minecraft.getInstance().player.level(),this.pos);
        });
    return true;
    }
}
