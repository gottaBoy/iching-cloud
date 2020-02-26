package com.gottaboy.iching.common.handler;

import com.gottaboy.iching.common.entity.IchingResponse;
import com.gottaboy.iching.common.utils.IchingUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gottaboy
 */
public class IchingAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        IchingResponse ichingResponse = new IchingResponse();
        IchingUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_UNAUTHORIZED, ichingResponse.message("token无效")
        );
    }
}
