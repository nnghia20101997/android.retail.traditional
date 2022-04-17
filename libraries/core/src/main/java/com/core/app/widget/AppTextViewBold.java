package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class AppTextViewBold extends AppCompatTextView {
    Typeface typeface = FontCache.get("fonts/Roboto-Medium.ttf", getContext());
    public AppTextViewBold(Context context) {
        super(context);
        setFontsTextView();
    }

    public AppTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public AppTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView() {
        setTypeface(typeface);
    }
}
