package com.example.myapplication1;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StateAdapterPlanToDay extends RecyclerView.Adapter<StateAdapterPlanToDay.ViewHolder>{
  //  public interface OnStateClickListener{ void onStateClick(newPlan stateP, int position);}
    private final LayoutInflater inflater;
    private final List<String> planToDay;
    public int ind;
    public final ArrayList<Date_simple> c;

  //  private final OnStateClickListener onClickListener;

    public StateAdapterPlanToDay(Context context, List<String> states , int ind, ArrayList<Date_simple> c) {
   //    this.onClickListener = OnClickListener;
        this.planToDay = states;
        this.ind = ind;
        this.c = c;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StateAdapterPlanToDay.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapterPlanToDay.ViewHolder holder, int position) {
        String state3 = planToDay.get(position);
        holder.nameView.setText(state3);
        if(ind==1){
            holder.box.setChecked(true);
            MainActivity2.p[position]=true;


        }else if(ind==0){
            holder.box.setChecked(false);
            MainActivity2.p[position]=false;

        }else {
            holder.box.setChecked(MainActivity2.p[position]);

        }
        holder.box.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ind=2;
             if(holder.box.isChecked())
               {
                   MainActivity2.p[position]=true;
                   //MainActivity2.ArrPlan.get(MainActivity2.num).add_date_to_study(c.get(position).day,c.get(position).month,c.get(position).year);

               }
                else
               {
                   MainActivity2.p[position]=false;
                  // MainActivity2.ArrPlan.get(MainActivity2.num).delete_date_to_study(c.get(position).day,c.get(position).month,c.get(position).year);
               }


            }
        });
    }

    @Override
    public int getItemCount() {
        return planToDay.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final CheckBox box;
        ViewHolder(View view){
            super(view);
            box=(CheckBox) view.findViewById(R.id.checkBox);
            nameView = (TextView) view.findViewById(R.id.opril3o);
        }
    }
}