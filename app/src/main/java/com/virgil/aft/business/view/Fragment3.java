package com.virgil.aft.business.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.virgil.aft.framework.BasicFragment;

/**
 * Created by liuwujing on 14-7-15.
 */
public class Fragment3 extends BasicFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        initData();
        return view;
    }

    private View initView() {
        Activity context = getActivity();
        RelativeLayout.LayoutParams lp_Realative1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lp_Realative2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout baseLayout = new LinearLayout(context);
        RelativeLayout info_Top = new RelativeLayout(context);

        bt_ReadCutomFile = new Button(context);
        bt_ReadCutomFile.setLayoutParams(lp_Realative1);
        lp_Realative1.addRule(RelativeLayout.ALIGN_PARENT_START);
        info_Top.addView(bt_ReadCutomFile, lp_Realative1);


        Button bt_ReadMore = new Button(context);
        bt_ReadMore.setText("下一段");

        bt_ReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile(new File(logPath + currentFileName));
            }
        });
        lp_Realative2.addRule(RelativeLayout.ALIGN_PARENT_END);
        info_Top.addView(bt_ReadMore, lp_Realative2);

        //中间的TextView
        mContentText = new TextView(context);
        mContentText.setVerticalScrollBarEnabled(true);
        mContentText.setMovementMethod(new ScrollingMovementMethod());


        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.addView(info_Top);
        baseLayout.addView(mContentText);
        return baseLayout;
    }

    /**
     * 初始化页面的显示数据，默认读取日志列表中名称排序最后的一个文件
     */
    private void initData() {
        String[] fileList = getFileNameList(logPath, "^PAY_LOG_TAG.*");
        currentFileName = fileList[fileList.length - 1];
        readInfo(logPath + currentFileName, 0);
        bt_ReadCutomFile.setText(currentFileName);
    }


    /**
     * 读取指定路径下的
     *
     * @param folderPath      文件的路径
     * @param fileNameMatcher 文件名称匹配的正则
     * @return
     */
    private static String[] getFileNameList(String folderPath, final String fileNameMatcher) {
        File file = new File(folderPath);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                Pattern p = Pattern.compile(fileNameMatcher);
                Matcher m = p.matcher(name);
                return m.matches();
            }
        };
        return file.list(filenameFilter);
    }

    /**
     * 读取文件的截止行数
     */
    static int mLastReadIndex = 0;
    /**
     * 一次读取的日志条数
     */
    static int READ_NUM_ONCE = 3;
    /**
     * 日志的路径
     */
    static String logPath = Environment.getExternalStorageDirectory().getPath() + "/CTRIP/COMMLOG/";
    /**
     * 当前的读取的日志名称
     */
    static String currentFileName = "";
    /**
     * Handler的type
     */
    /**
     * reload日志
     */
    private static final int CONTENT_REFRESH = 0;
    /**
     * append日志
     */
    private static final int CONTENT_APPEND = CONTENT_REFRESH + 1;
    /**
     * 显示的文案
     */
    private TextView mContentText;
    /**
     * Title上的Button
     */
    private Button bt_ReadCutomFile;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = (String) msg.obj;
            content = content == null ? "" : content;
            switch (msg.what) {
                case CONTENT_REFRESH:
                    mContentText.setText(content);
                    break;
                case CONTENT_APPEND:
                    mContentText.append(content);
                    break;
            }
            removeProcess();
        }
    };

    /**
     * 封装读取日志的逻辑
     *
     * @param fielePath String|日志文件的路径
     */
    private void readInfo(final String fielePath, final int lastReadIndex) {
        showProcess();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = readWithScanner(fielePath, lastReadIndex);
                Message msg = new Message();
                if (lastReadIndex == 0) {
                    msg.what = CONTENT_REFRESH;
                } else {
                    msg.what = CONTENT_APPEND;
                }
                msg.obj = content;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 读取信息
     *
     * @param filePath      String|log文件的路径
     * @param lastReadIndex int|指定读取的起始行数
     * @return String|读取的内容
     */
    public String readWithScanner(String filePath, int lastReadIndex) {
        //Create the file object
        File fileObj = new File(filePath);
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scnr = new Scanner(fileObj);
            //Reading each line of file using Scanner class
            int readed = 0;
            boolean stop = false;
            int currentReadIndex = 0;
            while (scnr.hasNextLine()) {
                String info = scnr.nextLine();
                currentReadIndex++;
                if (currentReadIndex < lastReadIndex) {
                    continue;
                }
                if (info.startsWith("PAY_LOG_TAG")) {
                    sb.append("\n\n");
                    if (readed < READ_NUM_ONCE) {
                        readed++;
                    } else {
                        stop = true;
                    }

                }
                if (stop) {
                    currentReadIndex--;
                    break;
                } else {
                    sb.append(info);
                }
            }
            mLastReadIndex = currentReadIndex;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void showProcess() {
    }

    private void removeProcess() {
    }

    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//设置intent的Action属性
        intent.setAction(Intent.ACTION_EDIT);
//获取文件file的MIME类型
        String type = "text/plain";
//设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
//跳转
        startActivity(intent);
    }

}
