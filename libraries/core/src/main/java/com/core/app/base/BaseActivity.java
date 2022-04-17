/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.core.app.base;

import static com.core.app.utils.ThirdViewUtil.convertAutoView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.core.app.R;
import com.core.app.base.delegate.IActivity;
import com.core.app.integration.cache.Cache;
import com.core.app.integration.cache.CacheType;
import com.core.app.integration.lifecycle.ActivityLifecycleable;
import com.core.app.mvp.IPresenter;
import com.core.app.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;


public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @Inject
    @Nullable
    protected P mPresenter;
    private Cache mCache;
    private Unbinder mUnbinder;
    AlertDialog alertDialog;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Subscribe
    public void login(EvenBusMoveToLogin event) {
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);

        View view1 = layoutInflaterAndroid.inflate(R.layout.dialog_move_to_login, null);
        builder.setView(view1);

        view1.findViewById(R.id.btnOk).setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = null;
//            try {
//                intent = new Intent(this,
//                        Class.forName("mvp.ui.activity.LoginActivity"));
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            startActivity(intent);
        });

        alertDialog = builder.create();

        try {
            View view = initView(savedInstanceState);
            setContentView(view);
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setLanguage(SharedPreferences sharedPreferences) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        String sCode = sharedPreferences.getString("LANGUAGE_APP", "vi");
        if (sharedPreferences.contains("LANGUAGE_APP")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(new Locale(sCode + ""));
            } else {
                config.locale = new Locale(sCode + "");
            }
        }
        resources.updateConfiguration(config, dm);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    private final List<OnRefreshFragment> onRefreshFragments = new ArrayList<>();

    public void setOnRefreshFragment(OnRefreshFragment onRefreshFragment) {
        onRefreshFragments.add(onRefreshFragment);
    }

    public OnRefreshFragment getOnRefreshFragment() {
        if (onRefreshFragments.size() > 0)
            return onRefreshFragments.get(onRefreshFragments.size() - 1);
        else
            return bundle -> Timber.d("null callback stack");
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) BaseApplication.getInstance().getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
