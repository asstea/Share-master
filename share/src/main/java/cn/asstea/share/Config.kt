package cn.asstea.share

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   : 各大分享平台Id
 */
class Config {

    var APP_NAME: String = "美好志愿"
    var WX_ID: String = ""
    var QQ_ID: String = ""
    var WEIBO_KEY: String = ""
    val WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
    val WEIBO_SCOPE =
            "email,direct_messages_read,direct_messages_write," +
                    "friendships_groups_read,friendships_groups_write,statuses_to_me_read," +
                    "follow_app_official_microblog," + "invitation_write"


}