package bootcamp.wssrs.domain.Member.repository;

import bootcamp.wssrs.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndStudentId(String email, String studentId);
}
