package com.bignerdranch.android.mytrainer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

public class NewCustomerActivty extends SingleFragmentActivity {

    private static final String EXTRA_CUSTOMER_ID =
            "com.bignerdranch.android.mytrainer.customer_id";

    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, NewCustomerActivty.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);
        return NewCustomerFragment.newInstance(customerId);
    }

}
