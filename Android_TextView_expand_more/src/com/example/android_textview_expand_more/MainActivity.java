package com.example.android_textview_expand_more;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

/**
 * 
 * @author xiaohui.wu
 *
 */
public class MainActivity extends Activity {
 final String str = "During the three-day visit from Saturday to Tuesday in Beijing, India's foreign minister Sushma Swaraj said India attaches great importance to bilateral ties and Modi is willing to pay a visit to China in May and her visit is \"a preparatory visit\" for the upcoming leaders' summit between the two countries.";
 private TextView tv;
 private boolean openState = Boolean.FALSE;
 private boolean isExpand = Boolean.FALSE;
 private ViewTreeObserver observer;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  initView();
 }

 private void initView(){
  tv = (TextView) findViewById(R.id.textView);
  tv.setText(str);
  
  if(!isExpand){
   initDetailText();
  }
  tv.setOnClickListener(new OnClickTextListener());
 }

 class OnClickTextListener implements OnClickListener{
  @Override
  public void onClick(View v) {
   if(!openState){
    disableObserver();
    tv.setText(str);
    openState = true;
    isExpand = true;
   }else{
    initDetailTextViewFormat();
    openState = false;
    isExpand = false;
   }
  }

 }

 private void initDetailText() {
  observer = tv.getViewTreeObserver();
  observer.addOnGlobalLayoutListener(myObserver);
 }
 
 /**
  * init detail textview less than three lines
  */
 private void  initDetailTextViewFormat() {
  if(tv.getLineCount() > 3){
   int lineEndIndex = tv.getLayout().getLineEnd(2); 
   String text = str.subSequence(0, lineEndIndex-7) +"...more";
   SpannableStringBuilder style = new SpannableStringBuilder(text);
   style.setSpan(new ForegroundColorSpan(Color.BLUE), lineEndIndex-7, lineEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
   tv.setText(style);

  }
 }

 private OnGlobalLayoutListener myObserver = new OnGlobalLayoutListener() {

  @Override
  public void onGlobalLayout() {
   if(tv.getLineCount() > 3){
    int lineEndIndex = tv.getLayout().getLineEnd(2); 
    String text = str.subSequence(0, lineEndIndex-7) +"...more";
    SpannableStringBuilder style = new SpannableStringBuilder(text);
    style.setSpan(new ForegroundColorSpan(Color.BLUE), lineEndIndex-7, lineEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv.setText(style);
   }
  }
 };
 

 private void disableObserver(){
  observer = tv.getViewTreeObserver();
  observer.removeGlobalOnLayoutListener(myObserver);
 }

}