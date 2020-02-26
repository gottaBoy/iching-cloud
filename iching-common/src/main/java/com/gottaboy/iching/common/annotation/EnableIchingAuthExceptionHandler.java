package com.gottaboy.iching.common.annotation;

import com.gottaboy.iching.common.configure.IchingAuthExceptionConfigure;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author gottaboy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IchingAuthExceptionConfigure.class)
public @interface EnableIchingAuthExceptionHandler {
}
