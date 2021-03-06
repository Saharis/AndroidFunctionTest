package com.virgil.aft.business.view;


import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.virgil.aft.R;
import com.virgil.aft.framework.BasicActivity;
import com.virgil.aft.framework.BasicFragment;
import com.virgil.aft.util.LogUtil;

/**
 * Created by liuwj on 2014/6/19.
 */
public class ThirdActivity extends BasicActivity implements View.OnClickListener {

    private Fragment1 fragment1;
    private Fragment_Calender fragment_calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.third_layout);

        setProgressBarVisibility(true);

        final ProgressBar progressHorizontal = (ProgressBar)findViewById(R.id.progress_horizontal);

        setProgress(progressHorizontal.getProgress() * 100);

        setSecondaryProgress(progressHorizontal.getSecondaryProgress()* 100);
        excuteFragment(new Fragment2());
//        ActionBar bar=getActionBar();
//        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment1>(this,"Fragment1",Fragment1.class)).setText("Fragment1"));
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment2>(this,"Fragment2",Fragment2.class)).setText("Fragment2"));
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment3>(this,"Fragment3",Fragment3.class)).setText("Fragment3"));
//
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment_Calender>(this, "Calendar", Fragment_Calender.class)).setText("Calendar"));
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment_SlideDrawer>(this,"Fragment_SlideDrawer",Fragment_SlideDrawer.class)).setText("Fragment_SlideDrawer"));
//        bar.addTab(bar.newTab().setTabListener(new TabListener<Fragment_Dialog>(this,"Fragment_Dialog",Fragment_Dialog.class)).setText("Fragment_Dialog"));
//        bar.addTab(bar.newTab().setTabListener(new TabListener<TestDialogFragment>(this,"TestDialogFragment",TestDialogFragment.class)).setText("TestDialogFragment"));

//        this.findViewById(R.id.stack_button_1).setOnClickListener(this);
//        this.findViewById(R.id.stack_button_2).setOnClickListener(this);
//        this.findViewById(R.id.stack_button_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
    private void excuteFragment(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if(fragment!=null){
            if(!fragment.isDetached()){

            }
        }
        ft.replace(R.id.fragment_to_replace,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void jumpToFragment(BasicFragment fragment,String tag){
        FragmentManager fgManager=getSupportFragmentManager();
        FragmentTransaction ft=fgManager.beginTransaction();
        BasicFragment added=(BasicFragment)fgManager.findFragmentByTag(tag);
        if(added!=null){
            if(added.isDetached()){
                ft.attach(added);
                ft.commit();
                LogUtil.i("This Fragment"+added.getClass().getSimpleName()+" is never attached");
            }else{
                LogUtil.i("This Fragment"+added.getClass().getSimpleName()+" is already attached");
            }
        }else{
            LogUtil.i("This Fragment"+fragment.getClass().getSimpleName()+" is new");
            ft.add(R.id.fragment_to_replace,fragment,tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

//        ft.replace(R.id.fragment_to_replace,fragment);

//        ft.addToBackStack(null);

    }


}
