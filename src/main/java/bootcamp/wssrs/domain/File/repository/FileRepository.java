package bootcamp.wssrs.domain.File.repository;

import bootcamp.wssrs.domain.File.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
