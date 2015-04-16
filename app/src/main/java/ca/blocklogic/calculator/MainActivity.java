package ca.blocklogic.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blocklogic.calculator.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Calculator calculator;
    TextView entryField;
    Button[] numberButtons;

    // portrait buttons
    Button clearButton;
    Button negateButton;
    Button dotButton;
    Button subButton;
    Button addButton;
    Button mulButton;
    Button divButton;
    Button eqButton;

    // landscape buttons
    Button altButton;
    Button randButton;
    Button facButton;
    Button modButton;
    Button sqButton;
    Button powButton;
    Button logButton;
    Button lnButton;
    Button radButton;
    Button piButton;
    Button sinButton;
    Button cosButton;
    Button tanButton;
    Button sinhButton;
    Button coshButton;
    Button tanhButton;

    String entryString;

    boolean clearOnNextInput;
    boolean altOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();

        entryField = (TextView) findViewById(R.id.calcEntry);
        entryField.setText("" + calculator.getEntry());
        entryString = "0";
        clearOnNextInput = true;
        altOperator = false;

        // number buttons
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

        // portrait buttons
        clearButton = (Button) findViewById(R.id.cls);
        negateButton = (Button) findViewById(R.id.negate);
        dotButton = (Button) findViewById(R.id.dot);
        subButton = (Button) findViewById(R.id.sub);
        addButton = (Button) findViewById(R.id.plus);
        mulButton = (Button) findViewById(R.id.mul);
        divButton = (Button) findViewById(R.id.div);
        eqButton = (Button) findViewById(R.id.eq);

        clearButton.setOnClickListener(this);
        negateButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        subButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        mulButton.setOnClickListener(this);
        divButton.setOnClickListener(this);
        eqButton.setOnClickListener(this);

        //landscape buttons
        altButton = (Button) findViewById(R.id.alt);
        randButton = (Button) findViewById(R.id.rand);
        piButton = (Button) findViewById(R.id.pi);
        facButton = (Button) findViewById(R.id.fac);
        modButton = (Button) findViewById(R.id.mod);
        sqButton = (Button) findViewById(R.id.sq);
        powButton = (Button) findViewById(R.id.pow);
        radButton = (Button) findViewById(R.id.rad);
        logButton = (Button) findViewById(R.id.log);
        lnButton = (Button) findViewById(R.id.ln);
        sinButton = (Button) findViewById(R.id.sin);
        cosButton = (Button) findViewById(R.id.cos);
        tanButton = (Button) findViewById(R.id.tan);
        sinhButton = (Button) findViewById(R.id.sinh);
        coshButton = (Button) findViewById(R.id.cosh);
        tanhButton = (Button) findViewById(R.id.tanh);

        altButton.setOnClickListener(this);
        randButton.setOnClickListener(this);
        piButton.setOnClickListener(this);
        facButton.setOnClickListener(this);
        modButton.setOnClickListener(this);
        sqButton.setOnClickListener(this);
        powButton.setOnClickListener(this);
        radButton.setOnClickListener(this);
        logButton.setOnClickListener(this);
        lnButton.setOnClickListener(this);
        sinButton.setOnClickListener(this);
        cosButton.setOnClickListener(this);
        tanButton.setOnClickListener(this);
        sinhButton.setOnClickListener(this);
        coshButton.setOnClickListener(this);
        tanhButton.setOnClickListener(this);
    }

    void setAltLabels() {
        sinButton.setText("ASIN");
        cosButton.setText("ACOS");
        tanButton.setText("ATAN");
        sinhButton.setText("ASINH");
        coshButton.setText("ACOSH");
        tanhButton.setText("ACOSH");
        lnButton.setText("e^x");
        logButton.setText("10^x");
        piButton.setText("e");
        sqButton.setText("SQRT");
    }

    // instead of 'alt' labels of buttons
    void setNormalLabels() {
        sinButton.setText("SIN");
        cosButton.setText("COS");
        tanButton.setText("TAN");
        sinhButton.setText("SINH");
        coshButton.setText("COSH");
        tanhButton.setText("COSH");
        lnButton.setText("LN");
        logButton.setText("LOG");
        piButton.setText("PI");
        sqButton.setText("X2");
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

    private void doUnaryOp(Calculator.UnOp op) {
        try {
            calculator.setEntry(entryString);
            calculator.apply(op);
            entryString = Double.toString(calculator.getEntry());
            clearOnNextInput = true;
        } catch(Exception e) {
            entryString = "ERROR";
            clearOnNextInput = true;
        }
    }

    private void setBinaryOp(Calculator.BinOp op) {
        try {
            // allow operator chaining
            calculator.setEntry(entryString);
            calculator.apply();
            entryString = Double.toString(calculator.getEntry());

            calculator.setOperator(op);
            calculator.setEntry(entryString);
            clearOnNextInput = true;
        } catch(Exception e){
            entryString = "ERROR";
            clearOnNextInput = true;
        }
    }

    public void onClick(View v) {
        if (entryString.equals("ERROR")) entryString = "0";

        if (isNumericButton(v)) {
            int id = getButtonId((Button) v);
            if (clearOnNextInput) {
                entryString = "";
                clearOnNextInput = false;
            }
            entryString += Integer.toString(id);
        }

        if (v == dotButton) {
            if (!entryString.contains(".")) {
                entryString += ".";
            }
        }

        if (v == clearButton) {
            calculator.clear();
            entryString = "0";
            clearOnNextInput = true;
        }

        if (v == subButton) {
            setBinaryOp(Calculator.BinOp.SUB);
        }

        if (v == addButton) {
            setBinaryOp(Calculator.BinOp.ADD);
        }

        if (v == mulButton) {

            setBinaryOp(Calculator.BinOp.MUL);
        }

        if (v == divButton) {
            setBinaryOp(Calculator.BinOp.DIV);
        }
        if (v == eqButton) {
            try {
                calculator.setEntry(entryString);
                calculator.apply();
                entryString = Double.toString(calculator.getEntry());
                clearOnNextInput = true;
            } catch (Exception e) {
                entryString = "ERROR";
                clearOnNextInput = true;
            }
        }

        if (v == negateButton) {
            if (entryString.startsWith("-")) {
                entryString = entryString.substring(1);
            } else {
                entryString = "-" + entryString;
            }
        }

        // landscape buttons
        if (v == altButton) {
            altOperator = !altOperator;
            if(altOperator) {
                setAltLabels();
            } else {
                setNormalLabels();
            }
        }

        if (v == randButton) {
            doUnaryOp(Calculator.UnOp.RAND);
        }

        if (v == piButton) {
            try {
                if(altOperator) calculator.setEntry("2.718281828459045");
                else calculator.setEntry("3.141592653589793");
                entryString = Double.toString(calculator.getEntry());
                clearOnNextInput = true;
            } catch(Exception e) {
                entryString = "ERROR";
                clearOnNextInput = true;
            }
        }

        if (v == facButton) {
            doUnaryOp(Calculator.UnOp.FAC);
        }

        if(v == modButton) {
            setBinaryOp(Calculator.BinOp.MOD);
        }

        if(v == powButton) {
            setBinaryOp(Calculator.BinOp.POW);
        }

        if(v == sqButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.SQRT);
            else doUnaryOp(Calculator.UnOp.SQ);
        }

        if (v == logButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.TENX);
            else doUnaryOp(Calculator.UnOp.LOG);
        }

        if (v == lnButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.EX);
        else doUnaryOp(Calculator.UnOp.LN);
        }

        if (v == sinButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.ASIN);
            else doUnaryOp(Calculator.UnOp.SIN);
        }

        if (v == cosButton) {
            if(altOperator)  doUnaryOp(Calculator.UnOp.ACOS);
            else doUnaryOp(Calculator.UnOp.COS);
        }

        if (v == tanButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.ATAN);
            else doUnaryOp(Calculator.UnOp.TAN);
        }

        if (v == sinhButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.ASINH);
            else doUnaryOp(Calculator.UnOp.SINH);
        }

        if (v == coshButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.ACOSH);
            else doUnaryOp(Calculator.UnOp.COSH);
        }

        if (v == tanhButton) {
            if(altOperator) doUnaryOp(Calculator.UnOp.TANH);
            else doUnaryOp(Calculator.UnOp.TANH);
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
