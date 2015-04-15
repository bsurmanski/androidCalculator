package com.bsurmanski.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;

/**
 * Created by brandon on 4/9/15.
 */
public class Calculator {
    enum BinOp {
        DIV,
        MUL,
        SUB,
        ADD,
        POW,
        MOD,
    }

    enum UnOp {
        INV,
        SIN,
        COS,
        TAN,
        ASIN,
        ACOS,
        ATAN,
        SINH,
        COSH,
        TANH,
        ASINH,
        ACOSH,
        ATANH,
        FAC,
        SQ,
        SQRT,
        LOG,
        LN,
        EX,
        TENX,
        RAND,
    }

    MathContext mc;

    BigDecimal ans; // previous entry
    BigDecimal entry; // current entry
    BinOp currentOp;
    boolean useDegrees = true;

    public Calculator() {
        mc = new MathContext(20);
        ans = new BigDecimal(0, mc);
        entry = new BigDecimal(0, mc);
        currentOp = BinOp.ADD;
    }

    public void setDegrees() {
        useDegrees = true;
    }

    public void setRadians() {
        useDegrees = false;
    }

    public void setOperator(BinOp op) {
        currentOp = op;
    }

    void apply() {
        switch(currentOp) {
            case DIV:
                ans = ans.divide(entry, mc);
                break;
            case MUL:
                ans = ans.multiply(entry, mc);
                break;
            case SUB:
                ans = ans.subtract(entry, mc);
                break;
            case ADD:
                ans = ans.add(entry, mc);
                break;
            case POW:
                ans = new BigDecimal(Math.pow(ans.doubleValue(), entry.doubleValue()), mc);
                break;
            case MOD:
                ans = new BigDecimal(ans.doubleValue() % entry.doubleValue(), mc);
                break;
        }
        entry = ans;
    }

    static double logGamma(double x) {
        double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
        double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
        return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
    }

    static double gamma(double x) { return Math.exp(logGamma(x)); }

    private double factorial(double i) {
        if(i < 0) {
            throw new IllegalArgumentException("factorial must be > 0");
        }

        double ret = gamma(i+1);
        if(i == Math.floor(i)) {
            ret = Math.round(ret);
        }
        return ret;
    }

    void apply(UnOp op) {
        double x = 0.0;
        switch(op) {
            case FAC:
                ans = new BigDecimal(factorial(entry.doubleValue()), mc);
                break;
            case SQRT:
                ans = new BigDecimal(Math.sqrt(entry.doubleValue()), mc);
                break;
            case SQ:
                ans = entry.multiply(entry, mc);
                break;
            case LOG:
                ans = new BigDecimal(Math.log10(entry.doubleValue()), mc);
                break;
            case LN:
                ans = new BigDecimal(Math.log(entry.doubleValue()), mc);
                break;
            case TENX:
                ans = new BigDecimal(Math.pow(10, entry.doubleValue()), mc);
                break;
            case EX:
                ans = new BigDecimal(Math.exp(entry.doubleValue()), mc);
                break;
            case INV:
                ans = new BigDecimal(1, mc).divide(ans, mc);
                break;
            case SIN:
                ans = new BigDecimal(Math.sin(entry.doubleValue()), mc);
                break;
            case COS:
                ans = new BigDecimal(Math.cos(entry.doubleValue()), mc);
                break;
            case TAN:
                ans = new BigDecimal(Math.tan(entry.doubleValue()), mc);
                break;
            case ASIN:
                ans = new BigDecimal(Math.asin(entry.doubleValue()), mc);
                break;
            case ACOS:
                ans = new BigDecimal(Math.acos(entry.doubleValue()), mc);
                break;
            case ATAN:
                ans = new BigDecimal(Math.atan(entry.doubleValue()), mc);
                break;
            case SINH:
                ans = new BigDecimal(Math.sinh(entry.doubleValue()), mc);
                break;
            case COSH:
                ans = new BigDecimal(Math.cosh(entry.doubleValue()), mc);
                break;
            case TANH:
                ans = new BigDecimal(Math.tanh(entry.doubleValue()), mc);
                break;
            case ASINH:
                x = entry.doubleValue();
                ans = new BigDecimal(Math.log(x + Math.sqrt(x*x + 1.0)), mc);
                break;
            case ACOSH:
                x = entry.doubleValue();
                ans = new BigDecimal(Math.log(x + Math.sqrt(x*x - 1.0)), mc);
                break;
            case ATANH:
                x = entry.doubleValue();
                ans = new BigDecimal(0.5*Math.log((x + 1.0) / (x - 1.0)), mc);
                break;
            case RAND:
                ans = new BigDecimal(Math.random(), mc);
                break;
        }
        entry = ans;
    }

    void clear() {
        entry = new BigDecimal(0, mc);
        currentOp = BinOp.ADD;
    }

    void setEntry(String str) throws ParseException {
        ans = entry;

        BigDecimal val = new BigDecimal(0, mc);
        int dot = 0;
        boolean negative = false;
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c >= '0' && c <= '9') {
                int digit = c - '0';
                if(dot == 0) {
                    val = val.movePointRight(1);
                    val = val.add(new BigDecimal(digit, mc), mc);
                } else {
                    val = val.add(new BigDecimal(digit, mc).movePointLeft(dot), mc);
                    dot++;
                }
            } else if(c == '.' && dot == 0) {
                dot = 1;
            } else if (c == '-' && i == 0) {
                negative = true;
            } else {
                    throw new ParseException("Invalid character in string", i);
                }
            }

            if(negative) {
                val = val.negate();
            }

            entry = val;
        }

    double getEntry() {
        return entry.doubleValue();
    }
}
