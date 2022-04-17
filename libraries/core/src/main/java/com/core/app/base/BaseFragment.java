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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.app.R;
import com.core.app.base.delegate.IFragment;
import com.core.app.integration.cache.Cache;
import com.core.app.integration.cache.CacheType;
import com.core.app.integration.lifecycle.FragmentLifecycleable;
import com.core.app.mvp.IPresenter;
import com.core.app.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    protected Context mContext;

    private Bundle mSavedInstanceState = new Bundle();
    private Boolean hasInflated = false;
    private ViewStub mViewStub;
    private Boolean visible = false;
    LayoutInflater inflatedView;
    ViewGroup mContainer;
    View inflate;
    @Inject
    @Nullable
    protected P mPresenter;
    private Cache mCache;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewstub, container, false);
        mViewStub = view.findViewById(R.id.fragmentViewStub);
        mViewStub.setLayoutResource(getViewStubLayoutResource());
        mSavedInstanceState = savedInstanceState;
        mContainer = container;
        if (visible && !hasInflated) {
            inflatedView = mViewStub.getLayoutInflater();
            inflate = mViewStub.inflate();
            onCreateViewAfterViewStubInflated(inflate, mSavedInstanceState);
            afterViewStubInflated(view);
        }
        return view;
    }

    @CallSuper
    public void afterViewStubInflated(View originalViewContainerWithViewStub) {
        hasInflated = true;
        if (originalViewContainerWithViewStub != null) {
            View pb = originalViewContainerWithViewStub.findViewById(R.id.inflateProgressbar);
            pb.setVisibility(View.GONE);
        }
    }

    @LayoutRes
    protected abstract Integer getViewStubLayoutResource();

    protected abstract void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        visible = true;
        if (mViewStub != null && !hasInflated) {
            onCreateViewAfterViewStubInflated(mViewStub.inflate(), mSavedInstanceState);
            afterViewStubInflated(getView());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        visible = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasInflated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        hasInflated = false;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }


    public void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) BaseApplication.getInstance().getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void closeKeyboard(EditText edt) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getInstance().getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
    }
}
