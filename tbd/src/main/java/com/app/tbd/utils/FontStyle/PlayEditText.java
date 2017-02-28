package com.app.tbd.utils.FontStyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class PlayEditText extends EditText {

    public PlayEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PlayEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/play_regular.ttf");
            setTypeface(tf);
        }
    }

}