package cn.hui.vote.web.controller;

import cn.hui.vote.integration.util.OkHttpUtils;
import cn.hui.vote.web.domain.Session;
import cn.hui.vote.web.util.SessionManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static String appKey = "1452314314";

    private static String appSecret = "36d5be80e0cbb2bb243685f479795074";

    private static String CALLBACK_URL_ACUTH = "http://www.huiclub.com/vote/login/auth/weibo/cb";

    private static String CALLBACK_URL_ACCESS = "http://www.huiclub.com/vote/login/auth/weibo/access_token_cb";

    private static String URL_AUTH = "https://api.weibo.com/oauth2/authorize";

    private static String URL_ACCESS = "https://api.weibo.com/oauth2/access_token";

    private static String URL_USER = "https://api.weibo.com/2/users/show.json";

    @RequestMapping("/token")
    public String getLoginUrl(@RequestParam("seed") String seed) {
        String token = Md5Crypt.md5Crypt((System.nanoTime() + seed).getBytes());
        return token;
    }

    @RequestMapping("/url")
    public String getLoginUrl(@RequestParam("token") String token, @RequestParam("formId") String formId) {
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("client_id", appKey);
        authParam.put("response_type", "code");
        authParam.put("redirect_uri", CALLBACK_URL_ACUTH);

        //微博登录携带本系统token与投票页id
        JSONObject stateJson = new JSONObject();
        stateJson.put("token", token);
        stateJson.put("formId", formId);
        String state = stateJson.toJSONString();
        authParam.put("state", state);
        String url = OkHttpUtils.buildUrl(URL_AUTH, authParam);
        return url;
    }

    @RequestMapping("/auth/weibo/cb")
    public String weiboCallback(@RequestParam("code") String code, @RequestParam("state") String state) {

        //获取access_token
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("client_id", appKey);
        authParam.put("client_secret", appSecret);
        authParam.put("grant_type", "authorization_code");
        authParam.put("redirect_uri", CALLBACK_URL_ACCESS);
        authParam.put("code", code);
        String authResp = OkHttpUtils.get(URL_ACCESS, authParam);
        JSONObject authJson = JSON.parseObject(authResp);
        String accessToken = authJson.getString("access_token");

        //获取用户信息
        Map<String, Object> showUserParam = new HashMap<>();
        showUserParam.put("access_token", accessToken);
        String userResp = OkHttpUtils.get(URL_USER, showUserParam);
        JSONObject userJson = JSON.parseObject(userResp);
        String userName = userJson.getString("screen_name");
        long uid = userJson.getLongValue("id");

        JSONObject stateJson = JSON.parseObject(state);
        String formId = stateJson.getString("formId");
        String token = stateJson.getString("token");
        Session session = SessionManager.getSession(token);
        session.setAccessToken(accessToken);
        session.setUserName(userName);
        session.setUid(uid);
        return "redirect:/vote/" + formId;

    }

    @RequestMapping("/auth/weibo/access_token_cb")
    public void accessTokenCallback() {
        LOGGER.info("accessTokenCallback");
    }
}
