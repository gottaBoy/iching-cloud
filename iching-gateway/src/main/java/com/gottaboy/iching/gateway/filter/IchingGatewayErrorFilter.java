package com.gottaboy.iching.gateway.filter;

import com.gottaboy.iching.common.entity.IchingResponse;
import com.gottaboy.iching.common.utils.IchingUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * zuul:
 *   SendErrorFilter:
 *     error:
 *       disable: true
 */
@Slf4j
@Component
public class IchingGatewayErrorFilter extends SendErrorFilter {

    @Override
    public Object run() {
        try {
            IchingResponse IchingResponse = new IchingResponse();
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            IchingResponse = resolveExceptionMessage(message, serviceId, IchingResponse);

            HttpServletResponse response = ctx.getResponse();
            IchingUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, IchingResponse
            );
            log.error("Zull sendError：{}", IchingResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private IchingResponse resolveExceptionMessage(String message, String serviceId, IchingResponse IchingResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return IchingResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return IchingResponse.message(serviceId + "服务不可用");
        }
        return IchingResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}