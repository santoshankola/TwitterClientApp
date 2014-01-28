package com.codepath.apps.mytwitterapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	EditText etStatus;
	Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		etStatus = (EditText) findViewById(R.id.etTweet);

		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ComposeTweetActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

	public void onPostStatus(View v) {
		MyTwitterApp.getRestClient().postTweet(etStatus.getText().toString(),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, JSONObject status) {
						Log.d("mydebug", "Success: " + status.toString());

						Intent data = new Intent();
						data.putExtra("tw", Tweet.fromJson(status));
						// Activity finished ok, return the data
						setResult(RESULT_OK, data); // set result code and
													// bundle data for response
						finish();
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject errors) {
						Log.d("DEBUG", "Failure: " + errors.toString());

						try {
							Toast.makeText(
									ComposeTweetActivity.this,
									errors.getJSONArray("errors")
											.getJSONObject(0)
											.getString("message"),
									Toast.LENGTH_LONG).show();
							etStatus.setText("");
						} catch (JSONException e) {
							Toast.makeText(ComposeTweetActivity.this,
									"Unable to update the status",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
					}

				});
	}

}