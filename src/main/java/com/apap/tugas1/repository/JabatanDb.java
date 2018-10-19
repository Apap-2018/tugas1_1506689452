package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanDb extends JpaRepository<JabatanModel, Long>{
	JabatanModel findById(BigInteger id);
}
