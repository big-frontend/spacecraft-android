package com.hawksjamesf.common.mvp

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/11/2018  Sun
 *
 * - 由于P层会持有V层对象，故，在进行网络请求之时，退出Activity等界面，需要释放持有V层的对象，否则容易leak。
 * - 通过rxjava中的compositionDisposable自动指定请求发送
 */
interface SpacecraftPresenter {

}