package org.mapmark.security.config;

import org.springframework.security.core.Authentication;

public interface AuthFacade {
    Authentication getAuthentication();
}
