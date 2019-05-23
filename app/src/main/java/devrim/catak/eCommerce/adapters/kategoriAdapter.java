package devrim.catak.eCommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import devrim.catak.eCommerce.R;
import devrim.catak.eCommerce.clases.kategori;

public class kategoriAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<kategori> kategoriList;

    public kategoriAdapter(Activity activity, List<kategori> kategoriler) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        kategoriList = kategoriler;
    }

    @Override
    public int getCount() {
        return kategoriList.size();
    }

    @Override
    public kategori getItem(int position) {
        return kategoriList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.list_kategori, parent,false);
        TextView isim = (TextView) satirView.findViewById(R.id.isim);
        ImageView image =  satirView.findViewById(R.id.resim1);


        kategori kategori = kategoriList.get(position);

        Picasso.get().load("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png").into(image);
        isim.setText(kategori.getIsim());

        return satirView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
