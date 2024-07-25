package bootcamp.wssrs.domain.member.repository;

import bootcamp.wssrs.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndStudentId(String email, String studentId);
}
