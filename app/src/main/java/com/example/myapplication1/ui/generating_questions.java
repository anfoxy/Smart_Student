package com.example.myapplication1.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication1.Date_simple;
import com.example.myapplication1.MainActivity2;
import com.example.myapplication1.PlanToDay;
import com.example.myapplication1.PlanToSub;
import com.example.myapplication1.Question;
import com.example.myapplication1.R;
import com.example.myapplication1.StateAdapter3;
import com.example.myapplication1.Study;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class generating_questions extends Fragment {
    Button Otvet;
    Button DA;
    Button NET;
    TextView Ot;
    TextView getVop;
    TextView getOtv;
    TextView nom;
    Dialog dialog;
    int a;
    int n;
    int i;
    int n1;
    @SuppressLint("CutPasteId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_generating_questions, container, false);
        Ot=(TextView)root.findViewById(R.id.otve) ;
        nom=(TextView)root.findViewById(R.id.Num) ;
        getVop=(TextView)root.findViewById(R.id.textVop) ;
        getOtv=(TextView)root.findViewById(R.id.otve) ;
        Otvet=(Button)root.findViewById(R.id.XZ) ;
        DA=(Button)root.findViewById(R.id.Da) ;
        NET=(Button)root.findViewById(R.id.No) ;
        i=1;
        nom.setText("Вопрос №"+ i);
        Ot.setVisibility(View.INVISIBLE);
        DA.setVisibility(View.INVISIBLE);
        NET.setVisibility(View.INVISIBLE);
        getOtv.setMovementMethod(new ScrollingMovementMethod());
        getOtv.setMovementMethod(new ScrollingMovementMethod());

        if(MainActivity2.num3==2){
            dialog=new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogque4);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView t = (TextView) dialog.findViewById(R.id.textv1);
            t.setText("Вы можете повторить вопросы, которые уже изучили. \nРезультат будет записан в общий план!");
            TextView t1 = (TextView) dialog.findViewById(R.id.textv);
            t1.setText("Вы уже прошли план на сегодня!");
            //кнопка добавление даты
            Button bt = (Button)dialog.findViewById(R.id.bSet);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            n1=0;
            Study n1 = new Study(MainActivity2.ArrPlan.get(MainActivity2.num).sub,2);
            n = n1.STUDY_ALL();

            getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
            getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());

            Otvet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ot.setVisibility(View.VISIBLE);
                    Otvet.setVisibility(View.INVISIBLE);
                    DA.setVisibility(View.VISIBLE);
                    NET.setVisibility(View.VISIBLE);
                }
            });
            DA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;

                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    Question q = new Question(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.ArrPlan.get(MainActivity2.num).after_study1(n, 1);

                    MainActivity2.myDBManager.update_dateQW(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, q, MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getdate());

                    n = n1.STUDY_ALL();
                    if (n == -1) {
                        Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                    } else {
                        getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
                        getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());
                        nom.setText("Вопрос №" + i);
                    }
                }
            });
            //нет
            NET.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    i++;

                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    Question q = new Question(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.ArrPlan.get(MainActivity2.num).after_study1(n, 0);

                    ArrayList<PlanToDay> p = new  ArrayList<PlanToDay>( MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day);
                    MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, q, MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned);
                    MainActivity2.myDBManager.update_Allplantoday(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub,p,MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day);


                    n = n1.STUDY_ALL();
                    if (n == -1) {
                        Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                    } else {
                        getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
                        getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());
                        nom.setText("Вопрос №" + i);
                    }

                }
            });

        }else if(MainActivity2.num3==1){
            dialog=new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogque4);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //кнопка добавление даты
            Button bt = (Button)dialog.findViewById(R.id.bSet);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //добавляем вопрос в массив вопросов

                    dialog.dismiss();

                }
            });
            dialog.show();
            n1=0;
            getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getQuestion());
            getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getAnswer());

            Otvet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ot.setVisibility(View.VISIBLE);
                    Otvet.setVisibility(View.INVISIBLE);
                    DA.setVisibility(View.VISIBLE);
                    NET.setVisibility(View.VISIBLE);
                }
            });
            DA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    n1++;
                    if(n1>=MainActivity2.ArrPlan.get(MainActivity2.num).getQue().size())n1=0;
                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getQuestion());
                    getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getAnswer());
                    nom.setText("Вопрос №"+ i);

                }
            });
            //нет
            NET.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    i++;
                    n1++;
                    if(n1==MainActivity2.ArrPlan.get(MainActivity2.num).getQue().size()){n1=0;}
                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getQuestion());
                    getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n1).getAnswer());
                    nom.setText("Вопрос №"+ i);


                }
            });

        }else {
            Study n1 = new Study(MainActivity2.ArrPlan.get(MainActivity2.num).sub, MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day.get(MainActivity2.num2).size_of_quetion, MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned);
            n = n1.STUDY();
            getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
            getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());

            Otvet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ot.setVisibility(View.VISIBLE);
                    Otvet.setVisibility(View.INVISIBLE);
                    DA.setVisibility(View.VISIBLE);
                    NET.setVisibility(View.VISIBLE);
                }
            });

            //да
            DA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;

                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    Question q = new Question(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.ArrPlan.get(MainActivity2.num).after_study(n, 1);

                    MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, q, MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned);

                    if (MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day.get(MainActivity2.num2).size_of_quetion == MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned) {
                        dialog=new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialogque4);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView t = (TextView) dialog.findViewById(R.id.textv1);
                        t.setText("Поздравляем! Вы прошли план на сегодня! \nТеперь вы можете повторить все выученные вопросы.");
                        TextView t1 = (TextView) dialog.findViewById(R.id.textv);
                        t1.setText("Вы прошли план на сегодня!");
                        //кнопка добавление даты
                        Button bt = (Button)dialog.findViewById(R.id.bSet);
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                        Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                    } else {
                        n = n1.STUDY();
                        getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
                        getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());
                        nom.setText("Вопрос №" + i);
                    }
                }
            });
            //нет
            NET.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    i++;
                    Ot.setVisibility(View.INVISIBLE);
                    Otvet.setVisibility(View.VISIBLE);
                    DA.setVisibility(View.INVISIBLE);
                    NET.setVisibility(View.INVISIBLE);

                    Question q = new Question(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.ArrPlan.get(MainActivity2.num).after_study(n, 0);

                    MainActivity2.myDBManager.update_QWTABLE(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, q, MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n));
                    MainActivity2.myDBManager.update_todaylearning(MainActivity2.ArrPlan.get(MainActivity2.num).sub.name_of_sub, MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned);

                    if (MainActivity2.ArrPlan.get(MainActivity2.num).plan_to_day.get(MainActivity2.num2).size_of_quetion == MainActivity2.ArrPlan.get(MainActivity2.num).todaylearned) {
                        Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_navigation_home);
                    } else {
                        n = n1.STUDY();
                        getVop.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getQuestion());
                        getOtv.setText(MainActivity2.ArrPlan.get(MainActivity2.num).getQue().get(n).getAnswer());
                        nom.setText("Вопрос №" + i);
                    }

                }
            });
        }

        return root;
    }
}