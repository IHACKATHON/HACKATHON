package com.foodwallet.repository;

import com.foodwallet.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where s.name like concat('%', :query, '%')")
    List<Store> findAllByNameLike(@Param("query") String query);
}
