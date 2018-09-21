package com.mobitechs.woodsnipe.sessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mobitechs.woodsnipe.Login;
import com.mobitechs.woodsnipe.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "Preference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_USERID = "userId";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEPTTYPE = "type";
    public static final String KEY_ATTENDANCEID = "attendanceId";
    public static final String KEY_SCHOOLID = "schoolId";
    public static final String KEY_PUNCHIN_TIME = "punchInTime";
    public static final String KEY_PUNCHOUT_TIME = "punchOutTime";
    public static final String KEY_SCHOOL_CHECKINID = "schoolCheckInId";

    String userType;

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String saveUserId, String userFullName,  String deptType) {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

        editor.putString(KEY_USERID, saveUserId);
        editor.putString(KEY_NAME, userFullName);
        editor.putString(KEY_DEPTTYPE, deptType);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_DEPTTYPE, pref.getString(KEY_DEPTTYPE, null));

        // return user
        return user;
    }



    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        } else {


            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void SetCheckinId(String attendanceId,String punchInTime,String punchOutTime) {

        editor.putString(KEY_ATTENDANCEID, attendanceId);
        editor.putString(KEY_PUNCHIN_TIME, punchInTime);
        editor.putString(KEY_PUNCHOUT_TIME, punchOutTime);

        editor.commit();
    }

    public HashMap<String, String> GetCheckinId() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ATTENDANCEID, pref.getString(KEY_ATTENDANCEID, null));
        user.put(KEY_PUNCHIN_TIME, pref.getString(KEY_PUNCHIN_TIME, null));
        user.put(KEY_PUNCHOUT_TIME, pref.getString(KEY_PUNCHOUT_TIME, null));

        // return user
        return user;
    }

    public void SetSchoolCheckInId(String schoolId, String schoolCheckInId) {
        editor.putString(KEY_SCHOOLID, schoolId);
        editor.putString(KEY_SCHOOL_CHECKINID, schoolCheckInId);

        editor.commit();
    }

    public HashMap<String, String> getSchoolCheckInId() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_SCHOOLID, pref.getString(KEY_SCHOOLID, null));
        user.put(KEY_SCHOOL_CHECKINID, pref.getString(KEY_SCHOOL_CHECKINID, null));


        // return user
        return user;
    }
}