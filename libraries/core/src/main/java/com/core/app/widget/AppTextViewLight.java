package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AppTextViewLight extends AppCompatTextView {
    Typeface typeface = FontCache.get("fonts/Roboto-Light.ttf", getContext());

    public AppTextViewLight(Context context) {
        super(context);
        setFontsTextView();
    }

    public AppTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public AppTextViewLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView() {
        setTypeface(typeface);
    }
}
