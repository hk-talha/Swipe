package com.example.jnetbackup.swipe;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
//import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    android.support.v7.app.ActionBar actionBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      Bundle b=  getIntent().getExtras();
        Log.d("BUNDLE",b.get("Branch_id").toString());
setTitle("EMS");
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
TabAdapter.putExtra(b.getStringArrayList("Branch_id"),b.getStringArrayList("Branch_name"),this,b.getString("username"));
        Tab = (ViewPager)findViewById(R.id.pager);

        if (Tab != null) {
            Tab.setOffscreenPageLimit(3);
        }
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                        if(position ==0)
                        {
                            Log.d("1","1");
                            Android.ip1 ="119.157.128.3:2000";
                            Log.d("1",Android.ip1);}
                        if(position==1){
                            Android.ip1 ="119.157.128.3:2030";
                            Log.d("2",Android.ip1);
                        }
                        if(position==2){
                            Android.ip1 ="119.157.128.3:2030";}
                        Log.d("3","3");
                        Log.d("3",Android.ip1);
                        actionBar = getSupportActionBar();
                        actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(TabAdapter);

        actionBar = getSupportActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

               }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

            }
        };
        //Add New Tab

       actionBar.addTab(actionBar.newTab().setText("Branch").setTag("kar").setTabListener((android.support.v7.app.ActionBar.TabListener) tabListener));
    //   actionBar.addTab(actionBar.newTab().setText("Branch 2").setTag("bad").setTabListener((android.support.v7.app.ActionBar.TabListener) tabListener));
      //  actionBar.addTab(actionBar.newTab().setText("Branch 3").setTag("naz").setTabListener((android.support.v7.app.ActionBar.TabListener) tabListener));
actionBar.addTab(actionBar.newTab().setTabListener(tabListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.button, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_cart)
        {
            startActivity(new Intent(getBaseContext(),Table.class));
            Toast.makeText(getApplicationContext(),"selected",Toast.LENGTH_LONG).show();
        }
        else
        if(item.getItemId()==R.id.action_settings)
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
//        switch (item.getItemId())
//        {
//
//            case R.id.action_settings:
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            case R.id.action_cart:
//                startActivity(new Intent(getBaseContext(),Table.class));
//                Toast.makeText(getApplicationContext(),"selected",Toast.LENGTH_LONG).show();
//        }
        return super.onOptionsItemSelected(item);
    }
}