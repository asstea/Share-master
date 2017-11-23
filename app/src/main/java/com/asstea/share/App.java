package com.asstea.share;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import cn.asstea.share.Config;
import cn.asstea.share.Share;
import cn.asstea.share.entity.ShareInfo;
import cn.asstea.share.entity.ShareItemData;

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   :
 */
public class App extends Application {

    public static String shareKey1 = "shareKey1";
    public static String shareKey2 = "shareKey2";

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config();
        // TODO: 2017/7/5 设置各大平台的key
        config.setWEIBO_KEY("150941052");
        config.setWX_ID("222222");
        config.setQQ_ID("222222");
        config.setAPP_NAME("啦啦啦");
        Share.Ext.Companion.getInstance().init(this, config);



        List<ShareInfo> ones1 = new ArrayList<>();
        ones1.add(new ShareInfo(0, R.mipmap.ic_launcher, "微信分享"));
        ones1.add(new ShareInfo(1, R.mipmap.ic_launcher, "微博分享"));
        ones1.add(new ShareInfo(2, R.mipmap.ic_launcher, "QQ分享"));
        ones1.add(new ShareInfo(3, R.mipmap.ic_launcher, "其他分享"));

        List<ShareInfo> twos1 = new ArrayList<>();

        List<ShareInfo> twos2 = new ArrayList<>();
        twos2.add(new ShareInfo(4, R.mipmap.ic_launcher, "其他分享"));
        twos2.add(new ShareInfo(5, R.mipmap.ic_launcher, "其他分享"));
        twos2.add(new ShareInfo(6, R.mipmap.ic_launcher, "其他分享"));
        ones1.add(new ShareInfo(7, R.mipmap.ic_launcher, "其他分享"));
        ShareItemData shareItemData1 = new ShareItemData(ones1, twos1);
        ShareItemData shareItemData2 = new ShareItemData(ones1, twos2);
        Share.INSTANCE.shareManager().putShareItemData(shareKey1, shareItemData1);
        Share.INSTANCE.shareManager().putShareItemData(shareKey2, shareItemData2);
    }

}
