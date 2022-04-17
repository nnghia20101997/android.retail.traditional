package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;



/**
 * ================================================
 * Created by HuuThang on 17/01/2021 10:40
 * ================================================
 */
public class AppTextView extends AppCompatTextView {
    Typeface typeface = FontCache.get("fonts/Roboto-Regular.ttf", getContext());
    public AppTextView(Context context) {
        super(context);
        setFontsTextView();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView() {
        setTypeface(typeface);
    }
}
