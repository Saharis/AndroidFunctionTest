package com.virgil.aft.business.view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.virgil.aft.ICountSerAIDL;
import com.virgil.aft.R;
import com.virgil.aft.core.ApplicationCache;
import com.virgil.aft.framework.BasicActivity;
import com.virgil.aft.util.CommUtil;
import com.virgil.aft.util.LogUtil;
import com.virgil.aft.util.TestDateTime;


public class HomeActivity extends BasicActivity implements View.OnClickListener {
    private TextView tx_2;
    private ICountSerAIDL iCountSer = null;
    private Thread myThread;
    private DatagramSocket socketServer;
    private TextView textView;
    TestDateTime rasa=new TestDateTime();
    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCountSer = ICountSerAIDL.Stub.asInterface(service);
            ApplicationCache.getInstance().setiCountSer(iCountSer);
            try {
                LogUtil.i("CountServiceAIDL Binded:getCount is " + iCountSer.getCon());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iCountSer = null;
            LogUtil.i("CountService Binded: stoped ");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button:
                applyViewHeight(textView);
                initTextViewColor(textView);
//                slideview(textView, 0, 100);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String content=(String)msg.obj;
                    if(TextUtils.isEmpty(content)){
                        tx_2.append("\n"+content);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_home, null);
        setContentView(view);
        tx_2 = (TextView) view.findViewById(R.id.main_text2);
        ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.main_fliper);
        flipper.startFlipping();
        flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));
        textView = (TextView) this.findViewById(R.id.main_text);
        this.findViewById(R.id.main_button).setOnClickListener(this);
        this.findViewById(R.id.main_button2).setOnClickListener(this);
        this.findViewById(R.id.main_button3).setOnClickListener(this);
        this.bindService(new Intent("com.virgil.aft.framework.CountService"), serviceCon, BIND_AUTO_CREATE);
//        this.startService(new Intent(this, CountService.class));
        try {
            socketServer=new DatagramSocket(8001);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommUtil.showToast("U got it");
            }
        });
        initMyThread();
        rasa.assgined();
    }
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private void initTextViewColor(View view){
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "textColor", RED, BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }
    private void applyViewHeight(View view){
        ValueAnimator stretchAnim2 = ObjectAnimator.ofInt(view, "height",
                view.getHeight(), view.getHeight() + 100);
        stretchAnim2.setDuration(2000);
//        stretchAnim2.setRepeatCount(1);
        stretchAnim2.setInterpolator(new DecelerateInterpolator());
        stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(stretchAnim2);
        bouncer.start();
    }
private void initMyThread(){
    myThread=new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what=1;
            Bundle b = new Bundle();

            try {
                // 定义缓冲区
                byte[] buffer = new byte[1024];
                // 定义接收数据包
                DatagramPacket packet = new DatagramPacket(buffer,
                        buffer.length);
                while (true) {
                    msg = mHandler.obtainMessage();
                    // 接收数据
                    socketServer.receive(packet);
                    // 判断是否收到数据，然后输出字符串
                    if (packet.getLength() > 0) {
                        String str = new String(buffer, 0, packet
                                .getLength());
                        b.putString("data", str + "\n");
                        msg.setData(b);
                        // 将Message对象加入到消息队列当中
                        mHandler.sendMessage(msg);
                        Log.e("myLog", "sendMessage");
                    }
                }
            } catch (SocketException e) {
                Log.e("DEBUG_TAG", e.toString());// e.printStackTrace();原来还想外围加个while用label跳转，试试而已
            } catch (IOException e) {
                Log.e("DEBUG_TAG", e.toString());// e.printStackTrace();
            }
        }
    });
}
    @Override
    protected void onResume() {
        super.onResume();
        if(myThread!=null&&!myThread.isAlive()&&socketServer!=null){
            if(socketServer.isClosed()){
                Log.e("myLog","Resume thread");
                // 定义UDP监听
                try {
                    socketServer = new DatagramSocket(8001);
                    //server.setReuseAddress(true);
                } catch (SocketException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            initMyThread();
            myThread.start();
            //mReceiveThread.run();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void slideview(final View view, final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, p1, p2);
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
                int height = (int) (p2 - p1);
                view.clearAnimation();
//                view.setX(left);
                view.setY(top+height);
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
//        this.unbindService(serviceCon);
        super.onDestroy();
    }
}