package com.example.android.myscannerapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.android.myscannerapp.R.id.url;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "likki@gmail.com", "140504"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    EditText mEmailView;
    EditText mPasswordView;
    TextView mSignUpView;
    // private View mProgressView;
    //private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.inputEmail);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.inputPin);
        mSignUpView = (TextView) findViewById(R.id.signup);
        mSignUpView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public void onLoginClick(View v) {
        final String emailId = mEmailView.getText().toString();
        final String pwd = mPasswordView.getText().toString();
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        //String retrivedPin = SignUpHelper.retrieveData(emailId);
        String url = "http://192.168.15.49:8080/signIn";
        String tag_json_obj = "json_obj_req";



        /*if (!emailId.equals("") ) {
            if (retrivedPin!=null) {
                if (retrivedPin.equals(pwd)) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG);
                }
            }
            else{
                Toast.makeText(this,"User does not exist",Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
        }*/


        Map<String, String> params = new HashMap<String, String>();
        params.put("username", emailId);
        params.put("pin", String.valueOf(pwd));
        params.put("firstName", "likitha");
        params.put("lastName", "n");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(AppController.class.getSimpleName(), response.toString());
                        try {
                            String msg = response.get("description").toString();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                            if(msg.equals("login successful")){
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                VolleyLog.d(AppController.class.getSimpleName(), "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", emailId);
                params.put("pin", String.valueOf(pwd));
                params.put("firstName", "likitha");
                params.put("lastName", "n");
                return params;

            }

            /**
                          * Passing some request headers
                          * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
   /* private void saveUser(String signUpEmail, String signUpPwd) {


        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        //values.put(SignUpHelper.EMAIL_ID,signUpEmail);
        //values.put(SignUpHelper.PIN,signUpPwd);
        db.insert(SignUpHelper.TABLE_ACCOUNT,null,values);
        Log.d("Email Id:SaveUser:",signUpEmail);
        Log.d("PIN:SaveUser:",signUpPwd);
    }

}*/
