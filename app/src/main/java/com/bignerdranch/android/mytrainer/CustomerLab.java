package com.bignerdranch.android.mytrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerLab {
    private static CustomerLab sCustomerLab;

    public static CustomerLab get(Context context){
        if (sCustomerLab == null) {
            sCustomerLab = new CustomerLab(context);
        }
        return sCustomerLab;
    }

    private List<Customer> mNewCustomerList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CustomerLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext)
                .getWritableDatabase();
        mNewCustomerList = new  ArrayList<>();
    }

    public void addCustomer(Customer c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerDbSchema.CustomerTable.Name, null, values);

    }


    public List<Customer> getNewCustomerList() {
        List<Customer> customers = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomer(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return customers;
    }

    public Customer getCustomer(UUID id) {
        CustomerCursorWrapper cursor = queryCustomer(
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCustomer();
        } finally {
            cursor.close();
        }
    }

    public void updateCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.update(CustomerDbSchema.CustomerTable.Name, values,
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomerDbSchema.CustomerTable.Cols.UUID, customer.getId().toString());
        values.put(CustomerDbSchema.CustomerTable.Cols.FullName, customer.getFullNmae());
        values.put(CustomerDbSchema.CustomerTable.Cols.Email, customer.getEmail());
        values.put(CustomerDbSchema.CustomerTable.Cols.Address, customer.getAddress());
        values.put(CustomerDbSchema.CustomerTable.Cols.PhoneNumber, customer.getPhoneNumber());

        return values;
    }

    private CustomerCursorWrapper queryCustomer(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomerDbSchema.CustomerTable.Name,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CustomerCursorWrapper(cursor);
    }


}
