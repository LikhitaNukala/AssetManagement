package com.example.android.myscannerapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText signUpEmail;
    EditText signUpPwd;
    EditText signUpConfirmPwd;
    // SignUpHelper helper=new SignUpHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void onSubmitClick(View v) {
        signUpEmail = (EditText) findViewById(R.id.inputSignUpEmail);
        signUpPwd = (EditText) findViewById(R.id.inputSignUpPin);
        signUpConfirmPwd = (EditText) findViewById(R.id.inputConfirmPin);
        final String emailId = signUpEmail.getText().toString();
        final String pwdString = signUpPwd.getText().toString();
        final int pwd = Integer.parseInt(pwdString);
        String cPwd = signUpConfirmPwd.getText().toString();
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url = "http://192.168.15.49:8080/signUp";
        String tag_json_obj = "json_obj_req";
        if (emailId != "") {
            if (!pwdString.equals(cPwd)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            } else {
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
                                    Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
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
                     *              * Passing some request headers
                     *              *
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }

                };
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


                Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            Toast.makeText(this, "Fields cannot be left empty", Toast.LENGTH_LONG).show();
        }


    }
}
