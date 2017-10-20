package com.dnkilic.bong.caller;

import java.util.ArrayList;

public class ContactPerson {
    private ArrayList<String> _phoneNumbers = new ArrayList<>();
    private ArrayList<Integer> _types = new ArrayList<>();
    private ArrayList<String> _originalNames = new ArrayList<>();
    private ArrayList<Boolean> _isPrimary = new ArrayList<>();

    public ContactPerson(String phoneNumber, String originalName, int type, boolean isPrimary) {
        _phoneNumbers.add(phoneNumber);
        _originalNames.add(originalName);
        _types.add(type);
        _isPrimary.add(isPrimary);
    }

    public ArrayList<String> getPhoneNumbers() {
        return _phoneNumbers;
    }


    public void addPhoneNumber(String phoneNumber, int type, String originalName, boolean isPrimary) {
        if (!_phoneNumbers.contains(phoneNumber)) {
            _phoneNumbers.add(phoneNumber);
            _types.add(type);
            _originalNames.add(originalName);
        }
    }

    public ArrayList<String> getOriginalNames() {
        return _originalNames;
    }

    public int getType(int index) {
        return _types.get(index);
    }
}
