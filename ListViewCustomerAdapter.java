package com.example.myfirstwebbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewCustomerAdapter extends BaseAdapter {

    private List<Message> itemList;

    private LayoutInflater inflater;

    public ListViewCustomerAdapter(Activity context, List<Message> itemList){
        super();
        this.itemList=itemList;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount(){
        return itemList.size();
    }
    public Object getItem(int position){
        return itemList.get(position);
    }
    public long getItemId(int position){
        return position;
    }

    public static class ViewHolder{
        TextView textViewTitle;
        TextView textViewSubtitle;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.listitem_row,null);

            holder.textViewTitle=(TextView) convertView.findViewById(R.id.txtViewTitle);
            holder.textViewSubtitle=(TextView) convertView.findViewById(R.id.txtViewSubtitle);

            convertView.setTag(holder);

        }
        else
            holder=(ViewHolder)convertView.getTag();

        Message message=(Message) itemList.get(position);
        holder.textViewTitle.setText(message.getTitle());
        holder.textViewSubtitle.setText(message.getSubtitle());
        return convertView;
    }



}
