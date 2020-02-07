package com.customviews;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.fonts.FontUtils;
import com.lotus.R;


/**
 * CustomCheckBox
 * Use for set custom font from .xml file
 */
@SuppressLint("AppCompatCustomView")
public class TypefaceCheckBox extends AppCompatCheckBox {


    public TypefaceCheckBox(Context context) {
        super(context);
    }

    public TypefaceCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt(context, attrs);
    }

    public TypefaceCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inIt(context, attrs);
    }

    /**
     * Initialize and set all required param from this method
     * parsing custom style tags here
     *
     * @param context
     * @param attrs
     */
    private void inIt(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TypefaceCheckBox);
                String fontName = a.getString(R.styleable.TypefaceCheckBox_custom_font);
                Typeface typeface = FontUtils.getInstance().getFont(context, fontName);
                if (typeface != null) {
                    setTypeface(typeface);
                }
            }
        }
    }
}
