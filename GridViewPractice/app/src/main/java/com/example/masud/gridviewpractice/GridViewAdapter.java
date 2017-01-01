package com.example.masud.gridviewpractice;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MASUD on 3/31/2016.
 */
public class GridViewAdapter extends ArrayAdapter<Item>
{
    Context mContext;
    int resourceId;
    ArrayList<Item> data = new ArrayList<Item>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Item> data)
    {
        super(context, layoutResourceId, data);
        this.mContext = context;
        this.resourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View itemView = convertView;
        ViewHolder holder = null;

        if (itemView == null)
        {
            final LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(resourceId, parent, false);

            holder = new ViewHolder();

            holder.btn = (ImageView) itemView.findViewById(R.id.buttonItem);
            itemView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) itemView.getTag();
        }

        Item item = getItem(position);

        //holder.btn.setText(item.getTitle());
        if(item.getTitle().contains("BLUE"))
        holder.btn.setBackgroundColor(Color.BLUE);
        else if(item.getTitle().contains("GREEN"))
            holder.btn.setBackgroundColor(Color.GREEN);
        else if(item.getTitle().contains("DKGRAY"))
            holder.btn.setBackgroundColor(Color.DKGRAY);
        else if(item.getTitle().contains("RED"))
            holder.btn.setBackgroundColor(Color.RED);
        else if(item.getTitle().contains("YELLOW"))
            holder.btn.setBackgroundColor(Color.YELLOW);
        else
            holder.btn.setBackgroundColor(Color.CYAN);

        ViewGroup.LayoutParams params = holder.btn.getLayoutParams();
        params.height=840/item.size;
        holder.btn.setLayoutParams(params);

        return itemView;
    }

    static class ViewHolder {

        ImageView btn;
    }

    public void clearData(){
        data.clear();
        notifyDataSetChanged();
    }

}
