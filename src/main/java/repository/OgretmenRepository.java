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
    public List<Ogretmen> findAllByAdLike(String ad, Sort sort);

    public List<Ogretmen> findAllByAdLikeOrderByAdAsc(String ad);

    public List<Ogretmen> findByAdLike(String ad);

    // yanlış query yazarak strack trace 'e bakılabilir
    @Query(name = "findByOgretmenAd", value = "SELECT * FROM springboot.ogretmen WHERE Ad LIKE %:ad% order by ad asc", nativeQuery = true)
    public List<Ogretmen> searchByAd(@Param("ad") String ad);
}