package devrim.catak.eCommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import devrim.catak.eCommerce.database.Database;
import devrim.catak.eCommerce.R;
import devrim.catak.eCommerce.clases.kitapClass;

public class sepetAdaptor extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<kitapClass> kitapList;
    public sepetAdaptor(Activity activity, List<kitapClass> kitaplar) {
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

    public void refreshEvents(List<kitapClass> events) {
        this.kitapList.clear();
        this.kitapList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View satirView;

        final Database db;
        db = new Database(mInflater.getContext());
        satirView = mInflater.inflate(R.layout.sepet_list, null);
        TextView isim = (TextView) satirView.findViewById(R.id.isim);
        TextView yazar = (TextView) satirView.findViewById(R.id.yazar);
        TextView fiyat = (TextView) satirView.findViewById(R.id.fiyat);
        TextView adet = (TextView) satirView.findViewById(R.id.adet);
        ImageView image =  satirView.findViewById(R.id.imageKitap);
        Button azalt =  satirView.findViewById(R.id.azalt);


        final kitapClass kitap = kitapList.get(position);

        isim.setText(kitap.getIsim());
        yazar.setText(kitap.getYazar());
        fiyat.setText(String.valueOf(kitap.getFiyat())+"₺");
        Picasso.get().load(kitap.getUrl()).into(image);
        adet.setText(String.valueOf(kitap.getSepetAdet()+" adet"));

        azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.kitapSil(kitap.getIsim());
                if (kitap.getSepetAdet()>1)
                    kitap.setSepetAdet(kitap.getSepetAdet()-1);
                else
                    kitapList.remove(position);
                notifyDataSetChanged(); }
        });

        return satirView;
    }
}

