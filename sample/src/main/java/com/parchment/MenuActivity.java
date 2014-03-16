package com.parchment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by emir on 15/03/14.
 */
public class MenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu);
        super.onCreate(savedInstanceState);
    }

    public void startActivity(Class<?> activityClass){
        final Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void onClickListView(final View view){
        startActivity(SimpleListViewActivity.class);
    }

    public void onClickGridView(final View view){
        startActivity(SimpleGridViewActivity.class);
    }

    public void onClickGridDefinitionView(final View view){
        startActivity(SimpleGridDefinitionViewActivity.class);
    }

    public void onClickViewPager(final View view){
        startActivity(SimpleViewPagerActivity.class);
    }


}