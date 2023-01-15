package repository;

import entity.Konu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KonuRepository extends JpaRepository<Konu, Long> {
}
