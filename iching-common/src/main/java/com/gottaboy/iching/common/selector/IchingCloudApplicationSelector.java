package com.gottaboy.iching.common.selector;

import com.gottaboy.iching.common.configure.IchingAuthExceptionConfigure;
import com.gottaboy.iching.common.configure.IchingOAuth2FeignConfigure;
import com.gottaboy.iching.common.configure.IchingServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.Nonnull;

/**
 * @author gottaboy
 */
public class IchingCloudApplicationSelector implements ImportSelector {

    @Override
    @SuppressWarnings("all")
    public String[] selectImports(@Nonnull AnnotationMetadata annotationMetadata) {
        return new String[]{
                IchingAuthExceptionConfigure.class.getName(),
                IchingOAuth2FeignConfigure.class.getName(),
                IchingServerProtectConfigure.class.getName()
        };
    }
}
