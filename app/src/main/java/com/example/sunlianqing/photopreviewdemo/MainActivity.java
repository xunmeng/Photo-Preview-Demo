package com.example.sunlianqing.photopreviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sunlianqing.photopreviewdemo.model.TestData;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.button);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				PhotographyPreviewActivity.jump(MainActivity.this, TestData.getList(), 1);
			}
		});
	}
}
