package org.slow.warp.commands;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.json.simple.JSONObject;

public class ConfigObject {
    public String name;
    public RegistryKey<World> dimension;
    public int x;
    public int y;
    public int z;

    public ConfigObject(JSONObject temp) {
        this.name = temp.get("name").toString();

        this.dimension = switch (temp.get("dimension").toString().toLowerCase()) {
            case "overworld" -> World.OVERWORLD;
            case "nether" -> World.NETHER;
            case "end" -> World.END;
            default -> World.OVERWORLD;
        };

        this.x = Integer.parseInt(temp.get("x").toString());
        this.y = Integer.parseInt(temp.get("y").toString());
        this.z = Integer.parseInt(temp.get("z").toString());
    }
}
