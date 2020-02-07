package com.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.fonts.FontUtils;
import com.lotus.R;


/**
 * Created by ubuntu on 28/12/17.
 */

public class TypefaceTextView extends AppCompatTextView {
    public TypefaceTextView(Context context) {
        super(context);
    }

    public TypefaceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TypefaceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }


    private void init(Context context, AttributeSet attributeSet){
        if (!isInEditMode()){
            if (attributeSet!=null){
                TypedArray typedArray=context.obtainStyledAttributes(attributeSet, R.styleable.TypefaceTextView);
                String fontName=typedArray.getString(R.styleable.TypefaceTextView_custom_font);
                Typeface typeface= FontUtils.getInstance().getFont(context, fontName);
                if (typeface!=null){
                    setTypeface(typeface);
                }
            }
        }
    }
}
