package bootcamp.wssrs.domain.Recruit.repository;

import bootcamp.wssrs.domain.Recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    List<Recruit> findByNoticeId(Long noticeId);
}
