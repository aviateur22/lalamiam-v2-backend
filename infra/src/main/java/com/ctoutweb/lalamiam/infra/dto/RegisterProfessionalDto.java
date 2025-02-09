package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.infra.annotation.custom.PasswordConstraint;
import com.ctoutweb.lalamiam.infra.annotation.custom.file.FileNotEmpty;
import com.ctoutweb.lalamiam.infra.annotation.custom.file.FilePdfIfNotEmpty;
import com.ctoutweb.lalamiam.infra.annotation.custom.file.PdfFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class RegisterProfessionalDto {
        @NotNull(message = "{email.missing}")
        @NotBlank(message = "{email.missing}")
        @Email(message = "{email.bad.format}")
        private String email;

        @PasswordConstraint(message = "{password.bad.format}")
        private String password;

        @NotNull(message = "{nickname.missing}")
        @NotBlank(message = "{nickname.missing}")
        private String nickname;

        @NotNull(message = "{firstname.missing}")
        @NotBlank(message = "{firstname.missing}")
        private String firstName;

        @NotNull(message = "{lastname.missing}")
        @NotBlank(message = "{lastname.missing}")
        private String lastName;

        @NotNull(message = "{phone.missing}")
        @NotBlank(message = "{phone.missing}")
        private String phone;

        @PdfFile(message = "{professional.register.file.1.error}")
        @FileNotEmpty(message = "{professional.register.file.1.error}")
        private MultipartFile file1;

        @FilePdfIfNotEmpty(message = "{professional.register.file.2.error}")
        private MultipartFile file2;
        @NotNull(message = "{captcha.response.missing}")
        private UserCaptchaResponseDto userCaptchaResponse;

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getNickname() {
                return nickname;
        }

        public void setNickname(String nickname) {
                this.nickname = nickname;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public MultipartFile getFile1() {
                return file1;
        }

        public void setFile1(MultipartFile file1) {
                this.file1 = file1;
        }

        public MultipartFile getFile2() {
                return file2;
        }

        public void setFile2(MultipartFile file2) {
                this.file2 = file2 != null ? file2 : null;
        }

        public UserCaptchaResponseDto getUserCaptchaResponse() {
                return userCaptchaResponse;
        }

        public void setUserCaptchaResponse(UserCaptchaResponseDto userCaptchaResponse) {
                this.userCaptchaResponse = userCaptchaResponse;
        }
}
