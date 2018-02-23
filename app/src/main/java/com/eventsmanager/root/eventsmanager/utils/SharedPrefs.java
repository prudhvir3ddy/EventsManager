package com.eventsmanager.root.eventsmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 18/1/18.
 */

public class SharedPrefs {

    Context context;
    public static final String myprefs = "myprefs";
    public static final String LogedInUserName = "UserName";
    public static final String LogedInEmail = "Email";
    public static final String LogedInBranch = "branch";
    public static final String LogedInPhone="phone";
    public static final String LogedInRollno="rollno";
    public static final String firstopen=null;
    public static final String checklog="null";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public SharedPrefs(Context context)
    {
        this.context=context;
        sharedpreferences = context.getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }
    public void setlogin()
    {
//        editor.putString(checklog,"true");
        editor.putString(checklog,"true");
        editor.commit();
    }
    public void clearlogin()
    {
        editor.putString(checklog,null);
        editor.commit();
    }
    public String getlogin(){
        return sharedpreferences.getString(checklog,null);
    }
    public String getopened(){
        return sharedpreferences.getString(firstopen,null);
    }
    public void setopened(){

        editor.putString(firstopen,"true");
        editor.commit();
    }
    public void setRollno(String rollno)
    {
        editor.putString(LogedInRollno,rollno);
        editor.commit();
    }
    public String getLogedInUserName()
    {
        return sharedpreferences.getString(LogedInUserName,null);
    }
    public String getLogedInEmail()
    {
        return sharedpreferences.getString(LogedInEmail,null);
    }
    public String getLogedInBranch()
    {
        return sharedpreferences.getString(LogedInBranch,null);
    }
    public String getLogedInPhone()
    {
        return sharedpreferences.getString(LogedInPhone,null);
    }
    public String getRollno()
    {
        return sharedpreferences.getString(LogedInRollno,null);
    }
    public void saveprefs(String UserName,String Email,String Phone,String Branch){
        editor.putString(LogedInUserName, UserName);
        editor.putString(LogedInEmail, Email);
        editor.putString(LogedInPhone,Phone);
        editor.putString(LogedInBranch,Branch);
        editor.commit();
    }
    public void clearprefs() {
        editor.putString(LogedInUserName, null);
        editor.putString(LogedInEmail, null);
        editor.putString(LogedInPhone,null);
        editor.putString(LogedInBranch,null);
        editor.putString(LogedInRollno,null);
        editor.commit();
    }

}
