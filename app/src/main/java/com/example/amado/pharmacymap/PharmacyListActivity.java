package com.example.amado.pharmacymap;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class PharmacyListActivity extends ActionBarActivity {

    private PharmacyListFragment mPharmacyListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);


        mPharmacyListFragment = (PharmacyListFragment)getFragmentManager().findFragmentById(R.id.container);
        if(mPharmacyListFragment== null){
            mPharmacyListFragment= new PharmacyListFragment();
            FragmentManager fm  = getFragmentManager();
            fm.beginTransaction().add(R.id.container, mPharmacyListFragment)
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
            setTitle(R.string.pharmacy_list);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pharmacy_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
