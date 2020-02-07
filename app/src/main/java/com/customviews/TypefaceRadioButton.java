package com.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.fonts.FontUtils;
import com.lotus.R;


/**
 * CustomRadioButton
 * Use for set custom font from .xml file
 */
public class TypefaceRadioButton extends AppCompatRadioButton {


    public TypefaceRadioButton(Context context) {
        super(context);
    }

    public TypefaceRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt(context, attrs);
    }

    public TypefaceRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TypefaceRadioButton);
                String fontName = a.getString(R.styleable.TypefaceRadioButton_custom_font);
                Typeface typeface = FontUtils.getInstance().getFont(context, fontName);
                if (typeface != null) {
                    setTypeface(typeface);
                }
            }
        }
    }
}
