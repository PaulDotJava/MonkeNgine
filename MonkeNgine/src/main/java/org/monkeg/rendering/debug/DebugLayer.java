package org.monkeg.rendering.debug;

import org.joml.Matrix4f;
import org.monkeg.rendering.debug.circle.Circle;
import org.monkeg.rendering.debug.circle.CircleBuffer;

public class DebugLayer {
    private final CircleBuffer circleBuffer;

    public DebugLayer() {
        circleBuffer = new CircleBuffer();
    }

    public void addCircle(Circle circle) {
        circleBuffer.pushCircle(circle);
    }

    public void clear() {
        circleBuffer.clear();
    }

    public void prepCircles(Matrix4f viewMat, Matrix4f projMat) {
        circleBuffer.drawPrep(viewMat, projMat);
    }

    public int getCircleCount() {
        return circleBuffer.getVertexCount();
    }
}
