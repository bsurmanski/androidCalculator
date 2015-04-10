package com.bsurmanski.calculator;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Calculator calculator;
    TextView entryField;
    Button clearButton;
    Button[] numberButtons;
    Button dotButton;
    Button subButton;
    Button addButton;
    Button eqButton;
    String entryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();

        entryField = (TextView) findViewById(R.id.calcEntry);
        entryField.setText("" + calculator.getEntry());
        entryString = "0";

        clearButton = (Button) findViewById(R.id.cls);
        dotButton = (Button) findViewById(R.id.dot);
        subButton = (Button) findViewById(R.id.sub);
        addButton = (Button) findViewById(R.id.plus);
        eqButton = (Button) findViewById(R.id.eq);

        clearButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        subButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        eqButton.setOnClickListener(this);

        numberButtons = new Button[10];
        numberButtons[0] = (Button) findViewById(R.id.num0);
        numberButtons[1] = (Button) findViewById(R.id.num1);
        numberButtons[2] = (Button) findViewById(R.id.num2);
        numberButtons[3] = (Button) findViewById(R.id.num3);
        numberButtons[4] = (Button) findViewById(R.id.num4);
        numberButtons[5] = (Button) findViewById(R.id.num5);
        numberButtons[6] = (Button) findViewById(R.id.num6);
        numberButtons[7] = (Button) findViewById(R.id.num7);
        numberButtons[8] = (Button) findViewById(R.id.num8);
        numberButtons[9] = (Button) findViewById(R.id.num9);

        for(int i = 0; i < numberButtons.length; i++) {
            numberButtons[i].setOnClickListener(this);
        }
    }

    int getButtonId(Button b) {
        for(int i = 0; i < numberButtons.length; i++) {
            if(numberButtons[i] == b) return i;
        }
        return -1;
    }

    boolean isNumericButton(View b) {
        for(int i = 0; i < numberButtons.length; i++){
            if(numberButtons[i] == b) return true;
        }
        return false;
    }

    public void onClick(View v) {
        if(entryString.equals("ERROR")) entryString = "0";

        if(isNumericButton(v)) {
            int id = getButtonId((Button) v);
            if(entryString.equals("0")) entryString = "";
            entryString += Integer.toString(id);
        }

        if(v == dotButton) {
            if(!entryString.contains(".")) {
                entryString += ".";
            }
        }

        if(v == clearButton) {
            calculator.clear();
            entryString = "0";
        }

        try {
            if (v == subButton) {
                calculator.setEntry(entryString);
            }
        } catch (Exception e) {
            entryString = "ERROR";
        }

        entryField.setText(entryString);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
