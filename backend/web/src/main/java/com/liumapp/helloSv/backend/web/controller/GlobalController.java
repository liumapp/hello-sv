package com.liumapp.helloSv.backend.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.utils.Consts;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class GlobalController {

    private Logger logger = Logger.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public RespInfo handleIOException(Exception ex) {
        logger.error("inner error:", ex);
        String message = "系统正忙,稍后再试";
        if (ex instanceof BizException) {
            message = ex.getMessage();
        }
        return new RespInfo(Consts.ERROR_CODE, ex.getLocalizedMessage(), message);
    }

    public void saveUploadResult(JSONArray jsonArray, Object object, String result, String errorReason) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        jsonObject.put("status", result);
        jsonObject.put("errorReason", errorReason);
        jsonArray.add(jsonObject);
    }
}