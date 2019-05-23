package devrim.catak.eCommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devrim.catak.eCommerce.adapters.sepetAdaptor;
import devrim.catak.eCommerce.clases.kitapClass;
import devrim.catak.eCommerce.database.Database;

public class sepet extends AppCompatActivity {

    final List<kitapClass> kitaplar = new ArrayList<kitapClass>();
    ListView listemiz;
    sepetAdaptor adaptorumuz;
    ArrayList<HashMap<String, String>> kitap_liste;
    Database db;
    Button satinAl;
    StringRequest request;
    RequestQueue requestQueue;
    int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);
        db = new Database(getApplicationContext());
        listemiz = (ListView) findViewById(R.id.list);
        adaptorumuz = new sepetAdaptor(this,kitaplar);

        satinAl=findViewById(R.id.satinAl);
        listele();
        if (kitap_liste.size()<1)
        {
            satinAl.setEnabled(false);
        }

        satinAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j=0;
                urunGetir2();
                for (int i = 0; i < kitap_liste.size(); i++) {
                    final int finalI = i;
                    final int finalI1 = i;
                    request = new StringRequest(Request.Method.POST, "http://skar.xyz/alisveris/alisveris.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.names().get(0).equals("success")) {
                                    db.kitapSatinAl(kitap_liste.get(finalI1).get("sipId"));
                                    adaptorumuz.refreshEvents(kitaplar);
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
                            hashMap.put("id", String.valueOf(kitap_liste.get(finalI).get("id")));
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                    j++;

                }
                //startActivity(new Intent(sepet.this,MainActivity.class));
            }
        });
    }

    public void listele()
    {
        db = new Database(getApplicationContext());
        kitap_liste = db.kitaplar();//kitap listesini alıyoruz
        if (kitap_liste.size() == 0) {//kitap listesi boşsa
            Toast.makeText(getApplicationContext(), "Henüz Kitap Eklenmemiş.\nYukarıdaki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < kitap_liste.size(); i++) {
                //kitaplar.add(new kitapClass(Integer.valueOf(kitap_liste.get(i).get("id")),Integer.valueOf(kitap_liste.get(i).get("sipId")), 0, Integer.valueOf(kitap_liste.get(i).get("fiyat")), 0, kitap_liste.get(i).get("kitap_adi"), kitap_liste.get(i).get("yazar"),Integer.valueOf(kitap_liste.get(i).get("SepetAdet"))));
                kitaplar.add(new kitapClass(Integer.valueOf(kitap_liste.get(i).get("id")),0,Integer.valueOf(kitap_liste.get(i).get("fiyat")),0,kitap_liste.get(i).get("kitap_adi"),kitap_liste.get(i).get("yazar"),kitap_liste.get(i).get("url"),"",Integer.valueOf(kitap_liste.get(i).get("SepetAdet"))));
            }
        }
        listemiz.setAdapter(adaptorumuz);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
    }

    public void urunGetir2()
    {
        db = new Database(getApplicationContext());
        kitap_liste = db.kitaplar2();//kitap listesini alıyoruz
        if (kitap_liste.size() == 0) {//kitap listesi boşsa
            Toast.makeText(getApplicationContext(), "Henüz Kitap Eklenmemiş.\nYukarıdaki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < kitap_liste.size(); i++) {
                //kitaplar.add(new kitapClass(Integer.valueOf(kitap_liste.get(i).get("id")),Integer.valueOf(kitap_liste.get(i).get("sipId")), 0, Integer.valueOf(kitap_liste.get(i).get("fiyat")), 0, kitap_liste.get(i).get("kitap_adi"), kitap_liste.get(i).get("yazar"),Integer.valueOf(kitap_liste.get(i).get("SepetAdet"))));
                kitaplar.add(new kitapClass(Integer.valueOf(kitap_liste.get(i).get("id")),0,Integer.valueOf(kitap_liste.get(i).get("fiyat")),0,kitap_liste.get(i).get("kitap_adi"),kitap_liste.get(i).get("yazar"),kitap_liste.get(i).get("url"),""));
            }
        }
        //listemiz.setAdapter(adaptorumuz);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
    }
}
