package com.bignerdranch.android.mytrainer;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class CustomerPictureActivity extends FragmentActivity {

    public class Logout extends FragmentActivity {

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu_options, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.logout_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
}
