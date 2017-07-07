package com.example.android.myscannerapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class SIMActivity extends android.support.v4.app.Fragment {
    Context mContext;
    final String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;
    ListView lv;
    TextView tv;


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_simcard_details, container, false);
        lv = (ListView) v.findViewById(R.id.sim_card_list);

        //List<SimDetailsList> res=new ArrayList<>();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        getList();

       final String imei1 = getOutput(mContext, "getDeviceId", 0);
        final String operator1 = getOutput(mContext, "getCarrierName", 0);
        final String networkType1 = getOutput(mContext, "getDataNetworkType", 0);
        final String simState1 = getOutput(mContext, "getSimState", 0);
        String dataState1 = getOutput(mContext, "getDataState", 0);
        final String networkRoaming1 = getOutput(mContext, "isNetworkRoaming", 0);
        Log.d("SIM1:", imei1 + operator1 + networkType1 + simState1 + dataState1 + networkRoaming1);
        //sim1Details.setText("SIM1: IMEI:  "+imei1+"\n Operator:  "+operator1+"\n Network Type: "+networkType1+"\n SIM state:"+simState1+"\n Data State:"+dataState1+"\n Roaming:"+networkRoaming1);
        String imei2 = getOutput(mContext, "getDeviceId", 1);
        String operator2 = getOutput(mContext, "getCarrierName", 1);
        String networkType2 = getOutput(mContext, "getDataNetworkType", 1);
        String simState2 = getOutput(mContext, "getSimState", 1);
        String dataState2 = getOutput(mContext, "getDataState", 1);
        String networkRoaming2 = getOutput(mContext, "isNetworkRoaming", 1);

        int i = 0;
        final String url = "http://192.168.15.49:8080/simCardDetails";


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Sim sim1 = new Sim();

                List<SimDetails> sim1DetailsList = new ArrayList<>();
                SimDetails sim1Details = new SimDetails();
                Gson gson = new Gson();
                sim1.setAssetId("IM-PHN-001");
                sim1Details.setDeviceId(imei1);

                sim1Details.setCarrierName(operator1);
                sim1Details.setDataNetworkType(networkType1);
                sim1Details.setSimState(simState1);
                sim1Details.setNetworkRoaming(networkRoaming1);

                sim1DetailsList.add(sim1Details);
                sim1.setSimDetailsList(sim1DetailsList);

                String sim1Json = gson.toJson(sim1);
                Log.d("Simjson", sim1Json);


                try

                {
                    JsonObjectRequest postJsonObjReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(sim1Json),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.d(AppController.class.getSimpleName(), response.toString());
                                    try {
                                        String msg = response.get("description").toString();
                                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
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
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }

                    };

                    AppController.getInstance().addToRequestQueue(postJsonObjReq, tag_json_obj);
                } catch (
                        JSONException e)

                {
                    e.printStackTrace();
                }
                int i = 0;
                Log.d("Execution", String.valueOf(i++) + "th time");
            }
        }, 0, 1000 * 60 * 60 * 24);


        return v;


    }

    public void getList() {
        String get_url = "http://192.168.15.49:8080/simDetails/IM-PHN-001";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, get_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("Get call description", response.get("description").toString());
                    if (response.get("description").toString().equals("success")) {
                        Log.d("get", response.get("result").toString());
                        Object sim1=response.get("result");

                        Sim sim = new Gson().fromJson(response.get("result").toString(), Sim.class);

                        //Log.d()
                        //for(int i=0;i<response.get("result").size(),)

                        SimListAdapter simDetailsAdapter = new SimListAdapter(mContext, sim.getSimDetailsList());
                        lv.setAdapter(simDetailsAdapter);
                    } else
                        Log.d("get", "get call failed!!");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GET error", "Error" + error.getMessage());
                pDialog.hide();

            }


        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private void perform(View v) {
        ListView lv = (ListView) v.findViewById(R.id.sim_card_list);
        List<SimDetailsList> res = new ArrayList<>();


    }

    private static String getOutput(Context context, String methodName, int slotId) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        String reflectionMethod = null;
        String output = null;
        try {
            telephonyClass = Class.forName(telephony.getClass().getName());
            for (Method method : telephonyClass.getMethods()) {
                String name = method.getName();
                if (name.contains(methodName)) {
                    Class<?>[] params = method.getParameterTypes();
                    if (params.length == 1 && params[0].getName().equals("int")) {
                        reflectionMethod = name;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (reflectionMethod != null) {
            try {
                output = getOpByReflection(telephony, reflectionMethod, slotId, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    private static String getOpByReflection(TelephonyManager telephony, String predictedMethodName, int slotID, boolean isPrivate) {


        String result = null;

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID;
            if (slotID != -1) {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName, parameter);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
                }
            } else {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName);
                }
            }

            Object ob_phone;
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            if (getSimID != null) {
                if (slotID != -1) {
                    ob_phone = getSimID.invoke(telephony, obParameter);
                } else {
                    ob_phone = getSimID.invoke(telephony);
                }

                if (ob_phone != null) {
                    result = ob_phone.toString();

                }
            }
        } catch (Exception e) {

            return null;
        }

        return result;
    }


}