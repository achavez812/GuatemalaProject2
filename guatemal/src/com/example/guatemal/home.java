package com.example.guatemal;
import com.nativecss.NativeCSS;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.Fragment;

public class home extends FragmentActivity implements ActionBar.TabListener{
	private static final int RESULT_LOAD_IMAGE = 1;
	SessionManagement session;
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    Fragment patient_list_frag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		session = new SessionManagement(getApplicationContext());
	    // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
	}
	
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                	
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                	return new patient_list();

                default:
                    // The other sections of the app are dummy placeholders.
                	return new add_patient();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String patientListString="View Patients";
            String addPatientString="Add Patients";
        	if(position==0){
            	
            	return patientListString;
            }else {
            	return addPatientString;
            }
        }
    }
    public void onSwitchView() {
    	finish();
    	startActivity(getIntent());
    	mViewPager.setCurrentItem(0);
    }    		
    
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_logout:
	    	makelogout();
	        return true;
	    case R.id.action_home:
	    	Openhome_page();
	        return true;
	    case R.id.action_about:
	    	Openabout_page();
	        return true;
	    default:
	        return true;
	    }
	}
	
	public void makelogout(){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(home.this, "",	"Loading ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						session.logoutUser();
					} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
	
	public void Openabout_page(){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(home.this, "",	"Loading ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						Intent intent = new Intent(home.this, about_us.class);
						startActivity(intent);
					} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
	
	public void Openhome_page(){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(home.this, "",	"Loading ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						onSwitchView();
					} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
		
	    
	    
	    
	}

