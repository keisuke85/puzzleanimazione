package it.keisoft.puzzleanimazione;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mmarcheselli on 05/11/2015.
 */
public class NavigationDrawerListAdapter extends BaseAdapter {

    private Context _context;
    private ArrayList<Items> _items;

    public NavigationDrawerListAdapter(Context _context, ArrayList<Items> _items){
        this._context = _context;
        this._items = _items;
    }

    private class ViewHolder{
        TextView itemName;
        TextView itemDescription;
        ImageView itemIcon;
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int i) {
        return _items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(view == null){
            view = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) view.findViewById(R.id.item_name_txtview);
            holder.itemDescription = (TextView) view.findViewById(R.id.item_name_description);
            holder.itemIcon = (ImageView) view.findViewById(R.id.item_icon_imgview);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Items item = (Items) getItem(i);
        holder.itemName.setText(item.getItemName());
        holder.itemDescription.setText(item.getItemDesc());
        holder.itemIcon.setBackgroundResource((int)item.getIconId());

        return view;
    }
}
