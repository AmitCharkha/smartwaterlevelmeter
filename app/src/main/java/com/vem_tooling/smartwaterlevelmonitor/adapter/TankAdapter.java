package com.vem_tooling.smartwaterlevelmonitor.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.CorisandeBoldTextView;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.vo.TankVO;

import java.util.List;

/**
 * Created by amit on 27/6/17.
 */

public class TankAdapter extends RecyclerView.Adapter<TankAdapter.ViewHolder> {

    List<TankVO> tankVOs;
    Context context;

    public TankAdapter (Context context,List<TankVO> tankVOs){
        this.context = context;
        this.tankVOs = tankVOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tank_item, viewGroup, false);
        return new TankAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tankNoTextView.setText("Tank no " + tankVOs.get(position).getTankNo());
        holder.tankLevelTextView.setText("Tank Level : " + tankVOs.get(position).getPercentage() + "%");
        if(tankVOs.get(position).getPercentage() <= 90){
            holder.OneTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            if(tankVOs.get(position).getPercentage() <= 80){
                holder.TwoTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                if(tankVOs.get(position).getPercentage() <= 70){
                    holder.ThreeTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    if(tankVOs.get(position).getPercentage() <= 60){
                        holder.FourTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                        if(tankVOs.get(position).getPercentage() <= 50){
                            holder.FiveTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                            if(tankVOs.get(position).getPercentage() <= 40){
                                holder.SixTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                                if(tankVOs.get(position).getPercentage() <= 30){
                                    holder.SevenTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                                    if(tankVOs.get(position).getPercentage() <= 20){
                                        holder.EightTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                                        if(tankVOs.get(position).getPercentage() <= 10){
                                            holder.NineTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                                            if(tankVOs.get(position).getPercentage() == 0){
                                                holder.TenTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

                                            }else{
                                                holder.TenTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_bg));
                                            }
                                        }else{
                                            holder.NineTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_bg));
                                        }
                                    }else{
                                        holder.EightTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.red_bg));
                                    }
                                }else{
                                    holder.SevenTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow_bg));
                                }
                            }else{
                                holder.SixTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow_bg));
                            }
                        }else{
                            holder.FiveTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow_bg));
                        }
                    }else{
                        holder.FourTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow_bg));
                    }
                }else{
                    holder.ThreeTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
                }
            }else{
                holder.TwoTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
            }
        }else{
            holder.OneTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return tankVOs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View OneTextView,TwoTextView,ThreeTextView,FourTextView,FiveTextView;
        View SixTextView,SevenTextView,EightTextView,NineTextView,TenTextView;
        CorisandeBoldTextView tankNoTextView;
        LatoRegularTextView tankLevelTextView;

        public  ViewHolder(final View itemView) {
            super(itemView);

            OneTextView = (View) itemView.findViewById(R.id.OneTextView);
            TwoTextView = (View) itemView.findViewById(R.id.TwoTextView);
            ThreeTextView = (View) itemView.findViewById(R.id.ThreeTextView);
            FourTextView = (View) itemView.findViewById(R.id.FourTextView);
            FiveTextView = (View) itemView.findViewById(R.id.FiveTextView);
            SixTextView = (View) itemView.findViewById(R.id.SixTextView);
            SevenTextView = (View) itemView.findViewById(R.id.SevenTextView);
            EightTextView = (View) itemView.findViewById(R.id.EightTextView);
            NineTextView = (View) itemView.findViewById(R.id.NineTextView);
            TenTextView = (View) itemView.findViewById(R.id.TenTextView);

            tankNoTextView = (CorisandeBoldTextView) itemView.findViewById(R.id.tankNoTextView);
            tankLevelTextView = (LatoRegularTextView) itemView.findViewById(R.id.tankLevelTextView);
        }
    }
}
