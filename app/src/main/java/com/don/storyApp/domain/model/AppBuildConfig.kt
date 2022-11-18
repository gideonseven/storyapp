package com.don.storyApp.domain.model

import com.don.storyApp.util.Constant


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
data class AppBuildConfig(
    var prefName: String = Constant.TEXT_BLANK,
    var appDebug: Boolean = false
)