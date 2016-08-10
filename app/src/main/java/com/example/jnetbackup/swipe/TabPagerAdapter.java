package com.example.jnetbackup.swipe;

/**
 * Created by jnetbackup on 6/14/2016.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> branch_id=new ArrayList<String>();
    ArrayList<String> branch_name=new ArrayList<String>();
    Context c;
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);

        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new Android("119.157.128.3:2000","Karim Centre",branch_id,branch_name,c);
            case 1:
                //Fragment for Ios Tab

                return new Android("119.157.128.3:2030","Bahadurabad",branch_id,branch_name,c);
            case 2:
                //Fragment for Windows Tab
                return new Android("119.157.128.3:2000","Nazimabad",branch_id,branch_name,c);
        }
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }
   public void  putExtra(ArrayList<String> branch_id,ArrayList<String> branch_name,Context a)
   {
      this.branch_id=branch_id;
       this.branch_name=branch_name;
       c=a;
   }

}