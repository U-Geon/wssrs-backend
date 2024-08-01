package bootcamp.wssrs.domain.Notice.repository;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();

    Page<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
