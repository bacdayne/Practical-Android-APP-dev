package com.example.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    private String mOrderMessage;
    public static final String EXTRA_ORDER_MESSAGE = "com.example.droidcafe.extra.ORDER_MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(EXTRA_ORDER_MESSAGE, mOrderMessage);
                    startActivity(intent);

            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // Nạp tệp menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_order) {
            displayToast(getString(R.string.action_order_message));
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra(EXTRA_ORDER_MESSAGE, mOrderMessage);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_status) {
            displayToast(getString(R.string.action_status_message));
            return true;
        } else if (id == R.id.action_favorites) {
            displayToast(getString(R.string.action_favorites_message));
            return true;
        } else if (id == R.id.action_contact) {
            displayToast(getString(R.string.action_contact_message));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Hàm hiển thị Toast
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Hàm xử lý cho Donut
    public void showDonutOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage);
    }

    // Hàm xử lý cho Ice Cream Sandwich
    public void showIceCreamOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage);
    }

    // Hàm xử lý cho Froyo
    public void showFroyoOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage);
    }



}
