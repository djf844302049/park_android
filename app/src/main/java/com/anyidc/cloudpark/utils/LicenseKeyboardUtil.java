package com.anyidc.cloudpark.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.anyidc.cloudpark.R;

import java.lang.reflect.Method;

public class LicenseKeyboardUtil {
    private EditText mEdit;
    private Context mContext;
    private Keyboard number_letter_keyboard;
    private Keyboard province_keyboard;
    private Keyboard car_num_keyboard;
    private KeyboardView mKeyboardView;

    public LicenseKeyboardUtil(Activity activity, EditText edit, int type) {
        mContext = activity;
        mEdit = edit;
        number_letter_keyboard = new Keyboard(mContext, R.xml.letters_nums_keyboard);
        province_keyboard = new Keyboard(mContext, R.xml.province_keyboard);
        car_num_keyboard = new Keyboard(mContext, R.xml.letters_nums_car_keyboard);
        mKeyboardView = activity.findViewById(R.id.keyboard_view);
        switch (type) {
            case 0:
                mKeyboardView.setKeyboard(number_letter_keyboard);
                break;
            case 1:
                mKeyboardView.setKeyboard(province_keyboard);
                break;
        }
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
        hideSystemSoftKeyboard();
    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.down_to_up));
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.up_to_hide));
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    public boolean isShow() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEdit.getText();
            int start = mEdit.getSelectionStart();
            if (primaryCode == -1 || primaryCode == -12) {//确定
                hideKeyboard();
            } else if (primaryCode == -3) {//删除
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == -10) {//切换到省输入面板
                mKeyboardView.setKeyboard(province_keyboard);
            } else if (primaryCode == -9) {//切换到字母数字输入面板
                mKeyboardView.setKeyboard(car_num_keyboard);
            } else if (primaryCode == -11 || primaryCode == -13) {

            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };

    /**
     * 隐藏系统键盘
     */
    public void hideSystemSoftKeyboard() {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mEdit, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mEdit.setInputType(InputType.TYPE_NULL);
        }
    }
}
