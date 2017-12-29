package com.example.myucifood.cs125app_excelcheck;

//import android.app.Activity;
//import android.os.Bundle;
//
//public class CS125_activity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cs125_activity);
//    }
//}
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CS125_activity extends Activity {

    private CheckBox breakfast, lunch, dinner, snack, snacks, asian, american, mexican, drinks, food;
    private Button btn_search;
    static String TAG = "YelpLog";
    String breakfast_s = "";
    String lunch_s = "";
    String dinner_s = "";
    String snack_s = "";
    String snacks_s = "";
    String asian_s = "";
    String american_s = "";
    String mexican_s = "";
    String drinks_s = "";
    String food_s = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs125_activity);

        addListenerOnChkIos();
        addListenerOnButton();
    }

    public void addListenerOnChkIos() {

        breakfast = (CheckBox) findViewById(R.id.breakfast);

        breakfast.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(CS125_activity.this,
                            "Selected!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void addListenerOnButton() {
        //time
        breakfast = (CheckBox) findViewById(R.id.breakfast);
        lunch = (CheckBox) findViewById(R.id.lunch);
        dinner = (CheckBox) findViewById(R.id.dinner);
        snack = (CheckBox) findViewById(R.id.snack);

        //cuisine
        asian = (CheckBox) findViewById(R.id.asian);
        american = (CheckBox) findViewById(R.id.american);
        mexican = (CheckBox) findViewById(R.id.mexican);

        //food
        food = (CheckBox) findViewById(R.id.food);
        drinks = (CheckBox) findViewById(R.id.drinks);
        snacks = (CheckBox) findViewById(R.id.snacks);

        btn_search = (Button) findViewById(R.id.btn_search);


        breakfast_s = breakfast.getText().toString();
        lunch_s = lunch.getText().toString();
        dinner_s = dinner.getText().toString();
        snack_s = snack.getText().toString();
        snacks_s = snack.getText().toString();
        asian_s = asian.getText().toString();
        american_s = american.getText().toString();
        mexican_s = mexican.getText().toString();
        drinks_s = drinks.getText().toString();
        food_s = food.getText().toString();




        btn_search.setOnClickListener(new OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                final StringBuffer checking = readExcel(CS125_activity.this);
                result.append("Breakfast : ").append(breakfast.isChecked());
                result.append("\nLunch : ").append(lunch.isChecked());
                result.append("\nDinner :").append(dinner.isChecked());
                result.append("\nSnack :").append(snack.isChecked());
                result.append("\nSnacks :").append(snacks.isChecked());
                result.append("\nAsian Cuisine :").append(asian.isChecked());
                result.append("\nAmerican Cuisine :").append(american.isChecked());
                result.append("\nMexican Cuisine :").append(mexican.isChecked());
                result.append("\nFood :").append(food.isChecked());
                result.append("\nDrinks :").append(drinks.isChecked());



                Toast.makeText(CS125_activity.this, checking.toString(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }


    private StringBuffer readExcel(Context context) {
        ArrayList<String> result = new ArrayList<String>();
        StringBuffer restaurant_names = new StringBuffer();
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or readonly");
            System.exit(0);
        }
        try {
            //Create Input Stream [CHANGE FILE NAME HERE IF NEEDED]
            InputStream myInput = getAssets().open("yelp_test_update.xls");

            // Create a workbook using the File System
            Workbook myWorkBook = Workbook.getWorkbook(myInput);

            // Get the first sheet from workbook
            Sheet mySheet = myWorkBook.getSheet(0);

            if (breakfast.isChecked()) {
                List<Integer> breakfast_row = findRow (mySheet, "breakfast");
                if (breakfast_row.size() == 0) {
                    Toast.makeText(context, "There are no breakfast options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < breakfast_row.size()) {
                        //bring up breakfast options
                        String rest = get_restaurant(mySheet, breakfast_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        //Toast.makeText(context, "Restuarant3: ", Toast.LENGTH_SHORT).show();
                        ++i;
                    }
                }

            }

            if (lunch.isChecked()) {
                List<Integer> lunch_row = findRow (mySheet, "lunch");
                if (lunch_row.size() == 0) {
                    Toast.makeText(context, "There are no lunch options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //bring up lunch options
                    int i = 0;
                    while (i < lunch_row.size()) {
                        String rest = get_restaurant(mySheet, lunch_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        //Toast.makeText(context, "Restuarant2: ", Toast.LENGTH_SHORT).show();
                        ++i;
                    }
                }

            }

            if (dinner.isChecked()) {
                List<Integer> dinner_row = findRow (mySheet, "dinner");
                if (dinner_row.size() == 0) {
                    Toast.makeText(context, "There are no dinner options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //bring up dinner options
                    int i = 0;
                    while (i<dinner_row.size()) {
                        String rest = get_restaurant(mySheet, dinner_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        //Toast.makeText(context, "Restuarant1: ", Toast.LENGTH_SHORT).show();
                        ++i;
                    }
                }

            }
            if (snack.isChecked() || snacks.isChecked()) {
                List<Integer> snack_row = findRow (mySheet, "snack");
                if (snack_row.size() == 0) {
                    Toast.makeText(context, "There are no snack options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < snack_row.size()) {
                        //bring up snack options
                        String rest = get_restaurant(mySheet, snack_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        //Toast.makeText(context, "Restuarant3: ", Toast.LENGTH_SHORT).show();
                        ++i;
                    }
                }

            }

            if (asian.isChecked()) {
                List<Integer> asian_row = findRow (mySheet, "asian");
                if (asian_row.size() == 0) {
                    Toast.makeText(context, "There are no Asian cuisine options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < asian_row.size()) {
                        //bring up asian cuisine options
                        String rest = get_restaurant(mySheet, asian_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        ++i;
                    }
                }

            }
            if (american.isChecked()) {
                List<Integer> american_row = findRow (mySheet, "american");
                if (american_row.size() == 0) {
                    Toast.makeText(context, "There are no American cuisine options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < american_row.size()) {
                        //bring up american cuisine options
                        String rest = get_restaurant(mySheet, american_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        ++i;
                    }
                }

            }

            if (mexican.isChecked()) {
                List<Integer> mexican_row = findRow (mySheet, "mexican");
                if (mexican_row.size() == 0) {
                    Toast.makeText(context, "There are no Mexican cuisine options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < mexican_row.size()) {
                        //bring up Mexican cuisine options
                        String rest = get_restaurant(mySheet, mexican_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        ++i;
                    }
                }

            }

            if (drinks.isChecked()) {
                List<Integer> drinks_row = findRow (mySheet, "drink");
                if (drinks_row.size() == 0) {
                    Toast.makeText(context, "There are no drink options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < drinks_row.size()) {
                        //bring up drink options
                        String rest = get_restaurant(mySheet, drinks_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        ++i;
                    }
                }

            }

            if (food.isChecked()) {
                List<Integer> american_row = findRow (mySheet, "food");
                if (american_row.size() == 0) {
                    Toast.makeText(context, "There are no food options.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int i = 0;
                    while (i < american_row.size()) {
                        //bring up food. options
                        String rest = get_restaurant(mySheet, american_row.get(i));
                        result.add(rest);
                        restaurant_names.append(rest).append(", ");
                        ++i;
                    }
                }

            }


        }catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

            //System.exit(0);
            //e.prntStackTrace();
            }
        return restaurant_names;
    }


    private static List<Integer> findRow (Sheet sheet, String tofind) {
        int row = sheet.getRows();
        List<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < row; ++i) {
            for (int c = 9; c < 11; ++c) {
                Cell x = sheet.getCell(c, i);
                String cell_info = x.getContents().toString();
                //System.out.print(cell_info);
                System.out.print("hello world");
                if (cell_info.toLowerCase().contains(tofind.toLowerCase())) {
                    System.out.print("hello");
                    System.out.print(cell_info);
                    result.add(i);
                }
            }
        }
        System.out.print(result.get(1));
        return result;
    }

    private String get_restaurant (Sheet sheet, int row_num) {

        return sheet.getCell(0, row_num).getContents();
    }


    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}