package com.xuwei.order.gaodemaptest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xuwei.order.gaodemaptest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xuweijie
 * on 2018/5/30 0030.
 */
public class SearchAdapter extends BaseAdapter {

    private List<HashMap<String,String>> addressData;
    private LayoutInflater layoutInflater;


    public SearchAdapter(Context context) {
        layoutInflater=LayoutInflater.from(context);
        addressData=new ArrayList<HashMap<String, String>>() ;

    }

    @Override
    public int getCount() {
        return addressData.size();
    }
    @Override
    public Object getItem(int position) {
        return addressData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.search_list_item, null);
            vh.title = (TextView) convertView.findViewById(R.id.search_item_title);
            vh.text = (TextView) convertView.findViewById(R.id.search_item_text);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(addressData.get(position).get("name"));
        //vh.text.setText(addressData.get(position).get("district")+addressData.get(position).get("address"));

        return convertView;
    }

    public class ViewHolder{
        TextView title;
        TextView text;
    }
}