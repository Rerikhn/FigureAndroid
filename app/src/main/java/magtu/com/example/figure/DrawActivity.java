package magtu.com.example.figure;

/**
 * Created by Nikita on 30.03.2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class DrawActivity extends Activity /*implements View.OnTouchListener*/ {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final TextView angleText = findViewById(R.id.textView);
        angleText.setText("Angle");

        final TextView scaleText = findViewById(R.id.textView2);
        scaleText.setText("Scale");

        final EditText angle = findViewById(R.id.angle);
        angle.setText("0");

        final EditText scale = findViewById(R.id.scale);
        scale.setText("0.5");

        final Button init = findViewById(R.id.init);
        init.setText("INIT");
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.gameThread.setAngle(Float.parseFloat(angle.getText().toString()));
                GameView.gameThread.setScx(Float.parseFloat(scale.getText().toString()));
                GameView.gameThread.setScy(Float.parseFloat(scale.getText().toString()));
                hideUI();
            }
        });

        final Switch mirrorX = findViewById(R.id.switch_mirror_x);
        mirrorX.setText("Toggle mirror X - axis");
        mirrorX.setTextSize(20);
        mirrorX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                GameView.gameThread.setMirrorX(b);
            }
        });

        final Switch mirrorY = findViewById(R.id.switch_mirror_y);
        mirrorY.setText("Toggle mirror Y - axis");
        mirrorY.setTextSize(20);
        mirrorY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                GameView.gameThread.setMirrorY(b);
            }
        });

        hideUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideUI();
    }

    /**
     * Function that turn on immersive mode
     */
    public void hideUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /**
     * On touch set the touch coordinates to speed of the stars;
     * On move set speed by move.
     *
     * @param event above
     * @return return true if touch
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Do some stuff
                GameView.gameThread.setStartX(event.getX());
                GameView.gameThread.setStartY(event.getY());
                GameView.gameThread.setDraw(true);
                break;
            case MotionEvent.ACTION_MOVE:
               /* GameView.gameThread.setAngle(map(event.getX(),0, metrics.widthPixels,0,360));
                GameView.gameThread.setScx(map(event.getY(),metrics.heightPixels, 0,0.3f,6));
                GameView.gameThread.setScy(map(event.getY(),metrics.heightPixels, 0,0.3f,6));*/
                GameView.gameThread.setStartX(event.getX());
                GameView.gameThread.setStartY(event.getY());
                break;
        }
        return true;
    }
}
