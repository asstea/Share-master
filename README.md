# Share-master
封装了QQ，微信，微博第三方分享，以及封装一个分享样式。

## 使用方式

1.初始化配置，建议在***Application***初始化


    Config config = new Config();
        // TODO: 2017/7/5 设置各大平台的key
        config.setWEIBO_KEY("222222");
        conig.setWX_ID("222222");
        config.setQQ_ID("222222");
        config.setAPP_NAME("222222");
        Share.Ext.INSTANCE.init(this, config);
            

    特别注意，需要在项目根目录 ../build.gradle文件中配置QQ_SCHEME(参考Demo)



2.可以配置多个分享类型表，如果你的项目当中需要在不同模块用到不同的分享内容，可以使用多个类型，如***Demo***中***App***中所示。


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
        
    Share.INSTANCE.ShareManager().putShareItemData(shareKey1, shareItemData1);
    Share.INSTANCE.ShareManager().putShareItemData(shareKey2, shareItemData2);
        
 
 3.分享
 参考***Demo***中***MainActivity***中的分享方法，支持在Activity中onActivityResult回调，或者是直接使用匿名内部类监听回调。
 
 4.主要Api
 
 
    //微信分享
    Share.INSTANCE.ShareManager().shareToWeixinFriends(weixinShareInfo);
      
    //微博分享
    Share.INSTANCE.ShareManager().shareWeibo(weiBoShareInfo);
        
    //QQ分享
    Share.INSTANCE.ShareManager().shareToQQ(qqShareInfo);
    
