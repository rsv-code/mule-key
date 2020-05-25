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

import java.util.ArrayList;

/**
 * Data bound class that holds the config file
 * contents.
 */
public class EncryptionConfigFile {
    /**
     * Stores the config records.
     */
    private ArrayList<EncryptionConfigRecord> keys = new ArrayList<EncryptionConfigRecord>();

    public EncryptionConfigFile() { }

    public ArrayList<EncryptionConfigRecord> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<EncryptionConfigRecord> Keys) {
        this.keys = Keys;
    }
}
