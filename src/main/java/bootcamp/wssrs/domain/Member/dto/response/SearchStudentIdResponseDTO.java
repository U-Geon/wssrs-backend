package bootcamp.wssrs.domain.Member.dto.response;

import bootcamp.wssrs.domain.Member.entity.Member;

public record SearchStudentIdResponseDTO(
        String msg,
        String password
) {
    public SearchStudentIdResponseDTO(Member member) {
        this("ok", member.getPassword());
    }
}
