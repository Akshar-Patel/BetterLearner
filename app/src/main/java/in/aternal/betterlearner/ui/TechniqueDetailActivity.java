package in.aternal.betterlearner.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import in.aternal.betterlearner.R;
import in.aternal.betterlearner.ui.HowFragment.OnHowFragmentInteractionListener;
import in.aternal.betterlearner.ui.WhatFragment.OnWhatFragmentInteractionListener;
import in.aternal.betterlearner.ui.WhyFragment.OnWhyFragmentInteractionListener;

public class TechniqueDetailActivity extends AppCompatActivity implements
    OnWhatFragmentInteractionListener, OnWhyFragmentInteractionListener,
    OnHowFragmentInteractionListener {

  private String mExtraTechniqueIdString;
  private int mItemId;
  private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      return replaceFragment(item.getItemId());
    }
  };

  private boolean replaceFragment(int itemId) {
    mItemId = itemId;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager
        .beginTransaction();

    switch (itemId) {
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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_technique_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    mExtraTechniqueIdString = null;
    if (getIntent().hasExtra(MainActivity.EXTRA_TECHNIQUE_ID)) {
      mExtraTechniqueIdString = String
          .valueOf(getIntent().getIntExtra(MainActivity.EXTRA_TECHNIQUE_ID, 0));
    }

    if (savedInstanceState != null) {
      int itemId = savedInstanceState.getInt("item_id");
      replaceFragment(itemId);
    } else {
      WhatFragment whatFragment = WhatFragment.newInstance(mExtraTechniqueIdString);
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager
          .beginTransaction();
      fragmentTransaction.replace(R.id.frame_layout_fragment_container,
          whatFragment).commit();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt("item_id", mItemId);
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
