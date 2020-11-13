package com.agoni.blog.security.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.agoni.blog.cache.AbstractStringCacheStore;
import com.agoni.blog.config.properties.HaloProperties;
import com.agoni.blog.security.handler.ContentAuthenticationFailureHandler;
import com.agoni.blog.security.service.OneTimeTokenService;
import com.agoni.blog.service.OptionService;
import com.agoni.blog.utils.HaloUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Content filter
 *
 * @author johnniang
 * @date 19-5-6
 */
@Component
@Order(-1)
public class ContentFilter extends AbstractAuthenticationFilter {

    public ContentFilter(HaloProperties haloProperties,
            OptionService optionService,
            AbstractStringCacheStore cacheStore,
            OneTimeTokenService oneTimeTokenService) {
        super(haloProperties, optionService, cacheStore, oneTimeTokenService);

        addUrlPatterns("/**");

        String adminPattern = HaloUtils.ensureBoth(haloProperties.getAdminPath(), "/") + "**";
        addExcludeUrlPatterns(
                adminPattern,
                "/api/**",
                "/install",
                "/version",
                "/js/**",
                "/css/**");

        // set failure handler
        setFailureHandler(new ContentAuthenticationFailureHandler());
    }

    @Override
    protected String getTokenFromRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Do nothing
        filterChain.doFilter(request, response);
    }
}
