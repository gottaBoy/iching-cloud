package com.gottaboy.iching.common.annotation;

import com.gottaboy.iching.common.configure.IchingOAuth2FeignConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author gottaboy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IchingOAuth2FeignConfigure.class)
public @interface EnableIchingOauth2FeignClient {
}
