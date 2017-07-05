package cn.asstea.share

import android.app.Application

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   :
 */
object Share {

    fun app() = Ext.app

    fun config() = Ext.config

    fun ShareManager(): ShareManager = Ext.shareManager


    /**
     * 初始化类
     */
    object Ext {

        internal var app: Application? = null

        internal var config: Config? = null

        internal val shareManager: ShareManager by lazy {
            ShareManagerImpl.registerInstance()
        }

        fun init(app: Application, config: Config) {
            if (this.app == null) {
                this.app = app
            }
            this.config = config
        }


    }

}
