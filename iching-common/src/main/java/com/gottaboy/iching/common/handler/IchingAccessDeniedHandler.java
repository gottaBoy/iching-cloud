package com.gottaboy.iching.common.handler;

import com.gottaboy.iching.common.entity.IchingResponse;
import com.gottaboy.iching.common.utils.IchingUtil;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gottaboy
 */
public class IchingAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        IchingResponse ichingResponse = new IchingResponse();
        IchingUtil.makeResponse(
                response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_FORBIDDEN, ichingResponse.message("没有权限访问该资源"));
    }
}
