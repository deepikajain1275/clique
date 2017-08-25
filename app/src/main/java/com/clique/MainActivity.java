package com.clique;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

RelativeLayout rl_footer;
ImageView iv_header;
boolean isBottom = true;
Button btn1;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.test);
    rl_footer = (RelativeLayout) findViewById(R.id.rl_footer);
    iv_header = (ImageView) findViewById(R.id.iv_up_arrow);
    iv_header.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            iv_header.setImageResource(android.R.drawable.arrow_down_float);
            iv_header.setPadding(0, 10, 0, 0); 
            rl_footer.setBackgroundResource(android.R.drawable.arrow_up_float);
            if (isBottom) {
                SlideToAbove();
                isBottom = false;
            } else {
                iv_header.setImageResource(android.R.drawable.arrow_up_float);
                iv_header.setPadding(0, 0, 0, 10);
                rl_footer.setBackgroundResource(android.R.drawable.arrow_down_float);
                SlideToDown();
                isBottom = true;
            }

        }
    });

}

public void SlideToAbove() {
    Animation slide = null;
    slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, -2.5f);
    Animation textAniUp = AnimationUtils.loadAnimation(getApplicationContext(),
            R.anim.scale);

    slide.setDuration(400);
    slide.setFillAfter(true);
    slide.setFillEnabled(true);
    rl_footer.startAnimation(textAniUp);

    slide.setAnimationListener(new AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {

           rl_footer.clearAnimation();

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    rl_footer.getWidth(), rl_footer.getHeight());
            // lp.setMargins(0, 0, 0, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            rl_footer.setLayoutParams(lp);

        }

    });

}

public void SlideToDown() {
    Animation slide = null;
    slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 2.5f);

    slide.setDuration(400);
    slide.setFillAfter(true);
    slide.setFillEnabled(true);
    rl_footer.startAnimation(slide);

    slide.setAnimationListener(new AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {

            rl_footer.clearAnimation();

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    rl_footer.getWidth(), rl_footer.getHeight());
            lp.setMargins(0, rl_footer.getWidth(), 0, 0);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            rl_footer.setLayoutParams(lp);

        }

    });

}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
   // getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

 }