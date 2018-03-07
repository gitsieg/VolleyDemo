package xyz.gitsieg.volleydemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String STATUS = "Status: ";
    String STRINGURL = "https://register.geonorge.no/api/subregister/sosi-kodelister/kartverket/fylkesnummer-alle.json?";
    String BILDE_URL = "https://lijie3jdjooecodwjcoo.files.wordpress.com/2017/01/manedens-bilde-april-09.jpg";
    String outStatus;
    TextView tw, status;
    ImageView imgView;
    Button btnString, btnJson, btnImage;

    RequestHandler rqHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RequestHandler som håndterer forespørsler
        rqHandler = new RequestHandler(this);

        // Henter views som er aktuelle
        status = findViewById(R.id.status);
        tw = findViewById(R.id.textView);
        imgView = findViewById(R.id.imageView);
        btnString = findViewById(R.id.btnString);
        btnJson = findViewById(R.id.btnJson);
        btnImage = findViewById(R.id.btnImage);

        // Legger på lyttere
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnImage.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.btnString:
                setImageViewDisabled();
                stringRequestClicked();
                break;
            case R.id.btnJson:
                setImageViewDisabled();
                jsonRequestClicked();
                break;
            case R.id.btnImage:
                setTextViewDisabled();
                imageRequestClicked();
                break;
            default:
                Log.e(TAG, "onClick(View view) -> default case");
        }
    }

    private void setTextViewDisabled() {
        tw.setVisibility(View.INVISIBLE);
        imgView.setVisibility(View.VISIBLE);
    }

    private void setImageViewDisabled() {
        imgView.setVisibility(View.INVISIBLE);
        tw.setVisibility(View.VISIBLE);
    }

    void stringRequestClicked() {
        rqHandler.sendStringRequest(STRINGURL, new RequestHandler.StringResponseHandler() {
            @Override
            public void onStringResponse(String response) {
                tw.setText(response);
            }

            @Override
            public void onError(VolleyError error) {
                outStatus = STATUS.concat(getString(R.string.string_error));
                status.setText(outStatus);
            }
        });
    }

    void imageRequestClicked() {

        rqHandler.sendImageRequest(
                BILDE_URL,
                100,
                100,
                new RequestHandler.ImageResponseHandler() {
                    @Override
                    public void onImageResponse(Bitmap bitmap) {
                        imgView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    }

                    @Override
                    public void onError(VolleyError error) {
                        outStatus = STATUS.concat(getString(R.string.image_error));
                        status.setText(outStatus);
                    }
                });
    }

    void jsonRequestClicked() {
        rqHandler.sendJsonRequest(STRINGURL, new RequestHandler.JSONResponseHandler() {
            @Override
            public void onJSONResponse(JSONObject response) {
                try {
                    tw.setText(response.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                outStatus = STATUS + getString(R.string.json_error);
                status.setText(outStatus);
                error.printStackTrace();
            }
        });
    }
}
