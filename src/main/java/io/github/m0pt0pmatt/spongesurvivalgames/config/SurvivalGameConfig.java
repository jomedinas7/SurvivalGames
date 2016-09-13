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

package io.github.m0pt0pmatt.spongesurvivalgames.config;

import com.google.common.reflect.TypeToken;

import com.flowpowered.math.vector.Vector3i;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Config for a Survival Game.
 * <p>All configurable fields are stored in this class. Getters return Optionals.</p>
 */
@ConfigSerializable
public class SurvivalGameConfig {

    private static final SurvivalGameConfig DEFAULT_CONFIG = createDefaultConfig();

    private static SurvivalGameConfig createDefaultConfig() {
        SurvivalGameConfig survivalGameConfig = new SurvivalGameConfig();
        survivalGameConfig.setPlayerLimit(4);
        survivalGameConfig.setCountdownSeconds(10);
        return survivalGameConfig;
    }

    static {
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(SurvivalGameConfig.class),
                new SurvivalGameConfigSerializer());
    }

    @Setting(value = "spawn-points", comment = "Where players spawn when the game starts.")
    private final Set<Vector3i> spawnPoints = new HashSet<>();

    @Setting(value = "world-name", comment = "The name of the world where the survival game will take place.")
    private String worldName;

    @Setting(value = "lesser-boundary", comment = "Map boundary")
    private Vector3i lesserBoundary;

    @Setting(value = "greater-boundary", comment = "Map boundary")
    private Vector3i greaterBoundary;

    @Setting(value = "exit-world-name", comment = "The name of the world where players teleport to once they leave the game.")
    private String exitWorldName;

    @Setting(value = "exit-vector", comment = "The location where players teleport to once they leave the game.")
    private Vector3i exitVector;

    @Setting(value = "center-vector", comment = "The center of the survival game map.")
    private Vector3i centerVector;

    @Setting(value = "player-limit", comment = "The max number of players which can join the game.")
    private Integer playerLimit;

    @Setting(value = "countdown-seconds", comment = "The number of seconds to countdown once the survival game starts.")
    private Integer countdownSeconds;

    public Optional<String> getWorldName() {
        return Optional.ofNullable(worldName);
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public Optional<Vector3i> getLesserBoundary() {
        return Optional.ofNullable(lesserBoundary);
    }

    public Optional<Vector3i> getGreaterBoundary() {
        if (greaterBoundary != null) {
            return Optional.of(greaterBoundary);
        }

        return getLesserBoundary();
    }

    public void addBoundaryVector(Vector3i vector3i) {

        if (vector3i == null) {
            return;
        }

        if (lesserBoundary == null) {
            lesserBoundary = vector3i;
        } else {
            if (greaterBoundary != null) {

                int x1 = lesserBoundary.getX();
                int x2 = greaterBoundary.getX();
                int x3 = vector3i.getX();

                int y1 = lesserBoundary.getY();
                int y2 = greaterBoundary.getY();
                int y3 = vector3i.getY();

                int z1 = lesserBoundary.getZ();
                int z2 = greaterBoundary.getZ();
                int z3 = vector3i.getZ();

                lesserBoundary = new Vector3i(
                        min(min(x1, x2), x3),
                        min(min(y1, y2), y3),
                        min(min(z1, z2), z3));

                greaterBoundary = new Vector3i(
                        max(max(x1, x2), x3),
                        max(max(y1, y2), y3),
                        max(max(z1, z2), z3));
            } else {
                int x1 = lesserBoundary.getX();
                int x2 = vector3i.getX();

                int y1 = lesserBoundary.getY();
                int y2 = vector3i.getY();

                int z1 = lesserBoundary.getZ();
                int z2 = vector3i.getZ();

                lesserBoundary = new Vector3i(
                        min(x1, x2),
                        min(y1, y2),
                        min(z1, z2));

                greaterBoundary = new Vector3i(
                        max(x1, x2),
                        max(y1, y2),
                        max(z1, z2));
            }
        }
    }

    public void clearBoundaryVectors() {
        lesserBoundary = null;
        greaterBoundary = null;
    }

    public Optional<String> getExitWorldName() {
        return Optional.ofNullable(exitWorldName);
    }

    public void setExitWorldName(String exitWorldName) {
        this.exitWorldName = exitWorldName;
    }

    public Optional<Vector3i> getExitVector() {
        return Optional.ofNullable(exitVector);
    }

    public void setExitVector(Vector3i exitVector) {
        this.exitVector = exitVector;
    }

    public Optional<Vector3i> getCenterVector() {
        return Optional.ofNullable(centerVector);
    }

    public void setCenterVector(Vector3i centerVector) {
        this.centerVector = centerVector;
    }

    public Optional<Integer> getPlayerLimit() {
        return Optional.ofNullable(playerLimit);
    }

    public void setPlayerLimit(Integer playerLimit) {
        this.playerLimit = playerLimit;
    }

    public Optional<Integer> getCountdownSeconds() {
        return Optional.ofNullable(countdownSeconds);
    }

    public void setCountdownSeconds(Integer countdownSeconds) {
        this.countdownSeconds = countdownSeconds;
    }

    public Set<Vector3i> getSpawnPoints() {
        return spawnPoints;
    }
}
