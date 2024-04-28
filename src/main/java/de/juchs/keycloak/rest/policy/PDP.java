package de.juchs.keycloak.rest.policy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PDP {
    private static final String SCRIPT_FOLDER_PATH = "/opt/SAPL/scripts";
    private static final String SCRIPT_EXTENSION = ".sapl";

    private Map<String, String> scripts;

    public PDP(){
        try {
            scripts = loadScripts();
        }
        catch (IOException io){
            System.err.println("Error loading script from folder: " + io.getMessage());
        }
    }

    /*
        For now just some dumb logic. Return true if the String contains more than
        zero characters.
     */
    public boolean sendToPDP(String data){
        return (!data.isEmpty());
    }

    /*
        Loads the SAPL scripts from the filesystem to make a decision
     */
    private Map<String, String> loadScripts() throws IOException {
        Map<String, String> scripts = new HashMap<>();

        File scriptFolder = new File(SCRIPT_FOLDER_PATH);
        if (!scriptFolder.exists() || !scriptFolder.isDirectory()) {
            throw new IllegalArgumentException("Script folder does not exist or is not a directory");
        }

        File[] scriptFiles = scriptFolder.listFiles((dir, name) -> name.endsWith(SCRIPT_EXTENSION));
        if (scriptFiles != null) {
            for (File scriptFile : scriptFiles) {
                String scriptName = scriptFile.getName();
                String scriptContent = new String(Files.readAllBytes(scriptFile.toPath()));
                scripts.put(scriptName, scriptContent);
            }
        }
        return scripts;
    }

    public Map<String, String> getLoadedScripts(){
        return  scripts;
    }
}
