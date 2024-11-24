package com.ctoutweb.lalamiam.infra.security;

import com.ctoutweb.lalamiam.infra.security.csrf.*;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.web.csrf.CsrfToken;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.COOKIE_CSRF_PARAM_NAME;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.HEADER_CSRF_PARAM_NAME;

public class CustomCsrfFilterTest {

  @Mock
  ICookieService cookieService;
  ICustomCsrfTokenRepository customCsrfTokenRepository = new CustomCsrfTokenRepositoryImpl(cookieService);
  CustomCsrfFilter customCsrfFilter;

  @BeforeEach
  public void init() {
    customCsrfFilter = new CustomCsrfFilter(customCsrfTokenRepository);
  }
  @Test
  public void areCsrfHeaderCookieEquals_should_be_false_when_csrf_not_equal() {
    /**
     * given
     */
    CsrfToken headerCsrfFormToken = new CsrfHeaderTokenImpl(HEADER_CSRF_PARAM_NAME, HEADER_CSRF_PARAM_NAME, "ff");
    CsrfToken csrfCookieToken = new CsrfCookieTokenImpl(COOKIE_CSRF_PARAM_NAME, COOKIE_CSRF_PARAM_NAME, "");

    /**
     * when
     */
    boolean actualResponse = customCsrfFilter.areCsrfHeaderCookieEquals(headerCsrfFormToken, csrfCookieToken);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }
  @Test
  public void areCsrfHeaderCookieEquals_should_be_false_when_csrfHeader_null() {
    /**
     * given
     */
    CsrfToken headerCsrfFormToken = null;
    CsrfToken csrfCookieToken = new CsrfCookieTokenImpl(COOKIE_CSRF_PARAM_NAME, COOKIE_CSRF_PARAM_NAME, "ff");

    /**
     * when
     */
    boolean actualResponse = customCsrfFilter.areCsrfHeaderCookieEquals(headerCsrfFormToken, csrfCookieToken);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }
  @Test
  public void areCsrfHeaderCookieEquals_should_be_false_when_csrfCookie_null() {
    /**
     * given
     */
    CsrfToken headerCsrfFormToken =  new CsrfHeaderTokenImpl(HEADER_CSRF_PARAM_NAME, HEADER_CSRF_PARAM_NAME, "ff");;
    CsrfToken csrfCookieToken = null;

    /**
     * when
     */
    boolean actualResponse = customCsrfFilter.areCsrfHeaderCookieEquals(headerCsrfFormToken, csrfCookieToken);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }
  @Test
  public void areCsrfHeaderCookieEquals_should_be_true_when_csrf_equal() {
    /**
     * given
     */
    CsrfToken headerCsrfFormToken = new CsrfHeaderTokenImpl(HEADER_CSRF_PARAM_NAME, HEADER_CSRF_PARAM_NAME, "ff");
    CsrfToken csrfCookieToken = new CsrfCookieTokenImpl(COOKIE_CSRF_PARAM_NAME, COOKIE_CSRF_PARAM_NAME, "ff");

    /**
     * when
     */
    boolean actualResponse = customCsrfFilter.areCsrfHeaderCookieEquals(headerCsrfFormToken, csrfCookieToken);

    /**
     * then
     */
    Assertions.assertTrue(actualResponse);
  }
}
