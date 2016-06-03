package framgia.vn.voanews.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import framgia.vn.voanews.R;
import framgia.vn.voanews.adapters.ViewpagerAdapter;
import framgia.vn.voanews.fragments.AllZonesFragment;
import framgia.vn.voanews.utils.LinkRssUtil;

public class MainActivity extends AppCompatActivity {
    private ViewpagerAdapter mViewpagerAdapter;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_home);
        mTabLayout = (TabLayout) findViewById(R.id.tab_home);
        setupViewPager(mViewPager, LinkRssUtil.LINK_RSS, LinkRssUtil.TITLE_RSS);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager, String[] linkRss, String[] titleRss) {
        mViewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < titleRss.length; i++) {
            mViewpagerAdapter.addFragment(AllZonesFragment.newInstance(linkRss[i], titleRss[i]), titleRss[i]);
        }
        viewPager.setAdapter(mViewpagerAdapter);
    }
}
