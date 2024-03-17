package net.makozort.advancedages.networking;

import net.makozort.advancedages.AdvancedAges;
import net.makozort.advancedages.networking.packet.BombPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {

    private static SimpleChannel INSTANCE;


    private static int packetId = 0;
    private static int id() {
        return packetId;
    }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(AdvancedAges.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();


        INSTANCE = net;

        net.messageBuilder(BombPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BombPacket::new)
                .encoder(BombPacket::toBytes)
                .consumerMainThread(BombPacket::handle)
                .add();
    }

    public static <MSG> void  sentToSever(MSG msg) {
        INSTANCE.sendToServer(msg);
    }


    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with((() -> player)), msg);
    }

}
