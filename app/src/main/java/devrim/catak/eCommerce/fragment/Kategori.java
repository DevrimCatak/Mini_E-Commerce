package devrim.catak.eCommerce.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import devrim.catak.eCommerce.R;
import devrim.catak.eCommerce.clases.kategori;
import devrim.catak.eCommerce.adapters.kategoriAdapter;
import devrim.catak.eCommerce.kitaplar;

public class Kategori extends Fragment {
    View view;
    RequestQueue requestQueue;
    StringRequest request;
    ProgressDialog pDialog;
    final List<kategori> kategoriler = new ArrayList<kategori>();
    ListView listemiz;
    kategoriAdapter adaptorumuz;

    public Kategori() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.f_kategori, container, false);

        listemiz = (ListView) view.findViewById(R.id.list);
        adaptorumuz = new kategoriAdapter(getActivity(),kategoriler);

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent ıntent=new Intent(getActivity(), kitaplar.class);///İntent ouşturup 2. activity'e gideceğini belirledik.
                ıntent.putExtra("kategori",kategoriler.get(position).getId());//Gönderilecek veriyi ve bir anahtar belirledik.
                startActivity(ıntent);//İntenti başlatarak yeni activityin başlamasını sağladık.

            }
        });

        requestQueue= Volley.newRequestQueue(view.getContext());

        pDialog = new ProgressDialog(view.getContext());
        pDialog.setMessage("Bilgileriniz Yükleniyor...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        new SendRequest().execute();

        return view;
    }

    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                java.net.URL url = new URL("http://skar.xyz/alisveris/kategori.php");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", "");

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
                JSONArray jArr = jArr = jObj.getJSONArray("kategori");
                for (int i = 0; i < jArr.length(); i++) {

                    JSONObject obj = jArr.getJSONObject(i);
                    kategoriler.add(new kategori(obj.getInt("id"), obj.getString("isim")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listemiz.setAdapter(adaptorumuz);

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
