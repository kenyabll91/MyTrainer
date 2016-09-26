package com.bignerdranch.android.mytrainer;


import java.util.UUID;

public class Customer {
    private UUID mId;
    private String mFullNmae;
    private String mEmail;
    private String mAddress;
    private String mPhoneNumber;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFullNmae() {
        return mFullNmae;
    }

    public void setFullNmae(String fullNmae) {
        mFullNmae = fullNmae;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public UUID getId() {
        return mId;
    }

    public Customer() {
        this(UUID.randomUUID());
    }

    public Customer(UUID id) {
        mId = id;
    }
}
