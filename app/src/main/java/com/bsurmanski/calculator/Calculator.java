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
    }

    enum UnOp {
        INV,
        SIN,
        COS,
        TAN,
        ASIN,
        ACOS,
        ATAN,
        SQRT,
        LOG,
        LN,
    }

    MathContext mc;

    BigDecimal ans; // previous entry
    BigDecimal entry; // current entry
    BinOp currentOp;

    public Calculator() {
        mc = new MathContext(20);
        ans = new BigDecimal(0, mc);
        entry = new BigDecimal(0, mc);
        currentOp = BinOp.ADD;
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
        }
        entry = ans;
    }

    void apply(UnOp op) {
        switch(op) {
            case INV:
                ans = new BigDecimal(1, mc).divide(ans, mc);
                break;
            case SIN:
                ans = new BigDecimal(Math.sin(entry.doubleValue()), mc);
                break;
            case COS:
                ans = new BigDecimal(Math.cos(entry.doubleValue()), mc);
                break;
        }
        entry = ans;
    }

    void clear() {
        entry = new BigDecimal(0, mc);
    }

    void setEntry(String str) throws ParseException {
        ans = entry;

        BigDecimal val = new BigDecimal(0, mc);
        boolean dot = false;
        boolean negative = false;
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c >= '0' && c <= '9') {
                int digit = c - '0';
                val = val.movePointRight(1);
                val = val.add(new BigDecimal(digit, mc), mc);
            } else if(c == '.' && !dot) {
                dot = true;
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
