package in.aternal.betterlearner.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import in.aternal.betterlearner.R;

public class TechniquePullServiceReceiver extends BroadcastReceiver {

  public static final String ACTION_DATA_FETCH = "in.aternal.betterlearner.action_data_fetch";

  @Override
  public void onReceive(Context context, Intent intent) {
    String fetchStatus = intent.getStringExtra(TechniquePullService.FETCH_STATUS);
    if (fetchStatus.equals(TechniquePullService.FETCH_COMPLETE)) {
      ProgressBar progressBar = ((AppCompatActivity) context).findViewById(R.id.progress_bar);
      progressBar.setVisibility(View.GONE);
    }
  }
}
