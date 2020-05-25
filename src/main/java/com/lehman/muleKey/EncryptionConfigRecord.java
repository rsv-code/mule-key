/*
 * This file is part of the Mule-Key (https://github.com/rsv-code/mule-key).
 * Copyright (c) 2020 Roseville Code Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.lehman.muleKey;

import javafx.util.StringConverter;

/**
 * Class holds the config for a specific encryption instance.
 */
public class EncryptionConfigRecord {
    /**
     * A descriptive unique name given to the encryption config.
     */
    private String name = "";

    /**
     * The algorithm to use.
     */
    private Algorithm algorithm = Algorithm.AES;

    /**
     * The mode to use.
     */
    private Mode mode = Mode.CBC;

    /**
     * The key to use.
     */
    private String key = "";

    /**
     * Default constructor.
     */
    public EncryptionConfigRecord() { }

    public EncryptionConfigRecord(String Name) {
        this.name = Name;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
