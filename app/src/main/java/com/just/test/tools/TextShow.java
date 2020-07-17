package com.just.test.tools;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/*
 *	特殊文字显示 (歌词同步显示)
 * */
public class TextShow {
	private SpannableStringBuilder ssb;
	private SpannableString ss;
	//private String str;
	private Map<String,String> map;
	//传入的map必须为LinkedHashMap
	public TextShow(Map<String,String> map) {
		this.map=map;
		ssb=new SpannableStringBuilder();
	}
	//设置字符串的样式（如果要分行显示，在传入的字符串中添加"\n"）
	//根据字符串从map中取值
	public SpannableString changeText(String str){
		ss=new SpannableString(str);
		//设置字符串的大小
		ss.setSpan(new AbsoluteSizeSpan(50),0,ss.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		//设置字符串的颜色
		ss.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	//追加到SpannableStringBuilder中某一行

	//ss 需要追加的有样式的字符串
	public SpannableStringBuilder Add(String str){
		//清除上一次的数据
		ssb.clear();
		//遍历map
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if(key.equals(str)){

				ssb.append(changeText(val));
			}
			else{
				SpannableString ss1=new SpannableString(val);
				ssb.append(ss1);
			}

		}
		/* 也可以用下面的方法
		 * for(String string:map.values()){
			if(string.equals(map.get(str))){
				ssb.append(changeText(str));
			}else{
			SpannableString ss1=new SpannableString(string);
			ssb.append(ss1);
			
			}
			
		}*/
		return ssb;
	}
}