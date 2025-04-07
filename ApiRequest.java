package com.example.pokedex;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiRequest {

    private static RequestQueue requestQueue;

    public ApiRequest(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void getJsonObject(String url, final ApiResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = (error.getMessage() != null) ? error.getMessage() : "Unknown error";
                        listener.onError(message);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public interface ApiResponseListener {
        void onSuccess(JSONObject response);
        void onError(String errorMessage);
    }
}

