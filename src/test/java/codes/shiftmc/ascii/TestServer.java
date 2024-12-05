package codes.shiftmc.ascii;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestServer {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Wall
        var start = new Pos(-22, 43, -11);
        var end = new Pos(21, 71, -11);

        for (double x = Math.min(start.x(), end.x()); x <= Math.max(start.x(), end.x()); x++) {
            for (double y = Math.min(start.y(), end.y()); y <= Math.max(start.y(), end.y()); y++) {
                for (double z = Math.min(start.z(), end.z()); z <= Math.max(start.z(), end.z()); z++) {
                    instanceContainer.setBlock((int) x, (int) y, (int) z, Block.BLACK_CONCRETE);
                }
            }
        }

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
            player.setGameMode(GameMode.CREATIVE);

            try {
                new NoTickingEntity(instanceContainer, new Pos(0, 44, -10));
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        minecraftServer.start("0.0.0.0", 25565);
    }
}