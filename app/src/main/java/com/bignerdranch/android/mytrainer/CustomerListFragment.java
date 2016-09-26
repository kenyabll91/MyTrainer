package com.bignerdranch.android.mytrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomerListFragment extends Fragment {

    private RecyclerView mCustomerRecyclerView;
    private CustomerAdapter mAdapter;
    private static final String DIALOG_LOGOUT = "DialogLogout";

    private static final int REQUEST_LOGOUT = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mCustomerRecyclerView = (RecyclerView) view
                .findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        updateUI();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_LOGOUT) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_customer:
                Customer customer = new Customer();
                CustomerLab.get(getActivity()).addCustomer(customer);
                Intent intent = NewCustomerActivty
                        .newIntent(getActivity(), customer.getId());
                startActivity(intent);
                return true;
            case R.id.logout_settings:
                FragmentManager manager = getFragmentManager();
                Logout logout = new Logout();
                logout.setTargetFragment(CustomerListFragment.this, REQUEST_LOGOUT);
                logout.show(manager, DIALOG_LOGOUT);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        CustomerLab customerLab = CustomerLab.get(getActivity());
        List<Customer> customers = customerLab.getNewCustomerList();

        if (mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCustomerRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCustomers(customers);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CustomerHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        private Customer mCustomer;

        public TextView mTitleTextView;

        public CustomerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


            mTitleTextView = (TextView) itemView;
        }

        public void bindCustomer(Customer customer) {
            mCustomer = customer;
            mTitleTextView.setText(mCustomer.getFullNmae());
        }

        @Override
        public void onClick(View v) {
            Intent intent = NewCustomerActivty.newIntent(getActivity(), mCustomer.getId());
            startActivity(intent);
        }
    }

    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

        private List<Customer> mCustomers;

        public CustomerAdapter(List<Customer> customers) {
            mCustomers = customers;
        }

        @Override
        public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CustomerHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomerHolder holder, int position) {
            Customer customer = mCustomers.get(position);
            holder.bindCustomer(customer);

        }

        @Override
        public int getItemCount() {
            return mCustomers.size();
        }

        public void setCustomers(List<Customer> customers) {
            mCustomers = customers;
        }
    }
}
