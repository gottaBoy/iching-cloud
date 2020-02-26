package com.gottaboy.iching.common.interceptor;

import com.gottaboy.iching.common.entity.IchingResponse;
import com.gottaboy.iching.common.utils.IchingUtil;
import com.gottaboy.iching.common.entity.constant.IchingConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gottaboy
 */
public class IchingServerProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 从请求头中获取 Gateway Token
        String token = request.getHeader(IchingConstant.GATEWAY_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(IchingConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        // 校验 Gateway Token的正确性
        if (StringUtils.equals(zuulToken, token)) {
            return true;
        } else {
            IchingResponse IchingResponse = new IchingResponse();
            IchingUtil.makeResponse(response,MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_FORBIDDEN, IchingResponse.message("请通过网关获取资源"));
            return false;
        }
    }
}
