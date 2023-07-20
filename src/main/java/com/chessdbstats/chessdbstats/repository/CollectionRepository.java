package com.chessdbstats.chessdbstats.repository;

import com.chessdbstats.chessdbstats.model.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends CrudRepository<Collection, Long> {
}