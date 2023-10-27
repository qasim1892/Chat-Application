package com.example.har.finalproject;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    Intent intent;

    Firebase reference;

    Button signin,signup,forget;
    EditText username,password;

    String user,pass,mail_r,date_r,month_r,year_r,country_r;

    EditText name,pass_r,mail,year;

    EditText for_pass,for_name,for_email;

    RadioButton male,female;
    RadioGroup R_gender;

    String gender;

    String f_name,f_mail,f_pass;

    int count=0;
    String number;

    String retirvename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Firebase.setAndroidContext(this);

        Intent mintent = getIntent();
        number = mintent.getExtras().getString("number");


        //for login
        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_pass);

        signin = (Button) findViewById(R.id.sing_in);
        signup = (Button) findViewById(R.id.register);
        forget = (Button) findViewById(R.id.forgetbnt);

        //for registeration
        name = (EditText) findViewById(R.id.name);
        pass_r = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.mail);
        year = (EditText) findViewById(R.id.year);

        //for forget password
        for_pass = (EditText) findViewById(R.id.f_pass);
        for_email = (EditText) findViewById(R.id.f_email);
        for_name = (EditText) findViewById(R.id.f_name);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        R_gender = (RadioGroup) findViewById(R.id.radioGender);

//--------------------------Country Spinner----------------------------

        final Spinner spinner_country = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
        spinner_country.setAdapter(adapter);

//------------------------Dates Spinner-------------------------------

        final Spinner spinner_dates = (Spinner) findViewById(R.id.date);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.dates_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_dates.setAdapter(adapter1);

//----------------------Months Spinner-------------------------------

        final Spinner spinner_months = (Spinner) findViewById(R.id.month);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.months_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_months.setAdapter(adapter2);
//----------------------Spinners Ends---------------------------------

//---------------------For Login Part Start---------------------------

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else {
                    String url = "https://finalpro-68488.firebaseio.com/users.json";

                    final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            reference = new Firebase("https://finalpro-68488.firebaseio.com/users");

                            if (s.equals("null")) {
                                Toast.makeText(Main2Activity.this, "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        Toast.makeText(Main2Activity.this, "user not found", Toast.LENGTH_LONG).show();
                                    } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                        UserDetails.username = user;
                                        UserDetails.password = pass;
                                        UserDetails.number   = number;

                                        retirvename = user;

                                        reference.child(user).child("number").setValue(number);

                                       // Toast.makeText(Main2Activity.this, "welcome to the Chat box " + user, Toast.LENGTH_LONG).show();

                                        intent = new Intent(Main2Activity.this, Chat.class);
                                        intent.putExtra("number", number);
                                        intent.putExtra("name", retirvename);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Main2Activity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                    rQueue.add(request);
                }

            }
        });

//--------------------------Login Part Ends here-------------------------------


//--------------------------Registeration Part starts here---------------------
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = name.getText().toString();
                pass = pass_r.getText().toString();
                mail_r = mail.getText().toString();
                year_r = year.getText().toString();

                country_r = spinner_country.getSelectedItem().toString();
                date_r = spinner_dates.getSelectedItem().toString();
                month_r = spinner_months.getSelectedItem().toString();

                if (user.equals("")) {
                    name.setError("can't be blank");
                } else if (pass.equals("")) {
                    pass_r.setError("can't be blank");
                } else if (!user.matches("[A-Za-z0-9]+")) {
                    name.setError("only alphabet or number allowed");
                } else if (user.length() < 5) {
                    name.setError("at least 5 characters long");
                } else if (pass.length() < 5) {
                    pass_r.setError("at least 5 characters long");
                } else if (mail_r.equals("")) {
                    mail.setError("can't be blank");
                } else if (year_r.equals("")) {
                    year.setError("can't be blank");
                } else if (R_gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Main2Activity.this, "Select your gender!!", Toast.LENGTH_LONG).show();
                } else {
                    if (male.isChecked()) {
                        gender = "male";
                        count = 1;
                    }
                    if (female.isChecked()) {
                        gender = "female";
                        count = 1;
                    }


                    if (count == 1) {
                        final String currentdate = "" + date_r + "-" + month_r + "-" + year_r;

                        final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
                        pd.setMessage("Loading...");
                        pd.show();

                        String url = "https://finalpro-68488.firebaseio.com/users.json";

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                reference = new Firebase("https://finalpro-68488.firebaseio.com/users");

                                if (s.equals("null")) {
                                    reference.child(user).child("password").setValue(pass);
                                    reference.child(user).child("email").setValue(mail_r);
                                    reference.child(user).child("date").setValue(currentdate);
                                    reference.child(user).child("gender").setValue(gender);
                                    reference.child(user).child("country").setValue(country_r);

                                    Toast.makeText(Main2Activity.this, "registration successful", Toast.LENGTH_LONG).show();
                                } else {
                                    try {
                                        JSONObject obj = new JSONObject(s);

                                        if (!obj.has(user)) {
                                            reference.child(user).child("password").setValue(pass);
                                            reference.child(user).child("email").setValue(mail_r);
                                            reference.child(user).child("date").setValue(currentdate);
                                            reference.child(user).child("gender").setValue(gender);
                                            reference.child(user).child("country").setValue(country_r);

                                            Toast.makeText(Main2Activity.this, "registration successful", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Main2Activity.this, "username already exists", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                pd.dismiss();
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("" + volleyError);
                                pd.dismiss();
                            }
                        });

                        RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                        rQueue.add(request);
                    }
                }
            }
        });


//----------------------------Forget Button Starts-------------------------------
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_name = for_name.getText().toString();
                f_mail = for_email.getText().toString();

                if (f_name.equals("")) {
                    for_name.setError("can't be blank");
                } else if (f_mail.equals("")) {
                    for_email.setError("can't be blank");
                } else {
                    String url = "https://finalpro-68488.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            reference = new Firebase("https://finalpro-68488.firebaseio.com/users");

                            if (s.equals("null")) {
                                Toast.makeText(Main2Activity.this, "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(f_name)) {
                                        Toast.makeText(Main2Activity.this, "user not found", Toast.LENGTH_LONG).show();
                                    } else if (obj.getJSONObject(f_name).getString("email").equals(f_mail)) {

                                        Toast.makeText(Main2Activity.this, "Enter new Password and press the button again " + f_name, Toast.LENGTH_LONG).show();

                                        for_pass.setAlpha(1);

                                        f_pass = for_pass.getText().toString();

                                        if (f_pass.equals("")) {
                                            for_pass.setError("can't be blank");
                                        } else if (f_pass.length() < 5) {
                                            for_pass.setError("at least 5 characters long");
                                        }

                                        reference.child(f_name).child("password").setValue(f_pass);

                                    } else {
                                        Toast.makeText(Main2Activity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Main2Activity.this);
                    rQueue.add(request);
                }

            }
        });


    }
}

