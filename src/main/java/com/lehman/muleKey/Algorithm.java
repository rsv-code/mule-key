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

/**
 * Enum Algorithm defines the available encryption algorithms.
 */
public enum Algorithm {
    AES("AES"),
    BLOWFISH("Blowfish"),
    DES("DES"),
    DESEDE("DESede"),
    RC2("RC2");

    private String value;

    Algorithm(String Value) {
        this.value = Value;
    }

    String getValue() {
        return this.value;
    }
}
