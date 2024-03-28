package org.slow.warp.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slow.warp.Warp;


public class Commander {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> warpNode = CommandManager
                    .literal("warp")
                    .build();


            dispatcher.getRoot().addChild(warpNode);

            JSONArray warps = (JSONArray) Warp.getConfig().get("warps");

            for (Object temp : warps) {
                ConfigObject warp = new ConfigObject((JSONObject) temp);

                warpNode.addChild(CommandManager
                        .literal(warp.name)
                        .executes((context) -> SubCommands.tp(context, warp.dimension, warp.x, warp.y, warp.z))
                        .build());
            }
        });
    }

    public static class SubCommands {
        public static int tp(CommandContext<ServerCommandSource> context, RegistryKey<World> dimension, int x, int y, int z) {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player != null) {
                player.teleport(player.getServer().getWorld(dimension), x, y, z, 0, 0);
            }
            return 0;
        }
    }
}