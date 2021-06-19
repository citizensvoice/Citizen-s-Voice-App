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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public final static String API_KEY_FROM_DEV_PORTAL = "ceaa2a038af2c038c2060fb8048451952364e1f4365124e0e7fc700951bc7c29";
    Button airtime, chimoney;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airtime = findViewById(R.id.ar);
        chimoney = findViewById(R.id.ch);
       // new MyTask().execute();
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        email = findViewById(R.id.editTextTextEmailAddress);
        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No Money", Toast.LENGTH_LONG);
            }
        });

        chimoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            String val = getLocalAmt(client, "20");
                            send_chimoney(client, val, email.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    public void send_chimoney(OkHttpClient client, String amt, String email) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"chimoneys\": [\n        {\n            \"email\": \""+email+"\",\n            \"valueInUSD\": "+amt+"\n        }");
        Request request = new Request.Builder()
                .url("https://chimoney.io/api/v0.1/payouts/chimoney")
               .method("POST", body)
                .addHeader("X-API-Key", API_KEY_FROM_DEV_PORTAL)
                .build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        //Toast.makeText(MainActivity.this, "This"+responseData, Toast.LENGTH_LONG);
        JSONObject json = new JSONObject(responseData);
        JSONObject json1 = json.getJSONObject("data");
        JSONObject json2 = json1.getJSONObject("data");
        String ref = json2.getString("chiRef"); //"418e7360-35c5-496a-b2b2-3087ba4d2612";//
        String url = "https://chimoney.io/redeem?chi="+ref;
       // sendEmail(url, email);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
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