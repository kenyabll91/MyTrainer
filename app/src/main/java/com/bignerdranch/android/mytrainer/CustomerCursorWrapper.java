package com.bignerdranch.android.mytrainer;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

public class CustomerCursorWrapper extends CursorWrapper{

    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Customer getCustomer() {
        String uuidString = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.UUID));
        String FullName = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.FullName));
        String Email = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.Email));
        String Address = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.Address));
        String PhoneNumber = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.PhoneNumber));

        Customer customer = new Customer(UUID.fromString(uuidString));
        customer.setFullNmae(FullName);
        customer.setEmail(Email);
        customer.setAddress(Address);
        customer.setPhoneNumber(PhoneNumber);

        return customer;
    }
}
