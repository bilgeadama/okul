package repository;

import entity.DersOgrenci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DersOgrenciRepository extends JpaRepository<DersOgrenci, Long> {
}
