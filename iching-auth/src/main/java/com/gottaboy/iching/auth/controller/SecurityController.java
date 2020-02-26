package com.gottaboy.iching.auth.controller;

import com.gottaboy.iching.auth.exception.IchingAuthException;
import com.gottaboy.iching.auth.manager.UserManager;
import com.gottaboy.iching.auth.service.ValidateCodeService;
import com.gottaboy.iching.common.entity.IchingResponse;
import com.gottaboy.iching.common.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author gottaboy
 */
@RestController
public class SecurityController {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    @DeleteMapping("signout")
    public IchingResponse signout(HttpServletRequest request) throws IchingAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        IchingResponse ichingResponse = new IchingResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new IchingAuthException("退出登录失败");
        }
        return ichingResponse.message("退出登录成功");
    }
}
