package magtu.com.example.figure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;

/**
 * Created by Nikita on 01.04.2018.
 */

class Figure {

    /**
     * Drawing a specific figure
     * @param canvas main canvas
     * @param paint ...
     * @param angle for rotating objects
     * @param scx center X
     * @param scy center Y
     * @param startX X by finger tap
     * @param startY Y by finger tap
     */
    void drawFigure(Canvas canvas, Paint paint,
                    float angle, float scx, float scy, float startX, float startY) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        // PreRendered calculate
            // Move to
            canvas.translate(startX, startY);
            // Rotating from current point
            canvas.rotate(angle);
            // Scaling all picture
            canvas.scale(scx+0.5f, scy+0.5f);

        // Start draw
        canvas.drawLine(-40, -20, 30, -20, paint);
            canvas.translate(30, -20);
        canvas.drawLine(0, 0, 0, -10, paint);
            canvas.translate(0, -10);
        canvas.drawLine(0, 0, 30, 30, paint);
            canvas.translate(30, 30);
        canvas.drawLine(0, 0, -30, 30, paint);
            canvas.translate(-30,30);
        canvas.drawLine(0,0,0,-10,paint);
            canvas.translate(0,-10);
        canvas.drawLine(0,0,-70,0,paint);
            canvas.translate(-70,0);
        canvas.drawLine(0,0,20,-20,paint);
            canvas.translate(20,-20);
        canvas.drawLine(0,0,-20,-20,paint);
    }

}
