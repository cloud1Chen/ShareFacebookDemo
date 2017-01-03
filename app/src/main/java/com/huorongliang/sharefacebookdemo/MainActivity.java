package com.huorongliang.sharefacebookdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.huorongliang.sharefacebookdemo.utils.FaceBookShareUtils;
import com.huorongliang.sharefacebookdemo.utils.NetUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_share_photo;
    private Button bt_share_link;
    /**
     * 分享到facebook
     */
    private CallbackManager callBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_share_photo = (Button) findViewById(R.id.bt_share_photo);
        bt_share_link = (Button) findViewById(R.id.bt_share_Link);
        bt_share_photo.setOnClickListener(this);
        bt_share_link.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("LHRTAG", "onclick()");
        if (NetUtils.isConnectNetWork(this)) {
            switch (view.getId()) {
                case R.id.bt_share_photo:
                    shareFaceBook();
                    break;
                case R.id.bt_share_Link:
                    callBackManager = CallbackManager.Factory.create();
                    new FaceBookShareUtils(this, callBackManager, facebookCallback)
                            .shareLink("快来查看", "http://img.mp.itc.cn/upload/20161229/becd23f92b114ac89731e068c04ac0d3_th.jpeg", this.getString(R.string.app_site));
                    break;
            }
        } else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareFaceBook() {
        Log.d("LHRTAG", "shareFaceBook()");
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        callBackManager = CallbackManager.Factory.create();
        new FaceBookShareUtils(this, callBackManager, facebookCallback)
                .sharePhoto(getResources().getString(R.string.app_name), image, "");
    }

    /**
     * facebook分享状态回调
     */
    private FacebookCallback facebookCallback = new FacebookCallback() {

        @Override
        public void onSuccess(Object o) {
            Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            //            SysoutUtil.out("onSuccess" + o.toString());
            //            Message msg = Message.obtain();
            //            msg.what = SHARE_COMPLETE;
            //            mHandler.sendMessage(msg);
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            //            SysoutUtil.out("onCancel");
            //            Message msg = Message.obtain();
            //            msg.what = SHARE_CANCEL;
            //            mHandler.sendMessage(msg);
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            //            SysoutUtil.out("onError");
            //            ToastUtils.showToast("share error--" + error.getMessage());
            //            Message msg = Message.obtain();
            //            msg.what = SHARE_ERROR;
            //            mHandler.sendMessage(msg);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FaceBookShareUtils.SHARE_REQUEST_CODE == requestCode) {
            callBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
