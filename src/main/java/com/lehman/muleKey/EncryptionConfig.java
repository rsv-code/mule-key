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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Singleton class implements encryption configuration functions.
 */
public class EncryptionConfig {
    /**
     * The config file name on disk.
     */
    private static String configFileName = "mule-key-config.json";

    /**
     * This class singleton instance.
     */
    private static EncryptionConfig instance;

    protected EncryptionConfig() { }

    /**
     * Gets the instance of this singleton class.
     * @return The instance of EncryptionConfig.
     */
    public static EncryptionConfig getInstance() {
        if (instance == null) {
            instance = new EncryptionConfig();
        }
        return instance;
    }

    private EncryptionConfigFile configFile = new EncryptionConfigFile();

    /**
     * Loads the config from the file system. The config file is 'config.json' and is
     * expected to be in the application directory.
     */
    public void loadConfig() {
        try {
            String cfgFileName = new File(".").getCanonicalPath() + "/" + configFileName;

            ObjectMapper mapper = new ObjectMapper();
            File file = new File(cfgFileName);
            this.configFile = mapper.readValue(file, EncryptionConfigFile.class);
        } catch (IOException e) {
            // Load isn't critical here, do nothing
        }
    }

    /**
     * Saves the config to the application directory as 'config.json'.
     * @throws IOException
     */
    public void saveConfig() throws IOException {
        String cfgFileName = new File(".").getCanonicalPath() + "/" + configFileName;

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(cfgFileName), this.configFile);
    }

    public EncryptionConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(EncryptionConfigFile configFile) {
        this.configFile = configFile;
    }
}
