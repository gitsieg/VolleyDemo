package xyz.gitsieg.volleydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by gitsieg on 06.03.18.
 */

class RequestHandler {

    private final RequestQueue queue;

    RequestHandler(@NonNull Context c) {
        this.queue = Volley.newRequestQueue(c);
    }

    void sendStringRequest(String url, final StringResponseHandler handler) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onStringResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onError(error);
                    }
                }
        );
        queue.add(request);
    }

    void sendImageRequest(String url, int maxWidth, int maxHeight, final ImageResponseHandler handler) {
        ImageRequest request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        handler.onImageResponse(response);
                    }
                },
                maxWidth,
                maxHeight,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onError(error);
                    }
                });
        queue.add(request);
    }

    void sendJsonRequest(String url, final JSONResponseHandler handler) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onJSONResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onError(error);
                    }
                }
        );
        queue.add(request);
    }

    public interface StringResponseHandler {
        void onStringResponse(String response);
        void onError(VolleyError error);
    }

    public interface ImageResponseHandler {
        void onImageResponse(Bitmap bitmap);
        void onError(VolleyError error);
    }

    public interface JSONResponseHandler {
        void onJSONResponse(JSONObject response);
        void onError(VolleyError error);
    }
}
