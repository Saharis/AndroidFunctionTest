package com.virgil.aft.business.view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.virgil.aft.R;
import com.virgil.aft.framework.BasicActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by liuwj on 2014/6/18.
 */
public class SecondActivity extends BasicActivity {

    private ImageView iv;
    private int WindowWidth;
    private int WindowHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.second_layout,null);
        setContentView(view);

        iv = (ImageView) view.findViewById(R.id.iv);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowHeight = wm.getDefaultDisplay().getHeight();
        WindowWidth = wm.getDefaultDisplay().getWidth();

    }
    public static void initFragment(FragmentManager fragmentManager,Fragment targetFragment,String tag,int postion){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(postion, targetFragment, tag);
        transaction.commitAllowingStateLoss();
    }

    private String picPath=Environment.getExternalStorageDirectory().getPath()+"/a.jpg";
    public void load(View v) throws IOException
    {
        // 图片解析配置
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 不去真的解析图片 获取图片头部信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        System.out.println("宽" + width + "高" + height);

        // 得到手机屏幕宽高比
        int scaleX=width/WindowWidth;
        int scaleY=height/WindowHeight;
        int scale=1;
        if(scaleX>scaleY && scaleY>=1)
        {
            scale=scaleX;
        }

        if(scaleY>scaleX && scaleX>=1)
        {
            scale=scaleY;
        }

        options.inJustDecodeBounds=false;
        options.inSampleSize=scale;
        AsyncTask as=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                BitmapFactory.Options option=(BitmapFactory.Options)params[0];
                Bitmap mitmap = BitmapFactory.decodeFile(picPath, option);
                return mitmap;
            }

            @Override
            protected void onPostExecute(Object o) {
                Bitmap mitmap=(Bitmap)o;
                iv.setImageBitmap(mitmap);
                try {
                    readEXIF(picPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute(options);
    }

    private void readEXIF(String path) throws IOException
    {
        ExifInterface exif=new ExifInterface(path);
        //获取信息
        String time=exif.getAttribute(ExifInterface.TAG_DATETIME);
        String model=exif.getAttribute(ExifInterface.TAG_MODEL);
        String iso=exif.getAttribute(ExifInterface.TAG_ISO);

        //设置信息 tag可以自定义
        exif.setAttribute(ExifInterface.TAG_EXPOSURE_TIME, 100+"");
        exif.saveAttributes();
        String ex_time=exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("EXIF信息");
        builder.setMessage(time+"\n"+model+"\n"+iso+"\n"+ex_time);
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }

        });
        builder.create().show();
    }
}
