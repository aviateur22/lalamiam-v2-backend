package com.ctoutweb.lalamiam.infra.dto.auth;

import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

/**
 * Données pour un captcha généré
 * @param captchaTitle String
 * @param captchaImage IImageBase64
 * @param captchaExpectedResponseId Long
 */
public record CaptchaGeneratedDto(String captchaTitle, IImageBase64 captchaImage, Long captchaExpectedResponseId) {
}
