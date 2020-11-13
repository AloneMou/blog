package com.agoni.blog.security.authentication;

import org.springframework.lang.NonNull;
import com.agoni.blog.security.support.UserDetail;

/**
 * Authentication.
 *
 * @author johnniang
 */
public interface Authentication {

    /**
     * Get user detail.
     *
     * @return user detail
     */
    @NonNull
    UserDetail getDetail();
}
