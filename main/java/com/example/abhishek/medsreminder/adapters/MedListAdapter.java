package com.example.abhishek.medsreminder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.medsreminder.R;

/**
 * Created by Abhishek on 30-Jun-17.
 */
public class MedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Cursor mCursor;
    private LayoutInflater mInflator;

    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_DOSAGE = 2;
    public static final int COLUMN_DATE = 3;
    public static final int COLUMN_TIME = 4;

    public MedListAdapter(Context context){
        mInflator = LayoutInflater.from(context);
    }

    public void setCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.p_med_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        mCursor.moveToPosition(position);

        holder.tvName.setText(mCursor.getString(COLUMN_NAME));
        holder.tvDosage.setText(mCursor.getString(COLUMN_DOSAGE));
        holder.tvDate.setText(mCursor.getString(COLUMN_DATE));
        holder.tvTime.setText(mCursor.getString(COLUMN_TIME));
    }

    @Override
    public int getItemCount() {
        if (mCursor != null){
            return mCursor.getCount();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDosage;
        TextView tvDate;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDosage = (TextView) itemView.findViewById(R.id.tv_dosage);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
