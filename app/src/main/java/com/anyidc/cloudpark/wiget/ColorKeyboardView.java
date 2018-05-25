package com.anyidc.cloudpark.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;

import com.anyidc.cloudpark.R;

import java.util.List;

public class ColorKeyboardView extends KeyboardView {

    public ColorKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == -1) {//确定按钮
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_confirm);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (key.codes[0] == -9) {//ABC按钮可点
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_letter_enable);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (key.codes[0] == -10) {//省按钮可点
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_province_enable);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (key.codes[0] == -11) {//ABC按钮不可点
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_letter_nomorl);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (key.codes[0] == -12) {//隐藏按钮
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_hide);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            } else if (key.codes[0] == -13) {//省按钮不可点
                Drawable dr = getContext().getResources().getDrawable(R.drawable.img_province_nomorl);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                dr.draw(canvas);
            }
        }
    }
}
