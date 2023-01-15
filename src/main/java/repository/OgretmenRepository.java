package repository;

import entity.Ogretmen;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OgretmenRepository extends JpaRepository<Ogretmen, Long> {

    // derived query
    List<Ogretmen> findAllByAdLike(String ad, Sort sort);

    List<Ogretmen> findAllByAdLikeOrderByAdAsc(String ad);

    List<Ogretmen> findByAdLike(String ad);

    @Query(name = "findByOgretmenAd", value = "SELECT * FROM springboot.ogretmen WHERE Ad LIKE %:ad% order by ad asc", nativeQuery = true)
    List<Ogretmen> searchByAd(@Param("ad") String ad);
}