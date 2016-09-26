package com.bignerdranch.android.mytrainer;

import android.support.v4.app.FragmentActivity;

public class CustomerDbSchema {
    public static final class CustomerTable {
        public static final String Name = "newcustomer";

        public static final class Cols {
            public static final String UUID = "UUID";
            public static final String FullName = "full_name";
            public static final String Email = "email";
            public static final String Address = "address";
            public static final String PhoneNumber = "phone_number";

        }
    }


}
