package org.monkeg.rendering.shaders;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import org.monkeg.api.util.logging.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {

    private int programId = 0;
    private final Map<String, Integer> uniformLocationCache;

    public ShaderProgram(String path) {
        Log.trace("Creating shader program...");

        uniformLocationCache = new HashMap<String, Integer>();

        int vsId = 0, fsId = 0;

        ShaderBundle bundle;
        try {
            bundle = new ShaderBundle(path);
        } catch (IOException e) {
            Log.error("Unable to read shader file!");
            if (e instanceof FileNotFoundException) {
                Log.error("--File not found!");
            }
            Log.error("{}", e.getMessage());

            throw new RuntimeException(e);
        }

        programId = GL30.glCreateProgram();
        vsId = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
        fsId = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);

        GL30.glShaderSource(vsId, bundle.getVertexShader());
        GL30.glShaderSource(fsId, bundle.getFragmentShader());

        GL30.glCompileShader(vsId);
        GL30.glCompileShader(fsId);

        int result = GL30.glGetShaderi(vsId, GL30.GL_COMPILE_STATUS);
        if (result == GL30.GL_FALSE) {
            String message = GL30.glGetShaderInfoLog(vsId);

            System.err.println("Failed to compile vertex shader!");
            System.err.println(message);

            GL30.glDeleteShader(vsId);
        }

        result = GL30.glGetShaderi(fsId, GL30.GL_COMPILE_STATUS);
        if (result == GL30.GL_FALSE) {
            String message = GL30.glGetShaderInfoLog(fsId);

            System.err.println("Failed to compile fragment shader!");
            System.err.println(message);

            GL30.glDeleteShader(fsId);
        }

        GL30.glAttachShader(programId, vsId);
        GL30.glAttachShader(programId, fsId);

        GL30.glLinkProgram(programId);
        GL30.glValidateProgram(programId);

        GL30.glDeleteShader(vsId);
        GL30.glDeleteShader(fsId);

        bind();
    }

    public void bind() {
        Log.trace("Binding shader program: {}", programId);
        GL30.glUseProgram(programId);
    }

    public void unbind() {
        Log.trace("Unbinding shader program: {}", programId);
        GL30.glUseProgram(0);
    }

    public void delete() {
        Log.debug("Destroying shader program: {}", programId);
        GL30.glDeleteProgram(programId);
    }

    void setUniform3f(String name, float v1, float v2, float v3) {
        GL30.glUniform3f(getUniformLocation(name), v1, v2, v3);
    }

    public void setUniform3f(String name, Vector3f values) {
        setUniform3f(name, values.x, values.y, values.z);
    }

    public void setUniform1i(String name, int v) {
        GL30.glUniform1i(getUniformLocation(name), v);
    }

    public void setUniformMat3f(String name, Matrix3f mat) {
        float[] buffer = new float[3 * 3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buffer[(i * 3) + j] = mat.get(i, j);
            }
        }

        setUniformMat3f(name, buffer);
    }

    public void setUniformMat3f(String name, float[] mat) {
        GL30.glUniformMatrix3fv(getUniformLocation(name), false, mat);
    }

    public void setUniformMat4f(String name, Matrix4f mat) {
        float[] buffer = new float[4 * 4];

        mat.get(buffer);

        setUniformMat4f(name, buffer);
    }

    public void setUniformMat4f(String name, float[] mat) {
        int location = getUniformLocation(name);
        Log.trace("Uniform location of '{}': {}", name, location);
        GL30.glUniformMatrix4fv(location, false, mat);
    }

    private int getUniformLocation(String name) {
        if(uniformLocationCache.containsKey(name)) {
            return uniformLocationCache.get(name);
        }

        Log.debug("Registering uniform location of \"{}\"", name);

        int location = GL30.glGetUniformLocation(programId, name);
        uniformLocationCache.put(name, location);
        return location;
    }

}
