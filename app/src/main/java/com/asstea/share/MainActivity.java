package com.asstea.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import cn.asstea.share.Share;
import cn.asstea.share.entity.QQShareInfo;
import cn.asstea.share.entity.ShareInfo;
import cn.asstea.share.entity.ShareItemOnClickListener;
import cn.asstea.share.entity.ShareResult;
import cn.asstea.share.entity.ShareResultCallback;
import cn.asstea.share.entity.WeiBoShareInfo;
import cn.asstea.share.entity.WeixinShareInfo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ShareResult.INSTANCE.getACTIVITY_REQUEST_CODE() && resultCode == ShareResult.INSTANCE.getACTIVITY_RESULT_CODE()) {
            final Enum code = (Enum) data.getSerializableExtra(ShareResult.INSTANCE.getKEY_CODE_NAME());
            final ShareInfo shareInfo = data.getParcelableExtra(ShareResult.INSTANCE.getKEY_SHAREINFO_NAME());
            Toast.makeText(this, shareInfo.toString(), Toast.LENGTH_SHORT).show();
            if (code == ShareResult.Code.OK) {
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            } else if (code == ShareResult.Code.ERROR) {
                Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void one(View view) {
        Data1 data = new Data1();
        data.id = 10086L;
        Share.INSTANCE.ShareManager().show(App.shareKey1, this, data, new ShareItemOnClickListener<Data1>() {

            @Override
            public void click(@NotNull ShareInfo shareInfo, Data1 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                switch (shareInfo.getId()) {
                    case 0:
                        WeixinShareInfo weixinShareInfo = new WeixinShareInfo();
                        weixinShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.ShareManager().shareToWeixinFriends(weixinShareInfo);
                        break;
                    case 1:
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
                        weiBoShareInfo.setUrl("http:www.baidu.com");
                        weiBoShareInfo.setTitle("title");
                        weiBoShareInfo.setBitmap(bitmap);
                        Share.INSTANCE.ShareManager().shareWeibo(weiBoShareInfo);
                        break;
                    case 2:
                        QQShareInfo qqShareInfo = new QQShareInfo();
                        qqShareInfo.setUrl("http://www.baidu.com");
                        qqShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.ShareManager().shareToQQ(qqShareInfo);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "其他分享", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ShareResultCallback<Data1>() {
            @Override
            public void shareResult(ShareInfo shareInfo, ShareResult.Code code, Data1 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                if (code == ShareResult.Code.OK) {
                    Toast.makeText(MainActivity.this, "监听分享成功", Toast.LENGTH_SHORT).show();
                    Share.INSTANCE.ShareManager().hide();
                } else if (code == ShareResult.Code.ERROR) {
                    Toast.makeText(MainActivity.this, "监听分享失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void two(View view) {
        Data2 data = new Data2();
        data.id = 999999L;
        Share.INSTANCE.ShareManager().show(App.shareKey2, this, data, new ShareItemOnClickListener<Data2>() {

            @Override
            public void click(@NotNull ShareInfo shareInfo, Data2 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                switch (shareInfo.getId()) {
                    case 0:
                        WeixinShareInfo weixinShareInfo = new WeixinShareInfo();
                        weixinShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.ShareManager().shareToWeixinFriends(weixinShareInfo);
                        break;
                    case 1:
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
                        weiBoShareInfo.setUrl("http:www.baidu.com");
                        weiBoShareInfo.setTitle("title");
                        weiBoShareInfo.setBitmap(bitmap);
                        Share.INSTANCE.ShareManager().shareWeibo(weiBoShareInfo);
                        break;
                    case 2:
                        QQShareInfo qqShareInfo = new QQShareInfo();
                        qqShareInfo.setUrl("http://www.baidu.com");
                        qqShareInfo.setTitle("大海真可怕");
                        Share.INSTANCE.ShareManager().shareToQQ(qqShareInfo);
                        break;
                }
            }
        }, new ShareResultCallback<Data2>() {

            @Override
            public void shareResult(ShareInfo shareInfo, ShareResult.Code code, Data2 data) {
                Toast.makeText(MainActivity.this, "data.id:" + data.id, Toast.LENGTH_SHORT).show();
                if (code == ShareResult.Code.OK) {
                    Toast.makeText(MainActivity.this, "监听分享成功", Toast.LENGTH_SHORT).show();
                    Share.INSTANCE.ShareManager().hide();
                } else if (code == ShareResult.Code.ERROR) {
                    Toast.makeText(MainActivity.this, "监听分享失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
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
