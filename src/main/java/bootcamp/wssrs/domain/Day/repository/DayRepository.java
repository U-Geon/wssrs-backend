package bootcamp.wssrs.domain.Day.repository;

import bootcamp.wssrs.domain.Day.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
