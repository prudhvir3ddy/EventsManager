package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText name,phone,email,rollno,passwd,cpasswd;
Spinner branch;
Button button;
String branchstr;
LoadToast loadToast;
Connection connection;
String[] branches={"CSE","ECE","EEE","IT","MECH","CIVIL","PHE","BME"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        connection=new Connection(this);
        loadToast=new LoadToast(this);
        loadToast.setText("loading");
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.Phone);
        email=(EditText)findViewById(R.id.Email);
        rollno=(EditText)findViewById(R.id.rollno);
        branch=(Spinner) findViewById(R.id.branch);
        button=(Button)findViewById(R.id.registerbtn);
        passwd=(EditText)findViewById(R.id.password);
        cpasswd=(EditText)findViewById(R.id.cpassword);
        branch.setOnItemSelectedListener(this);


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,branches);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        branch.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestr = name.getText().toString();
                String phonestr = phone.getText().toString();
                String emailstr = email.getText().toString();
                String rollnostr = rollno.getText().toString();
                String passwdstr=passwd.getText().toString();
                String cpasswdstr=passwd.getText().toString();

                if (namevalidator(namestr)) {
                    Log.d("name", "" + namestr);
                    if (phonevalidate(phonestr)) {
                        Log.d("phone", "" + phonestr);
                        if (isValidEmail(emailstr)) {
                            Log.d("email", "" + emailstr);
                            if (rollnovalidator(rollnostr)) {
                                Log.d("rollno:", "" + rollnostr);
                                if (passwdstr.equals(cpasswdstr)) {
                                    Log.d("passwd", "" + passwdstr + "" + cpasswdstr);
                                    if (branchstr != null) {
                                        Log.d("branch", "" + branchstr);
                                        Boolean checkinternet = (connection.isInternet());
                                        if (checkinternet) {
                                            Log.d("TAG", "online startted");
                                            stdregister(namestr, phonestr, emailstr, rollnostr,passwdstr, branchstr);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "select your branch", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),"both passwords are not equal",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"enter valid rollnumber",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                            Toast.makeText(getApplicationContext(),"invalid email",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"invalid phone number",Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"invalid user name",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void stdregister(String namestr, String phonestr, String emailstr, String rollnostr,String passwdstr,String branchstr) {
        AndroidNetworking.initialize(getApplicationContext());
        Log.d("url",""+Urls.studentreg);
        loadToast.show();
        AndroidNetworking.post(Urls.studentreg)
                .addBodyParameter("name",namestr)
                .addBodyParameter("phone",phonestr)
                .addBodyParameter("email",emailstr)
                .addBodyParameter("rollno",rollnostr)
                .addBodyParameter("branch",branchstr)
                .addBodyParameter("passwd",passwdstr)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        loadToast.success();
                        Log.d("respone",""+response);
                        int j=response.length();
                        for(int i=0;i<j;i++)
                        {
                            JSONObject jsonObject;
                            try {
                                jsonObject=response.getJSONObject(i);

                                if(!jsonObject.has("InsertionError"))
                                {
                                    if(!jsonObject.has("exists")) {
                                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(StudentRegisterActivity.this,StudentLogin.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"rollnumber already exists",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                Log.d("exception",""+e);
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Log.d("error",""+anError);
                    }
                });
    }

    public  static boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean rollnovalidator(String rollno)
    {
        Pattern pattern;
        Matcher matcher;
        final String PATTERN="^[0-9a-zA-Z]{10}$";
        pattern=Pattern.compile(PATTERN);
        matcher=pattern.matcher(rollno);
        return matcher.matches();
    }
    public boolean namevalidator(String name){
        Pattern pattern;
        Matcher matcher;
        final String USERNAME_PATTERN = "^[a-zA-Z ]{2,25}$";
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public boolean phonevalidate(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       branchstr=branches[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
