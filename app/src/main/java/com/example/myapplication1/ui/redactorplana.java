package com.example.myapplication1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.StateAdapter3;
import com.example.myapplication1.Subject;

import java.util.ArrayList;
import java.util.Calendar;


public class redactorplana extends Fragment {
    Button addQue;

    StateAdapter3.OnStateClickListener stateClickListener;
    private EditText mDisplayDate;
    private EditText name;
    String date;
    public Date_simple d2;
    private ArrayList<Question> q;
    int day2=MainActivity2.ArrPlan.get(MainActivity2.num).getDay();
    int month2 = MainActivity2.ArrPlan.get(MainActivity2.num).getMonth();
    int year2 =MainActivity2.ArrPlan.get(MainActivity2.num).getYear();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add2, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.listVop);
        q = new ArrayList<Question>(MainActivity2.ArrPlan.get(MainActivity2.num).getQue());

        //нажатие на вопрос
        stateClickListener = new StateAdapter3.OnStateClickListener() {
            @Override
            public void onStateClick(Question state, int position) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque1);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView textOtv = (TextView) dialog.findViewById(R.id.textOTVET);
                TextView textVop = (TextView) dialog.findViewById(R.id.textQUE);
                textOtv.setText(state.getAnswer());
                textVop.setText(state.getQuestion());

                //dialog.setCancelable(false);//нельзя закрыть кнопкой назад
                //кнопка добавление вопроса
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        q.get(position).question=textVop.getText().toString();
                        q.get(position).answer=textOtv.getText().toString();

                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                //Кнопка удаления
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        q.remove(position);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        };

        //Записываем имя
        name = (EditText) root.findViewById(R.id.Text1);
        name.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getSub());

        //записываем дату
        mDisplayDate = (EditText) root.findViewById(R.id.editTextDate);

        mDisplayDate.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getDate());
        d2 = new Date_simple(MainActivity2.ArrPlan.get(MainActivity2.num).getDate_of_exams().day, MainActivity2.ArrPlan.get(MainActivity2.num).getDate_of_exams().month, MainActivity2.ArrPlan.get(MainActivity2.num).getDate_of_exams().year);
        mDisplayDate.setInputType(InputType.TYPE_NULL);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque3);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CalendarView cw = (CalendarView) dialog.findViewById(R.id.calendarView);

                cw.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        day2 = dayOfMonth;
                        month2 = month;
                        year2 = year;
                        d2 = new Date_simple(day2,month2,year2);
                        month = month + 1;
                        date = dayOfMonth + "/" + month + "/" + year;

                    }
                });
                //кнопка добавление даты
                Button bt = (Button)dialog.findViewById(R.id.set);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        mDisplayDate.setText(date);
                        dialog.dismiss();

                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.ot);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();
            }
        });
        //выводим лист
        StateAdapter3 adapter = new StateAdapter3(getActivity(), q,stateClickListener);
        recyclerView.setAdapter(adapter);
        //редактировать план
        Button b1 = (Button)root.findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Date_simple d = new  Date_simple();
                if (d2.before(d)) {
                    Toast toast = Toast.makeText(getContext(),
                            "Неверная дата", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    MainActivity2.num2 = 10001;
                    MainActivity2.ArrPlan.get(MainActivity2.num).change_date_of_exams(day2, month2, year2);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_redactorplana_to_navigation_plantoday);
                }
            }

        });

        //сохранить
        TextView addpl=(TextView)root.findViewById(R.id.save);
        addpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Date_simple d = new  Date_simple();

                if (name.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Введите название", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(q.size()==0){
                    Toast toast = Toast.makeText(getContext(),
                            "Нет вопросов", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (d2.before(d)) {
                        Toast toast = Toast.makeText(getContext(), "Неверная дата", Toast.LENGTH_SHORT);
                        toast.show();
                }  else {
                    int u = 0;
                    for (int i = 0; i < MainActivity2.num; i++) {
                        if (    MainActivity2.ArrPlan.get(i).sub.name_of_sub.equals(name.getText().toString())) u = 1;
                    }
                    for (int i = MainActivity2.num+1; i < MainActivity2.ArrPlan.size(); i++) {
                        if (    MainActivity2.ArrPlan.get(i).sub.name_of_sub.equals(name.getText().toString())) u = 1;
                    }
                    if (u == 0) {
                        MainActivity2.myDBManager.delete_QUE(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub);
                        for(int i=0;i<q.size();i++){
                            MainActivity2.myDBManager.insert_TABLE_QUE(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, q.get(i));
                        }
                        MainActivity2.ArrPlan.get(MainActivity2.num).sub.add_questionArr(q);
                        MainActivity2.myDBManager.update_sub(name.getText().toString(), MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub);
                        MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub = name.getText().toString();
                        MainActivity2.ArrPlan.get(MainActivity2.num).change_date_of_exams(day2, month2, year2);
                        MainActivity2.myDBManager.update_date_ex(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.ArrPlan.get(MainActivity2.num).getDate_of_exams());
                        ArrayList<PlanToDay> p = new  ArrayList<PlanToDay>(MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day);
                        MainActivity2.myDBManager.update_Allplantoday(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub,p,MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day);
                        if(MainActivity2.num2==10002) {
                            for (int i = 0; i < MainActivity2.p.length; i++) {
                                if (MainActivity2.p[i]) {
                                    int n = MainActivity2.ArrPlan.get(MainActivity2.num).search_nom_plan(MainActivity2.c.get(i).day, MainActivity2.c.get(i).month, MainActivity2.c.get(i).year);
                                    if (n == -1) {
                                        MainActivity2.ArrPlan.get(MainActivity2.num).add_date_to_study(MainActivity2.c.get(i).day, MainActivity2.c.get(i).month, MainActivity2.c.get(i).year);
                                        int n1 = MainActivity2.ArrPlan.get(MainActivity2.num).search_nom_plan(MainActivity2.c.get(i).day, MainActivity2.c.get(i).month, MainActivity2.c.get(i).year);
                                        MainActivity2.myDBManager.insert_TABLE_TODAY2(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day.get(n1));
                                    }
                                } else {
                                    int n = MainActivity2.ArrPlan.get(MainActivity2.num).search_nom_plan(MainActivity2.c.get(i).day, MainActivity2.c.get(i).month, MainActivity2.c.get(i).year);
                                    if (n != -1) {
                                        MainActivity2.myDBManager.delete_plantoday(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.c.get(i));
                                        MainActivity2.ArrPlan.get(MainActivity2.num).delete_date_to_study(MainActivity2.c.get(i).day, MainActivity2.c.get(i).month, MainActivity2.c.get(i).year);
                                    }
                                }
                            }
                        }
                        MainActivity2.num2=-1;
                        Navigation.findNavController(view).navigate(R.id.action_navigation_redactorplana_to_navigation_dashboard);
                    }else {
                        Toast toast = Toast.makeText(getContext(), "предмет с данным названием уже существует", Toast.LENGTH_SHORT);
                        toast.show(); }
                }
            }
        });

        ImageButton del=(ImageButton)root.findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.del);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //Кнопка удаления
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity2.myDBManager.delsub(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub);
                        MainActivity2.ArrPlan.remove(MainActivity2.num);
                        Navigation.findNavController(view).navigate(R.id.action_navigation_redactorplana_to_navigation_dashboard);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        // нажатие на кнопку добавление вопроса
        addQue=(Button)root.findViewById(R.id.b);
        addQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog=new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogque);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);//нельзя закрыть кнопкой назад
                //кнопка добавление вопроса
                Button bt = (Button)dialog.findViewById(R.id.bSet);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //добавляем вопрос в массив вопросов
                        TextView textOtv = (TextView) dialog.findViewById(R.id.textOTVET);
                        TextView textVop = (TextView) dialog.findViewById(R.id.textQUE);
                        Question vop= new Question(textVop.getText().toString(),textOtv.getText().toString());
                        q.add(vop);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(),q,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.bOtmena);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();
            }
        });

        return root;
    }
}