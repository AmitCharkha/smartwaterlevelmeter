package com.vem_tooling.smartwaterlevelmonitor.font_text_view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by amit on 12/6/17.
 */

public class LatoRegularTextView extends AppCompatTextView {

    public LatoRegularTextView(Context context){
        super(context);
        init();
    }

    public LatoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Lato-Regular.ttf");
        setTypeface(typeface);
    }
}
