package com.virgil.aft.business.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.virgil.aft.ICountSerAIDL;
import com.virgil.aft.R;
import com.virgil.aft.framework.BasicActivity;
import com.virgil.aft.util.LogUtil;


public class HomeActivity extends BasicActivity implements View.OnClickListener {
    private ICountSerAIDL iCountSer=null;
    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCountSer=ICountSerAIDL.Stub.asInterface(service);
            try {
                LogUtil.i("CountServiceAIDL Binded:getCount is "+iCountSer.getCon());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iCountSer=null;
            LogUtil.i("CountService Binded: stoped ");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button:
                TextView textView = (TextView) this.findViewById(R.id.main_text);
                Animation ani = AnimationUtils.loadAnimation(this, R.anim.shake);

//                ani.setFillAfter(true);
//                textView.startAnimation(ani);
                slideview(textView, 0, 100);
//                textView.setX(-10);
//                textView.set
                break;
            case R.id.main_button2:
                Intent in = new Intent(this, SecondActivity.class);
                startActivity(in);
                break;
            case R.id.main_button3:
                Intent in2 = new Intent(this, ThirdActivity.class);
                startActivity(in2);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.main_fliper);
        flipper.startFlipping();
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));
        this.findViewById(R.id.main_button).setOnClickListener(this);
        this.findViewById(R.id.main_button2).setOnClickListener(this);
        this.findViewById(R.id.main_button3).setOnClickListener(this);
//        this.bindService(new Intent("com.virgil.aft.framework.CountService"), serviceCon, BIND_AUTO_CREATE);
//        this.startService(new Intent(this, CountService.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void slideview(final View view, final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
        animation.setStartOffset(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft() + (int) (p2 - p1);
                int top = view.getTop();
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.setX(left);
//                view.setY(top+height);
//                view.layout(left, top, left+width, top+height);
            }
        });
        view.startAnimation(animation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings_main) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.unbindService(serviceCon);
        super.onDestroy();
    }
}