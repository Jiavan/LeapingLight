package com.jiavan.leapinglight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class StageActivity extends Activity{

	private int startX;
	private int endX;
	private int screenWidth;
	private BluetoothLeClass mBLE;
	private BleData bleObj;
	private Button bt_shade;
	private String deviceAdress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage);
		initData();
		mBLE.connect(deviceAdress);
		Log.i("TAG", deviceAdress + "is :" + mBLE.connect(deviceAdress));
		
		bt_shade = (Button)findViewById(R.id.bt_shade);
		bt_shade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Constant.gattWriteServices(mBLE.getSupportedGattServices(), new byte[]{0x09}, mBLE);
			}
		});
	}
	
	private void initData(){
		this.startX = 0;
		this.endX = 0;
		WindowManager wm = this.getWindowManager();
		this.screenWidth = wm.getDefaultDisplay().getWidth();
		
		bleObj = (BleData)this.getApplication();
		this.mBLE = bleObj.getmBle();
		/*Intent intent = this.getIntent();
		deviceAdress = intent.getStringExtra("adress");*/
		
		if (!mBLE.initialize()) {
        	Toast.makeText(this, "init data error!", Toast.LENGTH_LONG).show();
            finish();
        }
		
		/*llCricle = (LinearLayout)findViewByLIGHT_IIIId(R.id.ll_circle);
		LayoutParams lp = llCricle.getLayoutParams();
		lp.width = (int)this.screenWidth / 2;		dest.writeValue(mBLE);
		lp.height = lp.width;
		llCricle.setLayoutParams(lp);*/
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int)event.getX();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			this.startX = x;
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			this.endX = x;
			if((endX - startX) > (this.screenWidth / 4)){
				/*Intent intent = new Intent();
				intent.setClass(StageActivity.this, MainActivity.class);
				StageActivity.this.startActivity(intent);*/
				
				finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			}
		}
		return super.onTouchEvent(event);
	}
}
