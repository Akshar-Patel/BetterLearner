package in.aternal.betterlearner;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import in.aternal.betterlearner.HowFragment.OnHowFragmentInteractionListener;
import in.aternal.betterlearner.WhatFragment.OnWhatFragmentInteractionListener;
import in.aternal.betterlearner.WhyFragment.OnWhyFragmentInteractionListener;

public class TechniqueDetailActivity extends AppCompatActivity implements
    OnWhatFragmentInteractionListener, OnWhyFragmentInteractionListener,
    OnHowFragmentInteractionListener {

  private TextView mTextMessage;
  private String mExtraTechniqueIdString;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager
          .beginTransaction();

      switch (item.getItemId()) {
        case R.id.navigation_what:
          WhatFragment whatFragment = WhatFragment.newInstance(mExtraTechniqueIdString);
          fragmentTransaction.replace(R.id.frame_layout_fragment_container,
              whatFragment).commit();
          return true;
        case R.id.navigation_why:
          WhyFragment whyFragment = WhyFragment.newInstance(mExtraTechniqueIdString);
          fragmentTransaction.replace(R.id.frame_layout_fragment_container,
              whyFragment).commit();
          return true;
        case R.id.navigation_how:
          HowFragment howFragment = HowFragment.newInstance(mExtraTechniqueIdString);
          fragmentTransaction.replace(R.id.frame_layout_fragment_container,
              howFragment).commit();
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

    mExtraTechniqueIdString = null;
    if (getIntent().hasExtra(MainActivity.EXTRA_TECHNIQUE_ID)) {
      mExtraTechniqueIdString = String
          .valueOf(getIntent().getIntExtra(MainActivity.EXTRA_TECHNIQUE_ID, 0));
    }

    WhatFragment whatFragment = WhatFragment.newInstance(mExtraTechniqueIdString);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager
        .beginTransaction();
    fragmentTransaction.add(R.id.frame_layout_fragment_container,
        whatFragment).commit();


  }

  @Override
  public void onWhatFragmentInteraction(Uri uri) {

  }

  @Override
  public void onWhyFragmentInteraction(Uri uri) {

  }

  @Override
  public void onHowFragmentInteraction(Uri uri) {

  }
}
