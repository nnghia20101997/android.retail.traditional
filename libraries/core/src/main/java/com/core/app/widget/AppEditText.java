package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * ================================================
 * Created by HuuThang on 17/01/2021 10:40
 * ================================================
 */
public class AppEditText extends AppCompatEditText {
    Typeface typeface = FontCache.get("fonts/Roboto-Regular.ttf", getContext());
    public AppEditText(Context context) {
        super(context);
        setFonts();
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFonts();
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFonts();
    }

    private void setFonts() {
        setTypeface(typeface);
    }
}
