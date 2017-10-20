package com.dnkilic.bong.caller;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ContactSaver {
    private Activity _theAct;
    private Map<String, ContactPerson> _numberMap;
    private Map<String, ArrayList<String>> _fullNameMap;
    private ArrayList<ContactPerson> _numbers;
    private HashMap<String, String> _allNumbersMap;

    public ContactSaver(Activity activity) {
        _theAct = activity;
        _numbers = new ArrayList<>();
        _numberMap = new HashMap<>();
        _allNumbersMap = new HashMap<>();
        _fullNameMap = new HashMap<>();
    }

    private static String modify(String str) {
        ArrayList<Character> TurkishCharacters = new ArrayList<>();
        TurkishCharacters.add('a');
        TurkishCharacters.add('b');
        TurkishCharacters.add('c');
        TurkishCharacters.add('ç');
        TurkishCharacters.add('d');
        TurkishCharacters.add('e');
        TurkishCharacters.add('f');
        TurkishCharacters.add('g');
        TurkishCharacters.add('ğ');
        TurkishCharacters.add('h');
        TurkishCharacters.add('ı');
        TurkishCharacters.add('i');
        TurkishCharacters.add('j');
        TurkishCharacters.add('k');
        TurkishCharacters.add('l');
        TurkishCharacters.add('m');
        TurkishCharacters.add('n');
        TurkishCharacters.add('o');
        TurkishCharacters.add('ö');
        TurkishCharacters.add('p');
        TurkishCharacters.add('r');
        TurkishCharacters.add('s');
        TurkishCharacters.add('ş');
        TurkishCharacters.add('t');
        TurkishCharacters.add('u');
        TurkishCharacters.add('ü');
        TurkishCharacters.add('v');
        TurkishCharacters.add('y');
        TurkishCharacters.add('z');
        TurkishCharacters.add(' ');

        int len = str.length();
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!TurkishCharacters.contains(c)) {
                str = str.replace(c, ' ');
            }
        }
        return str.trim();
    }

    public void generateContactMap() {
        try {
            String fullname, originalFullName, phoneNumber;
            Cursor phones = _theAct.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                originalFullName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                fullname = ReplaceLetters(originalFullName);
                fullname = fullname.trim();
                if (isNotAnEmptyName(fullname)) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
                    boolean isPrimary = false;
                    if (phones.getInt(phones.getColumnIndex(Phone.IS_SUPER_PRIMARY)) != 0) {
                        isPrimary = true;
                    }

                    putPersonToMap(type, fullname, originalFullName, phoneNumber.replaceAll("\\s+", ""), isPrimary);

                    divideNamesAndSurnames(fullname);
                }
            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putPersonToMap(int type, String name, String originalName, String phoneNumber, boolean isPrimary) {

        name = toLatina(name);

        if (_numberMap.containsKey(name)) {
            _numberMap.get(name).addPhoneNumber(phoneNumber, type, originalName, isPrimary);
        } else {
            _numberMap.put(name, new ContactPerson(phoneNumber, originalName, type, isPrimary));
        }

        _allNumbersMap.put(phoneNumber, originalName);
    }

    private boolean isNotAnEmptyName(String name) {
        return !(name.equals("") || name.equals(null));
    }

    private String toLatina(String name) {
        name = name.replace("ş", "s");
        name = name.replace("ü", "u");
        name = name.replace("ö", "o");
        name = name.replace("ı", "i");
        name = name.replace("ç", "c");
        name = name.replace("ğ", "g");
        name = name.replace("İ", "I");
        name = name.replace("Ü", "U");
        name = name.replace("Ö", "O");
        name = name.replace("Ş", "S");
        name = name.replace("Ç", "C");
        name = name.replace("Ğ", "G");

        return name;
    }

    private String ReplaceLetters(String oldStr) {
        String newStr;
        newStr = oldStr.replace("$", "ş").replace("ß", "b").replace("Σ", "e").replace("£", "l").replace("¥", "y");
        newStr = newStr.replace("µ", "m").replace("Q", "k").replace("q", "k").replace("W", "v").replace("w", "v");
        newStr = newStr.replace("@", "a").replace("|", "ı").replace("§", "s").replace("®", "r").replace("†", "t");
        newStr = newStr.replace("X", "ks").replace("x", "ks").replace("Ø", "o").replace("ø", "o").replace(",(", " ");
        newStr = newStr.replace("0", " sıfır").replace("1", " bir").replace("2", " iki").replace("3", " üç").replace("4", " dört");
        newStr = newStr.replace("5", " beş").replace("6", " altı").replace("7", " yedi").replace("8", " sekiz").replace("9", " dokuz");
        newStr = newStr.replace("α", "a").replace("™", "TM").replace("©", "c").replace("€", "e");

        newStr = convertToLowerCase(newStr);

        return modify(newStr);
    }

    private String convertToLowerCase(String str) {
        try {
            return str.replaceAll("İ", "I").toLowerCase((Locale.US));
        } catch (Exception e) {
            return str;
        }
    }

    private void divideNamesAndSurnames(String fullName) {
        String name;
        Scanner scan = new Scanner(fullName);

        while (scan.hasNext()) {
            name = scan.next();

            if (name.length() > 1) {
                if (_fullNameMap.containsKey(name)) {
                    if (!_fullNameMap.get(name).contains(fullName)) {
                        _fullNameMap.get(name).add(fullName);
                    }
                } else {
                    ArrayList<String> fullNames = new ArrayList<>();
                    fullNames.add(fullName);
                    _fullNameMap.put(name, fullNames);
                }
            }
        }
        scan.close();

        if (!_fullNameMap.containsKey(fullName)) {
            ArrayList<String> fullNames = new ArrayList<>();
            fullNames.add(fullName);
            _fullNameMap.put(fullName, fullNames);
        }
    }

    public Map<String, ContactPerson> getNumberMap() {
        return _numberMap;
    }

    public Map<String, ArrayList<String>> getNameSurnameMap() {
        return _fullNameMap;
    }

    public ArrayList<ContactPerson> searchFromContacts(String name) {
        if (_fullNameMap.containsKey(name)) {
            ArrayList<String> manyFullNames = _fullNameMap.get(name);
            for (String fullName : manyFullNames) {
                _numbers.add(_numberMap.get(fullName));
            }
        }
        return _numbers;
    }

    public ArrayList<ContactPerson> newSearchAlgorithm(String name) {

        ArrayList<ContactPerson> contacts = new ArrayList<>();

        name = toLatina(name);

        if (_numberMap.containsKey(name)) {
            ContactPerson manyFullNames = _numberMap.get(name);
            contacts.add(manyFullNames);
        } else {
            for (Entry<String, ContactPerson> entry : _numberMap.entrySet()) {
                if (entry.getKey().contains(name)) {
                    contacts.add(entry.getValue());
                }
            }

        }

        return contacts;
    }

    public String searchNumberFromContacts(String number) {
        for (Entry<String, String> entry : _allNumbersMap.entrySet()) {
            if (number.replaceAll("\\s+", "").contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return number;
    }
}
