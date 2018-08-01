package com.xuwei.order.gaodemaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        GetDriveDisUtil getDriveDisUtil=new GetDriveDisUtil(this);
        getDriveDisUtil.convertpoint("广州市乐天创意园", "深圳市海岸城东座");

    }
}
