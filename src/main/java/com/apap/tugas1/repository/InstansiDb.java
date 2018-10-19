package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

import com.apap.tugas1.model.InstansiModel;

public interface InstansiDb extends JpaRepository<InstansiModel, Long>{
	InstansiModel findById(BigInteger id);
}
