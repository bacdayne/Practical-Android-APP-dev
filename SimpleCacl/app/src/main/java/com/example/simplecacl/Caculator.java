package com.example.simplecacl;

public class Caculator {

    // Enum Operator để định nghĩa các phép toán
    public enum Operator {
        ADD, SUB, MUL, DIV
    }

    // Phương thức thực hiện phép cộng
    public double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    // Phương thức thực hiện phép trừ
    public double sub(double operand1, double operand2) {
        return operand1 - operand2;
    }

    // Phương thức thực hiện phép nhân
    public double mul(double operand1, double operand2) {
        return operand1 * operand2;
    }

    // Phương thức thực hiện phép chia
    public double div(double operand1, double operand2) {
        if (operand2 == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return operand1 / operand2;
    }
}
