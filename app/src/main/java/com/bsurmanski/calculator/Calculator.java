package com.bsurmanski.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

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

    public Calculator() {
        mc = new MathContext(20);
        ans = new BigDecimal(0, mc);
        entry = new BigDecimal(0, mc);
    }

    void apply(BinOp op) {
        switch(op) {
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
}
