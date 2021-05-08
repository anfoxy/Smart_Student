package com.example.myapplication1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.R;
import com.example.myapplication1.StateAdapter;
import com.example.myapplication1.StateAdapterPlanToDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddPlanToDay extends Fragment implements View.OnClickListener {
    Button AddPlan;
    Button allplan;
    Calendar cal;
    ArrayList<Date_simple> c;
    int k;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_plan, container, false);
        MainActivity2.num3=-5;
        final  RecyclerView recyclerView = root.findViewById(R.id.list1);
        ArrayList<String> date =new ArrayList<String>();
        Calendar a =new GregorianCalendar();
        int day = a.get(Calendar.DATE);
        int month =a.get(Calendar.MONTH);
        int year = a.get(Calendar.YEAR);
        int n = MainActivity2.ArrPlan.get(MainActivity2.num).size_today_day_of_exams();

        cal=new GregorianCalendar();
        c = new ArrayList<Date_simple>();
        for(int i=0; i<n; i++){
            c.add(new Date_simple(cal.get(Calendar.DATE),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR)));
            date.add(""+cal.get(Calendar.DATE)+" "+getMonth(cal.get(Calendar.MONTH))+" "+cal.get(Calendar.YEAR));
            cal.add(Calendar.DATE, 1);
        }


        MainActivity2.p= new boolean[c.size()];
        StateAdapterPlanToDay adapter = new StateAdapterPlanToDay(getActivity(),date ,0, c );
        recyclerView.setAdapter(adapter);

        allplan =(Button)root.findViewById(R.id.all1);
        k=0;
        allplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(k==0) {
                    k=1;
                    StateAdapterPlanToDay adapter = new StateAdapterPlanToDay(getActivity(), date, 1, c);
                    recyclerView.setAdapter(adapter);
                }else {
                    k=0;
                    StateAdapterPlanToDay adapter = new StateAdapterPlanToDay(getActivity(), date, 0, c);
                    recyclerView.setAdapter(adapter);

                }
            }
        });

        AddPlan=(Button)root.findViewById(R.id.AddPlan);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity2.num3=0;
                if(MainActivity2.num2==10001){
                    MainActivity2.c = new ArrayList<Date_simple>(c);
                    MainActivity2.num2=10002;
                    Navigation.findNavController(view).navigate(R.id.action_navigation_plantoday_to_navigation_redactorplana);
                }else {
                for (int i=0;i<c.size();i++){
                    if(MainActivity2.p[i]==true){
                        MainActivity2.ArrPlan.get(MainActivity2.num).add_date_to_study(c.get(i).day,c.get(i).month,c.get(i).year);
                    }
                }
                MainActivity2.myDBManager.get(MainActivity2.ArrPlan.get(MainActivity2.num));
                Navigation.findNavController(view).navigate(R.id.action_navigation_plantoday_to_navigation_dashboard);
                }
            }
        });
        return root;
    }
public String getMonth(int m){
    switch (m) {
        case 0:
            return "Января";
        case 1:
            return "Февраля";
        case 2:
            return "Марта";
        case 3:
            return "Апреля";
        case 4:
            return "Мая";
        case 5:
            return "Июня";
        case 6:
            return "Июля";
        case 7:
            return "Августа";
        case 8:
            return "Сентября";
        case 9:
            return "Октября";
        case 10:
            return "Ноября";
        case 11:
            return "Декабря";
        default:
            return "ERROR";
    }
}


    @Override
    public void onClick(View v) {

    }
}