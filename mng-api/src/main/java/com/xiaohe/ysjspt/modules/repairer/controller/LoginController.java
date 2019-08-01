package com.xiaohe.ysjspt.modules.repairer.controller;//package com.xiaohe.ysjspt.controller;


import com.xiaohe.ysjspt.config.aspect.LogLogin;
import com.xiaohe.ysjspt.entity.CommonResult;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.modules.cache.CaptchaService;
import com.xiaohe.ysjspt.modules.repairer.entity.Repairer;
import com.xiaohe.ysjspt.modules.repairer.service.RepairerService;
import com.xiaohe.ysjspt.utils.JavaWebToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/repairers")
public class LoginController {

    @Autowired
    private RepairerService repairerService;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 维修工登录
     *
     * @param repairer
     * @return
     */
    @PostMapping(value = "/login")
    @LogLogin
    public Result login(@RequestBody Repairer repairer) {
        String userName = repairer.getLoginName();
        String password = repairer.getPwd();
        String key = repairer.getKey();
        String captcha = repairer.getCaptcha();
        String kaptcha = captchaService.getCaptcha(key);
        CommonResult result = new CommonResult();
        if (StringUtils.isBlank(kaptcha) || !kaptcha.equals(captcha)) {
            result.error(2004, "验证码错误");
            return result;
        }
        Repairer user = repairerService.findByLoginNameAndPwd(userName, password);
        if (user != null) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("userid", user.getId());
            String token = JavaWebToken.createJavaWebToken(m);

            //缓存
            user.setToken(token);
            captchaService.setRepairer(user);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userId", user.getId());
            map.put("username", userName);
            map.put("isRepairer", 1);
            result.setDatas(map);
            return result.ok();
        } else {
            result.error(6001, "用户名，密码不匹配");
            return result;
        }
    }

    /**
     * 登出接口
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result logout(@RequestParam(value = "repairerId") Integer repairerId) {
        Result result = new Result();
        if (repairerId != null) {
            System.out.println(captchaService.remove(repairerId));
        }
        return result.ok();
    }
}
