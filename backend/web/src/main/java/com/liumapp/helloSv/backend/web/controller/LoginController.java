package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.model.User;
import com.liumapp.helloSv.backend.web.service.UserService;
import com.liumapp.helloSv.backend.web.utils.CacheUtil;
import com.liumapp.helloSv.backend.web.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
public class LoginController extends GlobalController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login/{timestamp}")
    public RespInfo login(@RequestBody User user, @PathVariable String timestamp) {
        User u = userService.getByNamePwd(user);
        int code = Consts.ERROR_CODE;
        if (u != null) {
            code = Consts.SUCCESS_CODE;
            String username = user.getName();
            CacheUtil.putCache(username + timestamp, userService.pullPermissions(username));
        }
        return new RespInfo(code, u, null);
    }

    @RequestMapping(value = "/pull/{key}")
    public RespInfo pull(@PathVariable String key) throws ExecutionException {

        return new RespInfo(Consts.SUCCESS_CODE, CacheUtil.getCache(key), null);
    }
}
