package prathameshshetye.mark.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import prathameshshetye.mark.R;
import prathameshshetye.mark.Utilities.Log;
import prathameshshetye.mark.database.Marker;

/**
 * Created by p.shetye on 5/5/15.
 */
public class MarkersAdapter extends RecyclerView.Adapter<MarkersAdapter.ViewHolder>{

    private List<Marker> mMarkers;

    public MarkersAdapter(List<Marker> markers) {
        mMarkers = markers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_markers_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String tagText = mMarkers.get(position).getName();
        if (tagText == null || tagText.isEmpty()) {
            tagText = String.valueOf(mMarkers.get(position).getLatitude()) + "," + String.valueOf(mMarkers.get(position).getLongitude());
        }
        Log.LogThis("tagText = " + tagText);
        holder.mItemText.setText(tagText);
        byte[] bitMapData = mMarkers.get(position).getImage();
        if (bitMapData != null) {
            holder.mItemText.setTextColor(Color.WHITE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitMapData, 0, bitMapData.length);
            BitmapDrawable bm = new BitmapDrawable(Resources.getSystem(), bitmap);
            bm.setColorFilter(new PorterDuffColorFilter(Color.argb(150, 0, 0, 0), PorterDuff.Mode.SRC_ATOP));
            holder.mItemScreen.setImageDrawable(bm);
        }
    }

    @Override
    public int getItemCount() {
        return mMarkers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemScreen;
        TextView mItemText;

        public ViewHolder(View view) {
            super(view);
            mItemScreen = (ImageView) view.findViewById(R.id.resultImage);
            mItemText = (TextView) view.findViewById(R.id.marker_name);
        }
    }
}
