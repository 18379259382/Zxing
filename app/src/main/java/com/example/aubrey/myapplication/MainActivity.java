package com.example.aubrey.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int REQ_CODE = 1028;
    private Button mButton;
    private Button mret;
    private EditText mViewById;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.but);
        mViewById = (EditText) findViewById(R.id.test);
        mImageView = (ImageView)findViewById(R.id.iamge);
        mret = (Button)findViewById(R.id.ret);
        mret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mViewById.getText().toString().trim();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtils.create2DCode(content);//根据内容生成二维码
                    mImageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            showToast("扫码结果：" + result);

        }

    }

    private void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }
}
