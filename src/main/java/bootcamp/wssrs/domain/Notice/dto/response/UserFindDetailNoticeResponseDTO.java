package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.Notice.entity.Notice;

public record UserFindDetailNoticeResponseDTO(
        String username,
        String studentId,
        Notice notice
) {}
