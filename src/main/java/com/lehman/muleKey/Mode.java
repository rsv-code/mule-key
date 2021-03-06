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

import java.util.HashMap;
import java.util.Map;

/**
 * Enum Mode defines the available encryption modes.
 */
public enum Mode {
    CBC("CBC"),
    CFB("CFB"),
    ECB("ECB"),
    OFB("OFB");

    private String value;

    Mode(String Value) {
        this.value = Value;
    }

    String getValue() {
        return this.value;
    }

    private static final Map<String, Mode> lookup = new HashMap<>();
    static {
        for(Mode mode : Mode.values()) {
            lookup.put(mode.getValue(), mode);
        }
    }

    public static Mode get(String mode) {
        return lookup.get(mode);
    }
}
