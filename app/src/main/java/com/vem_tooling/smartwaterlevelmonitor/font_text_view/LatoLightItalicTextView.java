package com.vem_tooling.smartwaterlevelmonitor.font_text_view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by amit on 12/6/17.
 */

public class LatoLightItalicTextView extends AppCompatTextView {

    public LatoLightItalicTextView(Context context){
        super(context);
        init();
    }

    public LatoLightItalicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoLightItalicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Lato-LightItalic.ttf");
        setTypeface(typeface);
    }
}
