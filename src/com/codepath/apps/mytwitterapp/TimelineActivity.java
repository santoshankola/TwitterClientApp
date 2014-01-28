package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	ListView lvTweets;
	private final int REQUEST_CODE = 991;
	TweetsAdapter tweetsAdapter;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		tweetsAdapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetsAdapter);

		refreshTimeLine();
	}

	private void refreshTimeLine() {
		MyTwitterApp.getRestClient().getHomeTimeLine(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						tweets = Tweet.fromJson(jsonTweets);
						tweetsAdapter.clear();
						tweetsAdapter.addAll(tweets);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_compose:
			/*
			 * Intent ic = new Intent(getApplicationContext(),
			 * ComposeTweetActivity.class); startActivity(ic);
			 */

			Intent i = new Intent(TimelineActivity.this,
					ComposeTweetActivity.class);
			startActivityForResult(i, REQUEST_CODE);

			break;
		case R.id.action_refreshtimeline:
			refreshTimeLine();
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			Tweet tw = (Tweet) data.getSerializableExtra("tw");
			refreshTimeLine();
			// tweetsAdapter.add(tw);

		}
	}

}