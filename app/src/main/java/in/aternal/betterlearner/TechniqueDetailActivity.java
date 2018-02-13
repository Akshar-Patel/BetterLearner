package in.aternal.betterlearner;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import in.aternal.betterlearner.WhatFragment.OnFragmentInteractionListener;

public class TechniqueDetailActivity extends AppCompatActivity implements
    OnFragmentInteractionListener {

  private TextView mTextMessage;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_what:
          mTextMessage.setText(R.string.title_what);
          WhatFragment whatFragment = WhatFragment.newInstance("test1","test2");
          FragmentManager fragmentManager = getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager
              .beginTransaction();
          fragmentTransaction.add(R.id.frame_layout_fragment_container,
              whatFragment).addToBackStack(null).commit();
          return true;
        case R.id.navigation_why:
          mTextMessage.setText(R.string.title_why);
          return true;
        case R.id.navigation_how:
          mTextMessage.setText(R.string.title_how);
          return true;
      }
      return false;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_technique_detail);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    WhatFragment whatFragment = WhatFragment.newInstance("test1","test2");
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager
        .beginTransaction();
    fragmentTransaction.add(R.id.frame_layout_fragment_container,
        whatFragment).addToBackStack(null).commit();
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
