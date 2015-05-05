package prathameshshetye.mark.ui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prathameshshetye.mark.R;

/**
 * Created by prathamesh on 5/1/15.
 */
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int mSelected;

    private List<DrawerItems> mDrawerItems;
    private String mTitle;

    public RecAdapter(List<DrawerItems> items, String title) {
        mDrawerItems = items;
        mTitle = title;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_item, parent, false);
            return new ViewHolder(v,viewType);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_header, parent, false);
            return new ViewHolder(v,viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.mHolderId ==1) {
            holder.mItemText.setText(mDrawerItems.get(position-1).getTitle());
            holder.mItemIcon.setImageResource(mDrawerItems.get(position-1).getIcon());
        } else {
            holder.mAppTitle.setText(mTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int mHolderId;

        private TextView mItemText;
        private ImageView mItemIcon;
        private TextView mAppTitle;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            if (ViewType == TYPE_ITEM) {
                mItemText = (TextView) itemView.findViewById(R.id.rowText);
                mItemIcon = (ImageView) itemView.findViewById(R.id.rowIcon);
                mHolderId = 1;
            } else {
                mAppTitle = (TextView) itemView.findViewById(R.id.name);
                mHolderId = 0;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
