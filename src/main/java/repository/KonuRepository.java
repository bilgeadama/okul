package repository;

import entity.Konu;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("my")
public interface KonuRepository extends JpaRepository<Konu, Long> {
}
