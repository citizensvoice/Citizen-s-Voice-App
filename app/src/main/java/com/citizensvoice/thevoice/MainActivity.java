package com.citizensvoice.thevoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public final static String API_KEY_FROM_DEV_PORTAL = "5d26dbea29f5ca944e76a5f8d087e2914d6a7a94412f1effb3f0cb761440ce7e";
    Button airtime, chimoney;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airtime = findViewById(R.id.ar);
        chimoney = findViewById(R.id.ch);
        email = findViewById(R.id.editTextTextEmailAddress2);

        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No Money", Toast.LENGTH_LONG);
            }
        });

        chimoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send();
               Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            String val = getLocalAmt(client, "20");
                            send_chimoney(email.getText().toString(), val);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });

    }

    public String getLocalAmt(OkHttpClient client, String value) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url("https://chimoney.io/api/v0.1/info/local-amount-in-usd?originCurrency=NGN&amountInOriginCurrency="+value)
                .method("GET", null)
                .addHeader("X-API-Key", API_KEY_FROM_DEV_PORTAL)
                .build();

        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        JSONObject json = new JSONObject(responseData);
        JSONObject json1 = json.getJSONObject("data");
        String amt = json1.getString("amountInUSD");
        return amt;
    }


    public void send(){
        String url = "https://chimoney.io/redeem?chi=5675e9a0-961c-488e-b64e-62f4a3d9e34b";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void send_chimoney(String email, String amt) throws IOException, JSONException {
        JSONArray val = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("email", "akinremibunmi111@gmail.com");
        val.put(jsonObject1);
        jsonObject1 = new JSONObject();
        jsonObject1.put("value", "1");
        val.put(jsonObject1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chimoneys", val);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        //MediaType mediaType = MediaType.parse("text/plain");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, val.toString());
       // RequestBody body = RequestBody.create(mediaType, "{\n    \"chimoney\": [\n        {\n            \"email\": \""+email+"\",\n            \"valueInUSD\": "+amt+"\n        }");
        Request request = new Request.Builder()
                .url("https://chimoney.io/api/v0.1/payouts/chimoney")
                .method("POST", body)
                .addHeader("X-API-Key", API_KEY_FROM_DEV_PORTAL)
                .build();

        Response response = client.newCall(request).execute();
        Log.e("response ", "onResponse(): " + response);
        String responseData = response.body().toString();
        Log.e("response ", "onResponse(): " + responseData);
        JSONObject json = new JSONObject(responseData);
        JSONObject json1 = json.getJSONObject("data");
        JSONArray json2 = json1.getJSONArray("data");
        String url = json2.getString(-1);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }
    public void sendAirtime(OkHttpClient client, String amt, String phone_num){
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"airtimes\": [\n        {\n            \"countryToSend\": \"Nigeria\",\n            \"phoneNumber\": \""+phone_num+"\",\n            \"valueInUSD\": "+amt+"\n        }");
        Request request = new Request.Builder()
                .url("https://chimoney.io/api/v0.1/payouts/airtime")
                .method("POST", body)
                .addHeader("X-API-Key", API_KEY_FROM_DEV_PORTAL)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendEmail(String url, String to){
        String subject="You've got some chimoney from TheVoice";
        String message="You've been redemmed your points as chimoney. Click this link to redeem it.\n"+url;

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

}