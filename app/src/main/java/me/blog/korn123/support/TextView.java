package me.blog.korn123.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import me.blog.korn123.awesomemanager.R;

/**
 * Created by CHO HANJOONG on 2016-10-09.
 */

public class TextView extends android.widget.TextView {

    private String customAttr;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.customTextViewStyle);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        final TypedArray array = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView, defStyle,
                R.style.Widget_TextView_AppTheme); // see below
        // FIXME
        this.customAttr =
                array.getString(R.styleable.CustomTextView_customAttr);
        array.recycle();
    }

}
