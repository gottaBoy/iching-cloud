package com.gottaboy.iching.common.annotation;

import com.gottaboy.iching.common.configure.IchingLettuceRedisConfigure;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author gottaboy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IchingLettuceRedisConfigure.class)
public @interface EnableIchingLettuceRedis {
}
