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
package com.core.app.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.app.integration.EventBusManager;
import com.core.app.utils.ArmsUtils;

public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (iActivity.useEventBus()) {
            EventBusManager.getInstance().register(mActivity);
        }

        iActivity.setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(mActivity));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        if (iActivity != null && iActivity.useEventBus()) {
            EventBusManager.getInstance().unregister(mActivity);
        }
        this.iActivity = null;
        this.mActivity = null;
    }
}
