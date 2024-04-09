package org.monkeg.rendering.debug;

import org.joml.Matrix4f;

public interface DebugDrawable {
    void draw(Matrix4f viewMat, Matrix4f projMat);

    void cleanUp();
}
