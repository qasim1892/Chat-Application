package com.example.har.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;




import java.util.ArrayList;
import java.util.Iterator;

import static android.R.attr.data;


public class Users extends AppCompatActivity {


    ListView usersList;
    TextView noUsersText;

    ProgressDialog pd;

    ArrayList<String> al = new ArrayList<>();

    int totalUsers = 0;
    String number;
    String actualname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Intent mintent = getIntent();
        number = mintent.getExtras().getString("number");
        actualname = mintent.getExtras().getString("name");


        Toast.makeText(this, "the value of number = " + number, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "the actual name of the user is = " + actualname, Toast.LENGTH_SHORT).show();

        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://finalpro-68488.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

      /*  usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });*/

    }


    public void doOnSuccess(String s) {


        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            //String key = "";

            if(obj.getJSONObject(UserDetails.username).getString("number").equals(number)) {
                pd.dismiss();
                startActivity(new Intent(Users.this, Chat.class));
                finish();
            }


           /* while(i.hasNext()){
                key = i.next().toString();

                UserDetails.loopuser = key;

                if(obj.getJSONObject(UserDetails.loopuser).getString("number").equals(number)) {
                    startActivity(new Intent(Users.this, Chat.class));
                }


            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }



       /* if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }*/





    }
}


/*

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.getRoot().orderByChild("number").equalTo(2);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                //Do something with the individual node here`enter code here`

                al.add(String.valueOf(dataSnapshot));
                totalUsers++;
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }


            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }


        pd.dismiss();
*/



/*
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.number)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }*/





       /*
       *  DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = mFirebaseDatabaseReference.child("users").orderByChild("number").equalTo(""+number);
        ValueEventListener valueEventListener = null;
        query.addValueEventListener((com.google.firebase.database.ValueEventListener) valueEventListener);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //TODO get the data here
                    al.add(String.valueOf(dataSnapshot));
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        };

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();*/

/*   DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

       reference.orderByChild("number").equalTo(2).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    // dataSnapshot is the "user" node with all children with number 2
                                  al.add(String.valueOf(snapshot));
                                  totalUsers++;
                }


            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/


/* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference();


        db.child("users").orderByChild("number").equalTo("2").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        UserDetails number = snapshot.getValue(UserDetails.class);
                        number.setKey(snapshot.getKey());
                        al.add(String.valueOf(number));
                        totalUsers++;

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            });*/