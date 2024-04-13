package org.monkeg.rendering.debug;

import org.joml.Matrix4f;
import org.monkeg.api.debug.Circle;
import org.monkeg.api.debug.Line;

public class DebugLayer {
    private final CircleBuffer circleBuffer;
    private final LineBuffer lineBuffer;

    public DebugLayer() {
        circleBuffer = new CircleBuffer();
        lineBuffer = new LineBuffer();
    }

    public void addCircle(Circle circle) {
        circleBuffer.pushCircle(circle);
    }
    public void addLine(Line line) {
        lineBuffer.pushLine(line);
    }

    public void clear() {
        circleBuffer.clear();
        lineBuffer.clear();
    }

    public void prepCircles(Matrix4f viewMat, Matrix4f projMat) {
        circleBuffer.drawPrep(viewMat, projMat);
    }

    public void prepLines(Matrix4f viewMat, Matrix4f projMat) {
        lineBuffer.drawPrep(viewMat, projMat);
    }

    public int getCircleCount() {
        return circleBuffer.getCircleCount();
    }

    public int getLineCount() {
        return lineBuffer.getLineCount();
    }
}
