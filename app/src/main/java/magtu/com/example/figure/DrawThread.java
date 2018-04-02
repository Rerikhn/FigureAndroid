package magtu.com.example.figure;

/**
 * Created by Nikita on 01.04.2018.
 */

import android.annotation.SuppressLint;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

/**
 * Main class of all graphics with thread
 */
public class DrawThread extends Thread {

    private DisplayMetrics metrics;
    private final SurfaceHolder surfaceHolder;
    private long prevTime;
    private Paint paint;
    private Figure figure;
    private float angle, scx, scy,
            startX, startY;
    private boolean draw = false;
    private boolean mirrorX = false;
    private boolean mirrorY = false;
    private boolean runFlag = false;

    @SuppressLint("ClickableViewAccessibility")
    DrawThread(SurfaceHolder surfaceHolder, DisplayMetrics metrics) {
        this.surfaceHolder = surfaceHolder;
        this.metrics = metrics;
        angle = 0;
        scx = 0.5f;
        scy = 0.5f;
        // Paint
        paint = new Paint();
        paint.setAntiAlias(true);
        // load components for graphics
        figure = new Figure();
        // save current time
        prevTime = System.currentTimeMillis();
    }

    void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (!runFlag) {
            try {
                surfaceHolder.wait();
            } catch (InterruptedException ignored) {
            }
        }
        while (runFlag) {
            // get current time and calculate difference with older time
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            // 1 - second = 1000 milliseconds
            //  60 frames per second = (1/60s) * 1000ms ~ 16.6ms
            if (elapsedTime > 16) {
                //if time > N milliseconds, save current time
                prevTime = now;
                //updateFrame(); // picture update with 60 fps (only for moving objects)
            }
            canvas = null;
            try {
                // get Canvas and create drawings
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas != null) synchronized (surfaceHolder) {
                    // graphics
                    canvas.drawColor(Color.WHITE);
                    drawXY(canvas, paint);

                    if (mirrorX)
                        canvas.scale(-1, 1,
                                metrics.widthPixels / 2, metrics.heightPixels / 2);
                    if (mirrorY)
                        canvas.scale(1, -1,
                                metrics.widthPixels / 2, metrics.heightPixels / 2);
                    if (draw)
                        figure.drawFigure(canvas, paint, angle, scx, scy, startX, startY);
                }
            } finally {
                if (canvas != null) {
                    // if graphics done, set it on display
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    void setDraw(boolean draw) {
        this.draw = draw;
    }

    void setMirrorX(boolean mirror) {
        this.mirrorX = mirror;
    }

    void setMirrorY(boolean mirror) {
        this.mirrorY = mirror;
    }

    void setStartX(float startX) {
        this.startX = startX;
    }

    void setStartY(float startY) {
        this.startY = startY;
    }

    void setAngle(float angle) {
        this.angle = angle;
    }

    void setScx(float scx) {
        this.scx = scx;
    }

    void setScy(float scy) {
        this.scy = scy;
    }

    private void updateFrame() {
        // supporting method for graphics update
        // only for moving objects
    }

    /**
     * Drawing a XY - axis
     *
     * @param canvas ...
     * @param paint  ...
     */
    private void drawXY(Canvas canvas, Paint paint) {
        paint.setColor(Color.GRAY);
        // X
        canvas.drawLine(0, metrics.heightPixels / 2,
                metrics.widthPixels, metrics.heightPixels / 2, paint);
        // Y
        canvas.drawLine(metrics.widthPixels / 2, 0,
                metrics.widthPixels / 2, metrics.heightPixels, paint);
    }

    /**
     * This function is remapping line value
     * Example:
     * 0 from 10
     * remap(4 from 0  to 10) = 40 from 0 to 100
     *
     * @param s  value to find
     * @param a1 start value 1
     * @param a2 stop value 1
     * @param b1 start value 2
     * @param b2 start value 2
     * @return returns a float value
     */
    private float map(float s, float a1, float a2, float b1, float b2) {
        return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
    }
}


