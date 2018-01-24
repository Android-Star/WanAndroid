package com.lw.wanandroid;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lw.wanandroid.base.BaseActivity;
import com.lw.wanandroid.base.BaseFragment;
import com.lw.wanandroid.bean.User;
import com.lw.wanandroid.ui.home.HomeFragment;
import com.lw.wanandroid.ui.hotsearch.HotFragment;
import com.lw.wanandroid.ui.knowledgesystem.KnowledgeSystemFragment;
import com.lw.wanandroid.ui.my.LoginContract;
import com.lw.wanandroid.ui.my.LoginPresenter;
import com.lw.wanandroid.ui.my.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<LoginPresenter> implements BottomNavigationView.OnNavigationItemSelectedListener, LoginContract.View {
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private List<BaseFragment> mFragments;
    private int mLastFgIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mNavigation.setOnNavigationItemSelectedListener(this);
        initFragment();
        switchFragment(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mToolbar.setTitle(R.string.app_name);
                switchFragment(0);
                break;
            case R.id.navigation_knowledgesystem:
                mToolbar.setTitle(R.string.title_knowledgesystem);
                switchFragment(1);
                break;
            case R.id.navigation_my:
                mToolbar.setTitle(R.string.title_my);
                switchFragment(2);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuHot) {
            mToolbar.setTitle(R.string.hot_title);
            switchFragment(3);
        } else if (item.getItemId() == R.id.menuSearch) {
            ARouter.getInstance().build("/hotsearch/SearchActivity").navigation();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(KnowledgeSystemFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        mFragments.add(HotFragment.newInstance());
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded())
            ft.add(R.id.layout_fragment, targetFg);
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void loginSuccess(User user) {
    }
}