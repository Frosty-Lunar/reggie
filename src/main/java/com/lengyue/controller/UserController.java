package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lengyue.commons.BaseContext;
import com.lengyue.commons.Result;
import com.lengyue.entity.User;
import com.lengyue.service.UserService;
import com.lengyue.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Map map, HttpSession httpSession) {
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        Object codeInRedis = redisTemplate.opsForValue().get(phone);
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInRedis != null && codeInRedis.equals(code)) {
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                user.setName("林之谜");
                userService.save(user);
            }
            httpSession.setAttribute("user", user.getId());
            redisTemplate.delete(phone);
            return Result.success(user);
        }
        return Result.error("登录失败");
    }

    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
        log.info("phone：{}", user.getPhone());
        if (StringUtils.isNotEmpty(user.getPhone())) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码：{}", code);
/*            SMSUtils.sendMessage("瑞吉外卖",
                    "SMS_266905319", user.getPhone().toString(), code);*/
            redisTemplate.opsForValue().set(user.getPhone(), code, 5, TimeUnit.MINUTES);
            return Result.success("验证码发送成功！");
        }
        return Result.error("请重新输入手机号！");
    }

    @PostMapping("loginout")
    public Result<String> loginout() {
        // 移除当前用户
        BaseContext.remove();
        return Result.success("注销用户成功！");
    }
}
