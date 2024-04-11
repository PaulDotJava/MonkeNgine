package org.monkeg.rendering.shaders;

import java.io.*;
import java.util.Objects;

import org.monkeg.api.util.logging.Log;

class ShaderBundle {

    private final String vertexShader;
    private final String fragmentShader;

    public ShaderBundle(String path) throws IOException {
        //BufferedReader reader = new BufferedReader(new FileReader(path));
        InputStream is = getClass().getResourceAsStream(path);

        if(is == null) {
            Log.fatal("Cant access resource: {}", path);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        Log.trace("Reading shader code from \"{}\"...", path);

        String line = reader.readLine();

        while(true) {
            if(line == null) {
                throw new IllegalStateException("Unable to find vertex Shader");
            }

            if (line.equals("//-vertex//")) {
                break;
            }
            line = reader.readLine();
        }

        line = reader.readLine();

        StringBuilder vsb = new StringBuilder();
        StringBuilder fsb = new StringBuilder();

        while (true) {
            if (line.equals("//-fragment//")) {
                line = reader.readLine();
                break;
            } else if (line == null) {
                throw new IllegalStateException("Unable to find fragment shader!");
            }
            vsb.append(line).append("\n");
            line = reader.readLine();
        }

        while (line != null) {
            fsb.append(line).append("\n");
            line = reader.readLine();
        }

        vertexShader = vsb.toString();
        fragmentShader = fsb.toString();

        //Log.trace("Read vertex shader:\n{}", vertexShader);
        //Log.trace("Read fragment shader:\n{}", fragmentShader);
    }

    public String getVertexShader() {
        return vertexShader;
    }

    public String getFragmentShader() {
        return fragmentShader;
    }
}
