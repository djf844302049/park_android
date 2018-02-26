package com.anyidc.cloudpark.utils;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/** 与设备分辨率相关的工具类
 * @author linwenxiong
 *
 */
public class ViewUtils {

	/**-1代表为初始化过*/
	public static int mScreenWidth=-1,mScreenHeigh=-1;
	
	
	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {
		if( mScreenWidth <= 0 )
		{
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			 mScreenWidth = dm.widthPixels;
		}
		return mScreenWidth;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowHeigh(Context context) {

		if( mScreenHeigh <=0 )
		{
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			 mScreenHeigh = dm.heightPixels;
		}
		return mScreenHeigh;
	}


	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp的单位 转成为 px
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * scale + 0.5f);
	}
	/**根据String的大小和string 获取该string需要的长度
	 * @param text 要判断的字符串
	 * @param size 字符串的大小以sp为标准
	 * @return
	 */
	public static float getStringWidthForSp(Context paramContext, String text, float size){
		float width = 0;
		TextPaint paint = new TextPaint();
		paint.setTextSize(dip2px(paramContext, size));
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		width = rect.width();
		return width;
	}

	/**根据String的大小和string 获取该string需要的长度
	 * @param text 要判断的字符串
	 * @param size 字符串的大小以px为标准
	 * @return
	 */
	public static float getStringWidthForPx(Context paramContext, String text, float size){
		float width = 0;
		TextPaint paint = new TextPaint();
		paint.setTextSize(size);
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		width = rect.width();
		return width;
	}

	public static float getStringHeightForPx(Context paramContext, String text, float size){
		float height = 0;
		TextPaint paint = new TextPaint();
		paint.setTextSize(size);
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		height = rect.height();
		return height;
	}
}
