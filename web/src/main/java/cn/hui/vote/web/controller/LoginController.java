package cn.hui.vote.web.controller;

import cn.hui.vote.integration.util.OkHttpUtils;
import cn.hui.vote.web.domain.Session;
import cn.hui.vote.web.util.SessionManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/vote/login")
public class LoginController {

    Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static String appKey = "1452314314";

    private static String appSecret = "36d5be80e0cbb2bb243685f479795074";

    private static String CALLBACK_URL_ACUTH = "http://www.huiclub.cn/vote/login/auth/weibo/cb";

    private static String URL_AUTH = "https://api.weibo.com/oauth2/authorize";

    private static String URL_ACCESS = "https://api.weibo.com/oauth2/access_token";

    private static String URL_REMOVE_AUTH = "https://api.weibo.com/oauth2/revokeoauth2";

    private static String URL_USER = "https://api.weibo.com/2/users/show.json";

    @GetMapping("/token")
    public @ResponseBody String token(@RequestParam("seed") String seed) {
        String token = DigestUtils.md5DigestAsHex(seed.getBytes());
        SessionManager.newSession(token);
        return token;
    }

    @GetMapping("/needLogin")
    public @ResponseBody boolean needLogin(@RequestParam("token") String token) {
        Session session = SessionManager.getSession(token);
        if (session == null) {
            return true;
        }
        long timeDiff = System.currentTimeMillis() - session.getCreateTime();
        if (timeDiff > 60 * 30 * 1000) {
            logout(token);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/logout")
    public @ResponseBody String logout(@RequestParam("token") String token) {
        Session session = SessionManager.getSession(token);
        if (session == null) {
            return "";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", session.getAccessToken());
        String response = OkHttpUtils.get(URL_REMOVE_AUTH, param);
        SessionManager.removeSession(token);
        return response;
    }

    @RequestMapping("/url")
    public @ResponseBody String getLoginUrl(@RequestParam("token") String token, @RequestParam("formId") String formId) {
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("client_id", appKey);
        authParam.put("response_type", "code");
        authParam.put("redirect_uri", CALLBACK_URL_ACUTH);

        //微博登录携带本系统token与投票页id
        authParam.put("state", formId+","+token);
        String url = OkHttpUtils.buildUrl(URL_AUTH, authParam);
        return url;
    }

    @RequestMapping("/auth/weibo/cb")
    public String weiboCallback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) {

        //获取access_token
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("client_id", appKey);
        authParam.put("client_secret", appSecret);
        authParam.put("grant_type", "authorization_code");
        authParam.put("redirect_uri", CALLBACK_URL_ACUTH);
        authParam.put("code", code);
        String authResp = OkHttpUtils.postForm(URL_ACCESS, authParam);
        JSONObject authJson = JSON.parseObject(authResp);
        String accessToken = authJson.getString("access_token");
        long uid = authJson.getLongValue("uid");

        //获取用户信息
//        Map<String, Object> showUserParam = new HashMap<>();
//        showUserParam.put("access_token", accessToken);
//        showUserParam.put("uid", uid);
//        String userResp = OkHttpUtils.get(URL_USER, showUserParam);
//        JSONObject userJson = JSON.parseObject(userResp);
//        String userName = userJson.getString("screen_name");

        String[] idtToken = state.split(",");
        String formId = idtToken[0];
        String token = idtToken[1];
        Session session = SessionManager.getSession(token);
        session.setAccessToken(accessToken);
//        session.setUserName(userName);
        session.setUid(uid);

        response.setHeader("Access-Control-Allow-Credentials","true");
        Cookie cookie = new Cookie("wb_uid1", "" + uid);
        cookie.setDomain("huiclub.cn");
        cookie.setPath("/vote");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

//        try {
//            userName = URLEncoder.encode(userName, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Cookie cookie2 = new Cookie("username", userName);
//        cookie2.setDomain("huiclub.cn");
//        cookie2.setPath("/vote");
//        cookie2.setMaxAge(-1);
//        response.addCookie(cookie2);
//        System.out.println("uid:" +uid + ", username:"+userName);
        if (formId != null && Integer.parseInt(formId) == 999) {
            return "redirect:/vote/voteStatic.html";
        }
        if (formId != null && Integer.parseInt(formId) == 998) {
            return "redirect:/vote/voteShirt.html";
        }
        if (formId != null && Integer.parseInt(formId) == 997) {
            return "redirect:/vote/voteHat.html";
        }

        return "redirect:/vote/vote.html?formId=" + formId;

    }
}
