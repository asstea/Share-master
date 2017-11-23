package com.asstea.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.asstea.share.Share;
import cn.asstea.share.entity.QQShareInfo;
import cn.asstea.share.entity.ShareInfo;
import cn.asstea.share.entity.ShareItemOnClickListener;
import cn.asstea.share.entity.ShareResult;
import cn.asstea.share.entity.ShareResultCallback;
import cn.asstea.share.entity.ShareResultCode;
import cn.asstea.share.entity.WeiBoShareInfo;
import cn.asstea.share.entity.WeixinShareInfo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ShareResult.ACTIVITY_REQUEST_CODE && resultCode == ShareResult.ACTIVITY_RESULT_CODE) {
            final Enum code = (Enum) data.getSerializableExtra(ShareResult.KEY_CODE_NAME);
            final ShareInfo shareInfo = data.getParcelableExtra(ShareResult.KEY_SHAREINFO_NAME);
            Toast.makeText(this, shareInfo.toString(), Toast.LENGTH_SHORT).show();
            if (code == ShareResultCode.OK) {
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            } else if (code == ShareResultCode.ERROR) {
                Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        Share.INSTANCE.shareManager().unAuthorize(this);
        super.onDestroy();
    }

    public void one(View view) {
        Data1 data = new Data1();
        data.id = 10086L;
        Share.INSTANCE.shareManager().show(App.shareKey1, this, data, new ShareItemOnClickListener<Data1>() {

            @Override
            public void click(ShareInfo shareInfo, Data1 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                switch (shareInfo.getId()) {
                    case 0:
                        WeixinShareInfo weixinShareInfo = new WeixinShareInfo();
                        weixinShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.shareManager().shareToWeixinFriends(weixinShareInfo);
                        break;
                    case 1:
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
                        weiBoShareInfo.setUrl("http:www.baidu.com");
                        weiBoShareInfo.setTitle("title");
                        weiBoShareInfo.setBitmap(bitmap);
                        Share.INSTANCE.shareManager().shareWeibo(weiBoShareInfo);
                        break;
                    case 2:
                        QQShareInfo qqShareInfo = new QQShareInfo();
                        qqShareInfo.setUrl("http://www.baidu.com");
                        qqShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.shareManager().shareToQQ(qqShareInfo);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "其他分享", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ShareResultCallback<Data1>() {
            @Override
            public void shareResult(ShareInfo shareInfo, ShareResultCode code, Data1 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                if (code == ShareResultCode.OK) {
                    Toast.makeText(MainActivity.this, "监听分享成功", Toast.LENGTH_SHORT).show();
                    Share.INSTANCE.shareManager().hide();
                } else if (code == ShareResultCode.ERROR) {
                    Toast.makeText(MainActivity.this, "监听分享失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void two(View view) {
        Data2 data = new Data2();
        data.id = 999999L;
        Share.INSTANCE.shareManager().show(App.shareKey2, this, data, new ShareItemOnClickListener<Data2>() {

            @Override
            public void click(ShareInfo shareInfo, Data2 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                switch (shareInfo.getId()) {
                    case 0:
                        WeixinShareInfo weixinShareInfo = new WeixinShareInfo();
                        weixinShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.shareManager().shareToWeixinFriends(weixinShareInfo);
                        break;
                    case 1:
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
                        weiBoShareInfo.setUrl("http:www.baidu.com");
                        weiBoShareInfo.setTitle("title");
                        weiBoShareInfo.setBitmap(bitmap);
                        Share.INSTANCE.shareManager().shareWeibo(weiBoShareInfo);
                        break;
                    case 2:
                        QQShareInfo qqShareInfo = new QQShareInfo();
                        qqShareInfo.setUrl("http://www.baidu.com");
                        qqShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.shareManager().shareToQQ(qqShareInfo);
                        break;
                }
            }
        }, new ShareResultCallback<Data2>() {

            @Override
            public void shareResult(ShareInfo shareInfo, ShareResultCode code, Data2 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                if (code == ShareResultCode.OK) {
                    Toast.makeText(MainActivity.this, "监听分享成功", Toast.LENGTH_SHORT).show();
                    Share.INSTANCE.shareManager().hide();
                } else if (code == ShareResultCode.ERROR) {
                    Toast.makeText(MainActivity.this, "监听分享失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void three(View view) {
        startActivity(new Intent(this, WeiboShareActivity.class));
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
//        weiBoShareInfo.setUrl("http:www.baidu.com");
//        weiBoShareInfo.setTitle("title");
//        weiBoShareInfo.setBitmap(bitmap);
//
//        Share.INSTANCE.shareManager().shareWeibo(weiBoShareInfo, new ShareResultCallback1() {
//            @Override
//            public void shareResult(ShareResultCode code) {
//                if (code == ShareResultCode.OK) {
//                    Toast.makeText(MainActivity.this, "监听分享成功", Toast.LENGTH_SHORT).show();
//                } else if (code == ShareResultCode.ERROR) {
//                    Toast.makeText(MainActivity.this, "监听分享失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public void four_onClick(View view) {

//        WeixinShareInfo weixinShareInfo = new WeixinShareInfo();
//        weixinShareInfo.setTitle("大海真可怕");
//        Share.INSTANCE.shareManager().shareToWeixinFriends(weixinShareInfo, new ShareResultCallback1() {
//            @Override
//            public void shareResult(ShareResultCode code) {
//                Toast.makeText(MainActivity.this, "code:" + code, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private static class Data1 {

        public long id;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    '}';
        }
    }

    private static class Data2 {

        public long id;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    '}';
        }
    }
}
