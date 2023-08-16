package com.example.timetableparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private HandlerHTML handlerHTML;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FileSavigData.isFile(this))
        {
            setContentView(R.layout.activity_main);
            FileSavigData.openText(this);
        }
        else
        {
            setContentView(R.layout.activity_main);
        }
        Spinner sSem = findViewById(R.id.spinSem);
        handlerHTML = new HandlerHTML(this);
        ArrayAdapter<String> adapterSemestr = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,handlerHTML.getSemesters());
        adapterSemestr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sSem.setAdapter(adapterSemestr);
    }

    public void button_Click(View v)
    {
        try
        {
            Spinner sSem = findViewById(R.id.spinSem);
            ArrayList<Couple> coupleList = handlerHTML.getScheduleCouples(sSem.getSelectedItem().toString(),"О-18-ИАС-аид-С");
            ArrayList<Day> days = sortCouples(coupleList);
            ListView lv = findViewById(R.id.list);
            ScheduleAdapter adapter = new ScheduleAdapter(this,R.layout.layout_day, days);
          //  CoupleAdapter adapter = new CoupleAdapter(this,R.layout.layout_couple, coupleList);
            lv.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Что-то пошло не так", Toast.LENGTH_LONG);
        }
    }

    private ArrayList<Day> sortCouples(ArrayList<Couple> couples)
    {
        ArrayList<Day> days = new ArrayList<>();
        Day day = new Day("");
        for (int i = 0; i < couples.size(); i++)
        {
            if(day.getName() != couples.get(i).getWeekday())
            {
                day = new Day(couples.get(i).getWeekday());
                days.add(day);
            }
            day.addCouple(couples.get(i));
        }
        return days;
    }
}