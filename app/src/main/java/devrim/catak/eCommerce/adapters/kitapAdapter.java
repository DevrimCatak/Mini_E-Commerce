package devrim.catak.eCommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import devrim.catak.eCommerce.R;
import devrim.catak.eCommerce.clases.kitapClass;

public class kitapAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<kitapClass> kitapList;
    public kitapAdapter(Activity activity, List<kitapClass> kitaplar) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        kitapList = kitaplar;
    }

    @Override
    public int getCount() {
        return kitapList.size();
    }

    @Override
    public kitapClass getItem(int position) {
        return kitapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.list_kitap, null);
        TextView isim =  satirView.findViewById(R.id.isim);
        TextView yazar =  satirView.findViewById(R.id.yazar);
        TextView fiyat =  satirView.findViewById(R.id.fiyat);
        ImageView image =  satirView.findViewById(R.id.imageKitap);
        image.setMaxHeight(150);
        image.setMaxWidth(150);

        //image.setImageResource(R.drawable.demo);

        kitapClass kitap = kitapList.get(position);
        Picasso.get().load(kitap.getUrl()).into(image);
        isim.setText(kitap.getIsim());
        yazar.setText(kitap.getYazar());
        fiyat.setText(String.valueOf(kitap.getFiyat())+"₺");

        return satirView;
    }
}
