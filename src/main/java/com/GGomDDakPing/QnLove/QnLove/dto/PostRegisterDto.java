package com.GGomDDakPing.QnLove.QnLove.dto;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRegisterDto {
    @NotNull(message = "id 는 null 일 수 없습니다.")
    private String loginId;

    @NotNull(message = "비밀번호가 비어있습니다.")
    private String password;

    @NotNull(message = "이름이 비어있습니다.")
    private String name;

    @NotNull(message = "이메일이 비어있습니다.")
    private String email;

    @NotNull(message = "성별이 비어있습니다.")
    private Long sex;
    @NotNull(message = "나이가 비어있습니다.")
    private Long age;
    @NotNull(message = "인스타그램 아이디가 비어있습니다.")
    private String instagramId;

    @NotNull(message = "프로필 이미지가 비어있습니다.")
    private String profileImage;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .instagramId(instagramId)
                .profileImage(profileImage)
                .sex(sex)
                .age(age)
                .build();
    }

}
