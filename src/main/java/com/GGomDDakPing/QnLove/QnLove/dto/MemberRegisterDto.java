package com.GGomDDakPing.QnLove.QnLove.dto;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {

    @NotNull(message = "id 는 null 일 수 없습니다.")
    private String loginId;

    @NotNull(message = "비밀번호가 비어있습니다.")
    private String password;

    @NotNull(message = "이름이 비어있습니다.")
    private String name;

    @NotNull(message = "닉네임이 비어있습니다.")
    private String nickname;

    @NotNull(message = "성별이 비어있습니다.")
    private Long sex;

    @NotNull(message = "나이가 비어있습니다.")
    private Long age;

    @NotNull(message = "인스타그램 아이디가 비어있습니다.")
    private String instagramId;

    @NotNull(message = "프로필 이미지가 비어있습니다.")
    private String profileImage;

    @Builder
    public MemberRegisterDto(String loginId, String password, String name, String nickname, Long sex, Long age, String instagramId, String profileImage) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
        this.instagramId = instagramId;
        this.profileImage = profileImage;
    }

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .instagramId(instagramId)
                .profileImage(profileImage)
                .sex(sex)
                .age(age)
                .build();
    }

}
