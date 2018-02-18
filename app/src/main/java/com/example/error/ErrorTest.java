package com.example.error;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.testpptplay.animation.mtESTa;
import com.strongsoft.ui.R;

public class ErrorTest extends Activity {
	
	private final String TAG=ErrorTest.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new GoIntent(ErrorTest.this).execute();
	}

	class GoIntent extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
        private Context mContext;
		public GoIntent(Context c) {
           mContext=c;
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(mContext);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
           try {
			Thread.sleep(18000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (dialog != null) {
				dialog.dismiss();
			}
			
			if(isCancelled()){
				Log.e(TAG, "ASYNCTASK 被取消了!!!") ;
			}

			Intent it = new Intent(mContext, mtESTa.class);
			startActivity(it);

		}

	}

}
