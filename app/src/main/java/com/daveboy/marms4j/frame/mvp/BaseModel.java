package com.daveboy.marms4j.frame.mvp;

/**
 *@description model基类
 *@author mxm
 *@creatTime 2018/11/21  18:40
 */
public class BaseModel implements IContract.IModel {
    /**
     * 链式添加参数
     */
    protected ParamsBuilder getBuilder(){
        return new ParamsBuilder();
    }
}
