package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long>{
	ProvinsiModel findById(BigInteger id);
}
