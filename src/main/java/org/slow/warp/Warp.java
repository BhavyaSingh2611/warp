package org.slow.warp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slow.warp.commands.Commander;

public class Warp implements ModInitializer {
    static JSONObject config;
    @Override
    public void onInitialize() {
        final String configFilePath = FabricLoader.getInstance().getConfigDir().toString() + "/warp.json";
        final Path configPath = Paths.get(configFilePath);
        try {
            File configFile = new File(configFilePath);
            String defaultConfig = """
                    {
                      "warps": [
                        {
                          "name": "spawn",
                          "dimension": "overworld",
                          "x": 0,
                          "y": 0,
                          "z": 0
                        },
                        {
                          "name": "spawn",
                          "dimension": "overworld",
                          "x": 0,
                          "y": 0,
                          "z": 0
                        }
                      ]
                    }""";
            if (configFile.createNewFile()){
                Files.write(configPath, defaultConfig.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            config = (JSONObject) JSONValue.parse(new String(Files.readAllBytes(configPath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Commander.register();
    }

    public static JSONObject getConfig() {
        return config;
    }
}
