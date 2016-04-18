package martell.com.vice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mstarace on 4/18/16.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter {
    private List<NavDrawerEntry> data;
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context, List<NavDrawerEntry> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView;
        switch (viewType) {
            case 0:
                itemLayoutView = inflater.inflate(R.layout.nav_drawer_item_with_icon, parent, false);
                ItemWithIconVH holder = new ItemWithIconVH(itemLayoutView);
                return holder;
            case 1:
                itemLayoutView = inflater.inflate(R.layout.nav_drawer_divider, parent, false);
                DividerVH dividerViewHolder = new DividerVH(itemLayoutView);
                return dividerViewHolder;
            case 2:
                itemLayoutView = inflater.inflate(R.layout.nav_drawer_item, parent, false);
                ItemVH itemViewHolder = new ItemVH(itemLayoutView);
                return itemViewHolder;
            case 3:
                itemLayoutView = inflater.inflate(R.layout.nav_drawer_toggle, parent, false);
                ToggleVH toggleViewHolder = new ToggleVH(itemLayoutView);
                return toggleViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NavDrawerEntry item = data.get(position);

        if (item instanceof NavDrawerItemWithIcon) {
            ItemWithIconVH viewHolder = (ItemWithIconVH) holder;
            viewHolder.mTitle.setText(((NavDrawerItemWithIcon) item).getTitle());
            viewHolder.mImageView.setImageResource(((NavDrawerItemWithIcon) item).getIconId());
        }

        if (item instanceof NavDrawerItem) {
            ItemVH viewHolder = (ItemVH) holder;
            viewHolder.mTitle.setText(((NavDrawerItem) item).getTitle());
        }

        if (item instanceof NavDrawerToggle) {
            ToggleVH viewHolder = (ToggleVH) holder;
            viewHolder.mTitle.setText(((NavDrawerToggle) item).getTitle());
            viewHolder.mSwitch.setChecked(((NavDrawerToggle) item).isChecked());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof NavDrawerItemWithIcon)
            return 0;

        if (data.get(position) instanceof NavDrawerDivider)
            return 1;

        if (data.get(position) instanceof NavDrawerItem)
            return 2;

        if (data.get(position) instanceof NavDrawerToggle)
            return 3;

        return -1;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemWithIconVH extends RecyclerView.ViewHolder {
        final TextView mTitle;
        final ImageView mImageView;

        public ItemWithIconVH(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.nav_item_title);
            mImageView = (ImageView) itemView.findViewById(R.id.nav_item_image);
        }
    }

    class DividerVH extends RecyclerView.ViewHolder {
        public DividerVH(View itemView) {
            super(itemView);
        }
    }

    class ItemVH extends RecyclerView.ViewHolder {
        final TextView mTitle;

        public ItemVH(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.nav_item_title);
        }
    }

    class ToggleVH extends RecyclerView.ViewHolder {
        final TextView mTitle;
        final Switch mSwitch;

        public ToggleVH(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.nav_item_title);
            mSwitch = (Switch) itemView.findViewById(R.id.nav_switch);
        }
    }

}
