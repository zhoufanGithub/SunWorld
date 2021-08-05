package com.steven.baselibrary.datapersistence;

/**
 * Created by zhoufan on 2018/2/24.
 * 暴露接口给具体类
 */

public interface IOFactory {

    IOHandler create(Class<? extends IOHandler> clazz);

}
