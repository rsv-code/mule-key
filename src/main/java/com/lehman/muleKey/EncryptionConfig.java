package com.lehman.muleKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class implements encryption configuration functions.
 */
public class EncryptionConfig {
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

    /**
     * Stores the config records.
     */
    private List<EncryptionConfigRecord> records = new ArrayList<EncryptionConfigRecord>();

    /**
     * Loads the config from the file system. The config file is 'config.json' and is
     * expected to be in the application directory.
     */
    public void loadConfig() {
        try {
            String cfgFileName = new File(".").getCanonicalPath() + "/config.json";

            ObjectMapper mapper = new ObjectMapper();
            File file = new File(cfgFileName);
            this.records = Arrays.asList(mapper.readValue(file, EncryptionConfigRecord[].class));
        } catch (IOException e) {
            // Load isn't critical here
        }
    }

    /**
     * Saves the config to the application directory as 'config.json'.
     * @throws IOException
     */
    public void saveConfig() throws IOException {
        String cfgFileName = new File(".").getCanonicalPath() + "/config.json";

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileOutputStream(cfgFileName), this.records);
    }
}
