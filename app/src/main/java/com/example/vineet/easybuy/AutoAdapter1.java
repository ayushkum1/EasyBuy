package com.example.vineet.easybuy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin3 on 15-12-2017.
 */

public class AutoAdapter1 extends RecyclerView.Adapter<AutoAdapter1.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    List primarytextList,secondarytextList;
    private static final String TAG = "PlaceAutocompleteAdapter";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    public AutoAdapter1(Context context, List primarytextList, List secondarytextList) {

        this.context = context;
        this.primarytextList = primarytextList;
        this.secondarytextList = secondarytextList;
        inflater = LayoutInflater.from(context);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;

        public MyViewHolder(View view) {
            super(view);


            tv1 = (TextView) view.findViewById(R.id.ptext);
            tv2 = (TextView) view.findViewById(R.id.sectext);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        // holder.imageView.setImageDrawable(imagelist.get(position));
        //AutocompletePrediction item = getItem(position);

        holder.tv1.setText(primarytextList.get(position).toString());
        holder.tv2.setText(secondarytextList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return primarytextList.size();
    }



}
