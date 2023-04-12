package com.paymentsystem.repository;

import com.paymentsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT e FROM Transaction e WHERE e.createdDateTime < :threshold")
    List<Transaction> findOlderThan(@Param("threshold") LocalDateTime threshold);
    Optional<Transaction> findTransactionByUuid(String uuid);

}
