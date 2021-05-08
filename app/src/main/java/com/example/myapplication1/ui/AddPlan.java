package com.example.myapplication1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.StateAdapter3;
import com.example.myapplication1.Subject;

import java.util.ArrayList;
import java.util.Calendar;


public class AddPlan extends Fragment implements View.OnClickListener  {
Button addQue;
Button addplan;
private TextView mDisplayDate;
    TextView textView;
    StateAdapter3.OnStateClickListener stateClickListener;
private DatePickerDialog.OnDateSetListener mDateSetListener;
    int day2,month2,year2=-1;
    Dialog dialog;
    String date;
    Date_simple d2;
    //editTextDate   textOTVET  textQUE
 public static  ArrayList<Question> question ;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add, container, false);
        textView = (TextView) root.findViewById(R.id.Text1);
        mDisplayDate = (EditText) root.findViewById(R.id.editTextDate);
        if(MainActivity2.num3==-5){
            question = new ArrayList<Question>(MainActivity2.ArrPlan.get(MainActivity2.num).getQue());
            day2 = MainActivity2.ArrPlan.get(MainActivity2.num).getDay();
            month2 = MainActivity2.ArrPlan.get(MainActivity2.num).getMonth();
            year2 = MainActivity2.ArrPlan.get(MainActivity2.num).getYear();
            date = day2 + "/" + (month2+1) + "/" + year2;
            mDisplayDate.setText(date);
            textView.setText(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub);
            MainActivity2.ArrPlan.remove(MainActivity2.num);
        }else {
            question = new ArrayList<Question>();
        }
        MainActivity2.num3=0;

        final  RecyclerView recyclerView = root.findViewById(R.id.listVop);

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
                        question.get(position).answer=textOtv.getText().toString();
                        question.get(position).question=textVop.getText().toString();
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                //Кнопка отмены
                Button bt2 = (Button)dialog.findViewById(R.id.bdel);
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        question.remove(position);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();//закрываем диалоговое окно
                    }
                });
                dialog.show();

            }
        };

        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);
        recyclerView.setAdapter(adapter);


       // нажатие на кнопку добавление вопроса
        addQue=(Button)root.findViewById(R.id.addque);
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
                        question.add(vop);
                        StateAdapter3 adapter = new StateAdapter3(getActivity(), question,stateClickListener);

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
        // запись даты
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



        // кнопка добавления плана
        addplan=(Button)root.findViewById(R.id.AddPlan1);
        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                d2 = new Date_simple(day2,month2,year2);
                Date_simple d = new  Date_simple();
                if (day2 == -1 || month2 == -1 || year2 == -1) {
                    Toast toast = Toast.makeText(getContext(),
                            "Выберите дату", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (textView.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(getContext(),
                            "Введите название", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (question.size() == 0) {
                    Toast toast = Toast.makeText(getContext(),
                            "Нет вопросов", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (d2.before(d)) {
                    Toast toast = Toast.makeText(getContext(),
                            "Неверная дата", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    int u = 0;
                    for (int i = 0; i < MainActivity2.ArrPlan.size(); i++) {
                        if (MainActivity2.ArrPlan.get(i).sub.name_of_sub.equals(textView.getText().toString())) {
                            u = 1;
                        }
                    }
                    if (u == 0) {
                        Subject subject = new Subject(textView.getText().toString(), question);
                        PlanToSub pl = new PlanToSub(subject, day2, month2, year2);
                        MainActivity2.ArrPlan.add(pl);
                        MainActivity2.num=MainActivity2.ArrPlan.size()-1;

                        Navigation.findNavController(view).navigate(R.id.action_navigation_addplan_to_navigation_plantoday);
                    } else {
                        Toast toast = Toast.makeText(getContext(), "предмет с данным названием уже существует", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        return root;
    }




    @Override
    public void onClick(View v) {

    }
}