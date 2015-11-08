/*
 * This file is part of SpongeSurvivalGamesPlugin, licensed under the MIT License (MIT).
 *
 * Copyright (c) Matthew Broomfield <m0pt0pmatt17@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.m0pt0pmatt.spongesurvivalgames.commands.game;

import io.github.m0pt0pmatt.spongesurvivalgames.BukkitSurvivalGamesPlugin;
import io.github.m0pt0pmatt.spongesurvivalgames.commands.CommandArgs;
import io.github.m0pt0pmatt.spongesurvivalgames.config.SurvivalGameConfig;
import io.github.m0pt0pmatt.spongesurvivalgames.config.SurvivalGameConfigSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Command to load a configuration for a game
 */
public class LoadCommand extends GameCommand {

    @Override
    public boolean execute(CommandSender sender, Map<String, String> arguments) {

        if (!super.execute(sender, arguments)) {
            return false;
        }

        if (!arguments.containsKey(CommandArgs.FILENAME)) {
            sender.sendMessage("No file name given.");
            return false;
        }
        String fileName = arguments.get(CommandArgs.FILENAME);

        File file = new File(BukkitSurvivalGamesPlugin.plugin.getDataFolder(), fileName);

        SurvivalGameConfigSerializer serializer = new SurvivalGameConfigSerializer();
        YamlConfiguration yaml = new YamlConfiguration();

        try {
            yaml.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            sender.sendMessage("Unable to load config file");
            return false;
        }

        boolean overwrite = true;
        
        if (arguments.containsKey(CommandArgs.OVERWRITE) && arguments.get(CommandArgs.OVERWRITE).equalsIgnoreCase("false")) {
        	overwrite = false;
        }
        
        SurvivalGameConfig config;

        config = serializer.deserialize(yaml, overwrite);

        BukkitSurvivalGamesPlugin.survivalGameMap.get(id).setConfig(config);
        sender.sendMessage("Config file loaded");
        return true;
    }
}
