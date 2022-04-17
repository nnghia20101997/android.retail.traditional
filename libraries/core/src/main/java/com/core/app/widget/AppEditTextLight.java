package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


public class AppEditTextLight extends AppCompatEditText {
    Typeface typeface = FontCache.get("fonts/Roboto-Light.ttf", getContext());
    public AppEditTextLight(Context context) {
        super(context);
        setFonts();
    }

    public AppEditTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFonts();
    }

    public AppEditTextLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFonts();
    }

    private void setFonts() {
        setTypeface(typeface);
    }
}
