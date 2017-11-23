package cn.asstea.share

import android.app.Application

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   :
 */
object Share {

    fun app() = Ext.instance.app

    fun config() = Ext.instance.config

    fun shareManager(): ShareManager = Ext.instance.shareManager


    /**
     * 初始化类
     */
    class Ext {

        companion object {
            val instance: Ext by lazy {
                Ext()
            }
        }
        internal var app: Application? = null

        internal var config: Config = Config()

        internal val shareManager = ShareManagerImpl.registerInstance()

        fun init(app: Application, config: Config) {
            if (this.app == null) {
                this.app = app
            }
            this.config = config
        }


    }

}
