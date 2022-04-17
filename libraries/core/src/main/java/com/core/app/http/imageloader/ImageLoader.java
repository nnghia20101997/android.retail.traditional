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
package com.core.app.http.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

import com.core.app.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ImageLoader {
    @Inject
    @Nullable
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader() {
    }

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        //noinspection unchecked
        this.mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        //noinspection unchecked
        this.mStrategy.clear(context, config);
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }
}
