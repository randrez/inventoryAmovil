package com.app.aplication.inventoryprojectamovil;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;


import com.app.aplication.inventoryprojectamovil.fragments.MenuFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;



/**
 * Created by andres on 23/10/15.
 */
public class SlidingActivity extends SlidingFragmentActivity {

    private boolean closed = false;
    private boolean opened = false;
    private static final String TAG = "SlidingActivity";
    private SlidingMenu sm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                Log.e(TAG, "open");
                getActionBar().setTitle(getString(R.string.menu));
            }
        });

        getActionBar().setIcon(R.mipmap.ic_menu_white_24dp);
        getActionBar().setHomeButtonEnabled(true);

        setInitView();
    }

    private void setInitView() {
        setBehindContentView(R.layout.menu_slide);
        transactionFragments(MenuFragment.newInstance(), R.id.menu_slide);
    }

    public void transactionFragments(Fragment fragment, int viewResource) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewResource, fragment);
        ft.commit();
        toggle();
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            String content_code = result.getContents();
            SharedPreferences settings = getSharedPreferences("myData", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("content_code", content_code);
            editor.commit();
            this.getSlidingMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
