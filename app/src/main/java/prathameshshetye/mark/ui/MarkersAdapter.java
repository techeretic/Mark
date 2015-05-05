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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import prathameshshetye.mark.R;
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
        if (tagText == null) {
            tagText = String.valueOf(mMarkers.get(position).getLatitude()) + "," + String.valueOf(mMarkers.get(position).getLongitude());
        }
        holder.mItemText.setText(tagText);
        holder.mItemText.setTextColor(Color.BLACK);
        byte[] bitMapData = mMarkers.get(position).getImage();
        if (bitMapData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitMapData, 0, bitMapData.length);

            BitmapDrawable bm = new BitmapDrawable(Resources.getSystem(), bitmap);
            bm.setColorFilter(new PorterDuffColorFilter(Color.argb(150, 0, 0, 0), PorterDuff.Mode.SRC_ATOP));
            holder.mItemLayout.setBackground(bm);
        }
    }

    @Override
    public int getItemCount() {
        return mMarkers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mItemLayout;
        TextView mItemText;

        public ViewHolder(View view) {
            super(view);
            mItemLayout = (LinearLayout) view.findViewById(R.id.marker_layout);
            mItemText = (TextView) view.findViewById(R.id.marker_name);
        }
    }
}
