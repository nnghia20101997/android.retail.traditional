package com.core.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class AppButton extends androidx.appcompat.widget.AppCompatButton {
    Typeface typeface = FontCache.get("fonts/Roboto-Regular.ttf", getContext());

    /**
     * @param context
     */
    public AppButton(Context context) {
        super(context);
        setTypeface(typeface);
    }

    /**
     * @param context
     * @param attrs
     */
    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(typeface);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(typeface);
    }
}