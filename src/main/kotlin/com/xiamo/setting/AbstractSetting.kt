package com.xiamo.setting

abstract class AbstractSetting<T:Any> constructor(
    open var name : String,
    open var description : String,
    open var defaultValue : T
) {

    open val value = defaultValue

}