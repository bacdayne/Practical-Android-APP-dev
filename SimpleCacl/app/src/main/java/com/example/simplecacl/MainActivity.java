package com.example.simplecacl;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
    private Caculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện
        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult = findViewById(R.id.tvResult);

        // Khởi tạo đối tượng Calculator
        mCalculator = new Caculator();
    }

    // Xử lý sự kiện khi nhấn vào các nút phép toán
    public void onOperatorClick(View view) {
        Caculator.Operator operator;

        if (view.getId() == R.id.btnAdd) {
            operator = Caculator.Operator.ADD;
        } else if (view.getId() == R.id.btnSub) {
            operator = Caculator.Operator.SUB;
        } else if (view.getId() == R.id.btnMul) {
            operator = Caculator.Operator.MUL;
        } else if (view.getId() == R.id.btnDiv) {
            operator = Caculator.Operator.DIV;
        } else {
            throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        // Gọi phương thức compute để thực hiện phép toán
        compute(operator);
    }


    private void compute(Caculator.Operator operator) {
        double operand1, operand2;

        try {
            operand1 = getOperand(etNumber1);
            operand2 = getOperand(etNumber2);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }

        double result;
        try {
            switch (operator) {
                case ADD:
                    result = mCalculator.add(operand1, operand2);
                    break;
                case SUB:
                    result = mCalculator.sub(operand1, operand2);
                    break;
                case MUL:
                    result = mCalculator.mul(operand1, operand2);
                    break;
                case DIV:
                    result = mCalculator.div(operand1, operand2);
                    break;
                default:
                    throw new IllegalStateException("Unexpected operator: " + operator);
            }

            // Cập nhật kết quả
            tvResult.setText(String.valueOf(result));
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private double getOperand(EditText operandEditText) {
        return Double.parseDouble(getOperandText(operandEditText));
    }

    private String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }
}
