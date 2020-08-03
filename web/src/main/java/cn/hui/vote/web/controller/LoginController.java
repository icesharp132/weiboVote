package cn.hui.vote.web.controller;

import cn.hui.vote.integration.util.OkHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static String appKey = "1452314314";

    private static String appSecret = "36d5be80e0cbb2bb243685f479795074";

    private static String CALLBACK_URL = "http://www.huiclub.com/vote/login/auth/weibo/access_token_cb";

    private static String URL_AUTH = "https://api.weibo.com/oauth2/access_token";

    private static String URL_USER = "https://api.weibo.com/2/users/show.json";

    @RequestMapping("/auth/weibo/cb")
    public String weiboCallback(HttpSession session, @RequestParam("code") String code, @RequestParam("state") String state) {
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("client_id", appKey);
        authParam.put("client_secret", appSecret);
        authParam.put("grant_type", "authorization_code");
        authParam.put("redirect_uri", CALLBACK_URL);
        authParam.put("code", code);
        String authResp = OkHttpUtils.get(URL_AUTH, authParam);
        JSONObject authJson = JSON.parseObject(authResp);
        String accessToken = authJson.getString("access_token");
        session.setAttribute("accessToken", accessToken);

        Map<String, Object> showUserParam = new HashMap<>();
        showUserParam.put("access_token", accessToken);
        String userResp = OkHttpUtils.get(URL_USER, showUserParam);
        JSONObject userJson = JSON.parseObject(userResp);
        String userName = userJson.getString("screen_name");
        long uid = userJson.getLongValue("id");
        session.setAttribute("user_name", userName);
        session.setAttribute("uid", uid);
        return "redirect:/vote/" + state;
    }

    @RequestMapping("/auth/weibo/access_token_cb")
    public void accessTokenCallback() {
        LOGGER.info("accessTokenCallback");
    }
}
