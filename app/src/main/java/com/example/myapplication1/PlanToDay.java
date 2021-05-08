package com.example.myapplication1;
import java.util.Calendar;
import java.util.GregorianCalendar;
//ди
public class PlanToDay {

  public   Date_simple date;
   public int size_of_quetion;
    public PlanToDay(int day, int mon, int year, int size_of_quet){
        date= new Date_simple(day, mon, year);
        size_of_quetion=size_of_quet;
    }
    PlanToDay(Date_simple date_, int size_of_quet){
        date=  date_;
        size_of_quetion=size_of_quet;
    }

    void change_size_of_quetion(int a){
        size_of_quetion=a;
    }

    public String getSize_of_quetion() {
        return String.valueOf(size_of_quetion);
    }
}