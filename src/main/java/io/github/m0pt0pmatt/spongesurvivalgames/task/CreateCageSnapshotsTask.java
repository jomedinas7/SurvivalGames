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
package io.github.m0pt0pmatt.spongesurvivalgames.task;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableSet;
import io.github.m0pt0pmatt.spongesurvivalgames.SpongeSurvivalGamesPlugin;
import io.github.m0pt0pmatt.spongesurvivalgames.game.SurvivalGame;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.util.TextMessageException;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CreateCageSnapshotsTask implements Task {

    private static final Task INSTANCE = new CreateCageSnapshotsTask();

    private static final Set<Vector3d> SURROUNDING_BLOCKS = ImmutableSet.<Vector3d>builder()
            .add(new Vector3d(1, 0, 0))
            .add(new Vector3d(1, 1, 0))
            .add(new Vector3d(-1, 0, 0))
            .add(new Vector3d(-1, 1, 0))
            .add(new Vector3d(0, 0, 1))
            .add(new Vector3d(0, 1, 1))
            .add(new Vector3d(0, 0, -1))
            .add(new Vector3d(0, 1, -1))
            .add(new Vector3d(0, 2, 0))
            .build();

    @Override
    public void execute(SurvivalGame survivalGame) throws TextMessageException {

        setBlocks(survivalGame, BlockTypes.BARRIER);

        int countdownSeconds = survivalGame.getConfig().getCountdownSeconds().orElse(10);

        SpongeSurvivalGamesPlugin.EXECUTOR.schedule(() ->
                setBlocks(survivalGame, BlockTypes.AIR), countdownSeconds, TimeUnit.SECONDS);
    }

    private void setBlocks(SurvivalGame survivalGame, BlockType blockType) {
        survivalGame.getConfig().getWorldName().ifPresent(worldName ->
                Sponge.getServer().getWorld(worldName).ifPresent(world ->
                        survivalGame.getConfig().getSpawnPoints().forEach(spawnPoint ->
                                SURROUNDING_BLOCKS.forEach(vector3i ->
                                        world.getLocation(spawnPoint.add(vector3i))
                                                .setBlockType(blockType,

                                                        Cause.of(NamedCause.of("CreateCageSnapshotsTask", SpongeSurvivalGamesPlugin.PLUGIN_CONTAINER))
                                                )))));
    }

    public static Task getInstance() {
        return INSTANCE;
    }
}