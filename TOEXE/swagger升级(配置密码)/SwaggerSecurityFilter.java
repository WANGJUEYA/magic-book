package com.zh.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * {@link org.apache.catalina.core.ApplicationFilterFactory#matchFiltersURL(String, String)}
 */
@Slf4j
@WebFilter(urlPatterns = {"/swagger*", "/swagger/*", "/swagger-resources/*", "/v3/*"}, filterName = "swaggerSecurityFilter")
public class SwaggerSecurityFilter implements Filter {

    public static final String SWAGGER_SECURITY_SESSION = "swaggerSecuritySession";

    /**
     * 开启安全校验
     */
    @Value("${system-config.swagger-security:true}")
    private boolean security = true;

    @Value("${system-config.swagger-username:cplh_swagger}")
    private String username;
    @Value("${system-config.swagger-password:ZHbootvue@swagger}")
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (this.security) {
            String swaggerSessionValue = (String) servletRequest.getSession().getAttribute(SWAGGER_SECURITY_SESSION);
            if (StringUtils.isNotBlank(swaggerSessionValue) && StringUtils.equals(swaggerSessionValue, this.username)) {
                // 如果session中有值且等于密码, 取消拦截
                chain.doFilter(request, response);
            } else {
                String auth = servletRequest.getHeader("Authorization");
                if (StringUtils.isNotBlank(auth)) {
                    String userAndPass = new String(Base64.getDecoder().decode(auth.substring(6)));
                    String[] upArr = userAndPass.split(":");
                    if (upArr.length != 2) {
                        this.writeForbiddenCode(httpServletResponse);
                        return;
                    }

                    String iptUser = upArr[0];
                    String iptPass = upArr[1];
                    if (StringUtils.isNotBlank(iptUser) && StringUtils.isNotBlank(iptPass)
                            && StringUtils.equals(iptUser, this.username) && StringUtils.equals(iptPass, this.password)) {
                        servletRequest.getSession().setAttribute(SWAGGER_SECURITY_SESSION, this.username);
                        chain.doFilter(request, response);
                        return;
                    }

                    this.writeForbiddenCode(httpServletResponse);
                    return;
                }
                this.writeForbiddenCode(httpServletResponse);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void writeForbiddenCode(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"input Swagger Basic userName & password \"");
        httpServletResponse.getWriter().write("You do not have permission to access this resource");
    }

}
