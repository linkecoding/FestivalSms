package com.codekong.festival_sms.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codekong.festival_sms.R;
import com.codekong.festival_sms.fragment.FestivalCategoryFragment;
import com.codekong.festival_sms.fragment.SmsHistoryFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //选项卡切换的标题
    private String[] mTitles = new String[]{"节日短信", "发送记录"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 1)
                    return new SmsHistoryFragment();
                return new FestivalCategoryFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
