package devrim.catak.eCommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import devrim.catak.eCommerce.database.Database;

public class kitap extends AppCompatActivity {

    ProgressDialog pDialog;
    RequestQueue requestQueue;
    StringRequest request;
    int id;
    String fiyat1,url;
    TextView adet,isim,yazar,fiyat,aciklama;
    Button sepeteEkle,satinAl;
    Menu menu;
    Database db ;
    private Menu optionsMenu;
    String a;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap);
        db=new Database(getApplicationContext());
        a="ds";
        Bundle gelenVeri = getIntent().getExtras();
        id = gelenVeri.getInt("id");

        adet=findViewById(R.id.adet);
        yazar=findViewById(R.id.yazar);
        isim=findViewById(R.id.kitap);
        fiyat=findViewById(R.id.fiyat);
        sepeteEkle=findViewById(R.id.sepeteEkle);
        satinAl=findViewById(R.id.satinal);
        aciklama=findViewById(R.id.aciklama);
        image=findViewById(R.id.image);




        sepeteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(kitap.this,kitapDetay.class));*/
                db.kitapEkle(id,isim.getText().toString(),yazar.getText().toString(),fiyat1,url);
                menu.findItem(R.id.adet).setTitle("Sepet("+String.valueOf(db.getRowCount())+")");

            }
        });

        satinAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SatinAl().execute();
                request = new StringRequest(Request.Method.POST, "http://skar.xyz/alisveris/alisveris.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(),  jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                new SendRequest().execute();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id", String.valueOf(id));
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        pDialog = new ProgressDialog(kitap.this);
        pDialog.setMessage("Siparişiniz Tamamlanıyor...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        new SendRequest().execute();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sepet, menu);
        this.menu = menu;
        menu.findItem(R.id.adet).setTitle("Sepet("+String.valueOf(db.getRowCount())+")");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adet:
                startActivity(new Intent(kitap.this,sepet.class));
            return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                java.net.URL url = new URL("http://skar.xyz/alisveris/kitapGoruntule.php");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", id);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jObj = null;
            pDialog.cancel();
            try {
                jObj = new JSONObject(result);
                JSONArray jArr = jArr = jObj.getJSONArray("kitap");
                for (int i = 0; i < jArr.length(); i++) {

                    JSONObject obj = jArr.getJSONObject(i);
                    isim.setText(obj.getString("isim"));
                    yazar.setText(obj.getString("yazar"));
                    fiyat1=String.valueOf(obj.getInt("fiyat"));
                    url=obj.getString("url");
                    Picasso.get().load(url).into(image);
                    fiyat.setText(fiyat1+"₺");
                    aciklama.setText(obj.getString("aciklama"));
                    if (obj.getInt("adet")<1) {
                        sepeteEkle.setVisibility(View.INVISIBLE);
                        satinAl.setVisibility(View.INVISIBLE);
                        adet.setText("Stoklarımızda kalmamıştır.");
                    }
                    else
                        adet.setText("Bir günde kargoda.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
