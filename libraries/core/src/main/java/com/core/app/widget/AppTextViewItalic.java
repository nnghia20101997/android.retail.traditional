package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class AppTextViewItalic extends AppCompatTextView {
    Typeface typeface = FontCache.get("fonts/Roboto-Italic.ttf", getContext());
    public AppTextViewItalic(Context context) {
        super(context);
        setFontsTextView();
    }

    public AppTextViewItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontsTextView();
    }

    public AppTextViewItalic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontsTextView();
    }

    private void setFontsTextView() {
        setTypeface(typeface);
    }
}
