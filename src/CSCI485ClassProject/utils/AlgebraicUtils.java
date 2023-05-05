package CSCI485ClassProject.utils;

import CSCI485ClassProject.models.AlgebraicOperator;

public class AlgebraicUtils {
    public static Object computeINT(Object obj1, Object obj2, AlgebraicOperator op) {
        long val1;
        if (obj1 instanceof Integer) {
            val1 = Long.valueOf((Integer) obj1);
        } else {
            val1 = (long) obj1;
        }

        long val2;
        if (obj2 instanceof Integer) {
            val2 = Long.valueOf((Integer) obj2);
        } else {
            val2 = (long) obj2;
        }

        if (op == AlgebraicOperator.PLUS) {
            return val1 + val2;
        } else if (op == AlgebraicOperator.MINUS) {
            return val1 - val2;
        } else if (op == AlgebraicOperator.PRODUCT) {
            return val1 * val2;
        } else {
            return val1 / val2;
        }
    }

    public static Object computeDOUBLE(Object obj1, Object obj2, AlgebraicOperator op) {
        double val1 = (double) obj1;
        double val2 = (double) obj2;

        if (op == AlgebraicOperator.PLUS) {
            return val1 + val2;
        } else if (op == AlgebraicOperator.MINUS) {
            return val1 - val2;
        } else if (op == AlgebraicOperator.PRODUCT) {
            return val1 * val2;
        } else {
            return val1 / val2;
        }
    }

    public static Object computeVARCHAR(Object obj1, Object obj2, AlgebraicOperator op) {
        String val1 = (String) obj1;
        String val2 = (String) obj2;

        if (op == AlgebraicOperator.PLUS) {
            return val1 + val2;
        } else {
            return null;
        }
    }
}
