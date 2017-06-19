package com.vem_tooling.smartwaterlevelmonitor.font_text_view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by amit on 12/6/17.
 */

public class CorisandeBoldTextView extends AppCompatTextView {

    public CorisandeBoldTextView(Context context){
        super(context);
        init();
    }

    public CorisandeBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CorisandeBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/CorisandeBold.ttf");
        setTypeface(typeface);
    }
}
