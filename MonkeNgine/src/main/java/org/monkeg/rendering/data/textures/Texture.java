package org.monkeg.rendering.data.textures;

import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.monkeg.api.util.logging.Log;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

// TODO: add getter for texture slot

public class Texture {
    private final int textureId;

    int width, height;
    int channels;

    public Texture(String filename) {
        Log.trace("Constructing texture:");
        ImageData data = loadImage(filename);

        width = data.width;
        height = data.height;
        channels = data.channels;

        Log.trace("Texture data: {}w * {}h pixels, {} channels", width, height, channels);

        textureId = createTexture(data.buffer);
        Log.trace("Texture ID: {}", textureId);
        freeLocalBuffer(data.buffer);
    }

    private ImageData loadImage(String filename) {
        Log.trace("Loading image data from \"{}\"...", filename);

        ImageData data = new ImageData();

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            data.buffer = STBImage.stbi_load(filename, w, h, channels, 4);

            if(data.buffer == null) {
                throw new Exception("Unable to load file: " + filename);
            }

            data.width = w.get();
            data.height = h.get();
            data.channels = channels.get();
        } catch (Exception e) {
            // TODO: make default texture
            Log.error("Error occured while loading image data from \"{}\"", filename);
            e.printStackTrace();
        }

        return data;
    }

    private void freeLocalBuffer(ByteBuffer buffer) {
        Log.trace("Freeing local image buffer...");
        STBImage.stbi_image_free(buffer);
    }

    private int createTexture(ByteBuffer buffer) {
        Log.trace("Creating OpenGL texture...");
        int id = GL30.glGenTextures();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, id);
        GL30.glPixelStorei(GL30.GL_UNPACK_ALIGNMENT, 1);

        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_BORDER);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_BORDER);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA8, width, height, 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);

        return id;
    }

    public void bind(int slot) {
        Log.trace("Binding texture: {} to slot {}", textureId, slot);
        GL30.glActiveTexture(GL30.GL_TEXTURE0 + slot);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
    }

    public void unbind() {
        // TODO unbind from texture slot
        Log.trace("Unbinding texture: {}", textureId);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        Log.trace("Deleting texture: {}", textureId);
        GL30.glDeleteTextures(textureId);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }
}
