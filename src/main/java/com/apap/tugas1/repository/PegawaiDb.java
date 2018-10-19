package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.math.BigInteger;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findByNip(String nip);
	
	@Query("SELECT p FROM PegawaiModel p, InstansiModel i JOIN p.jabatan j WHERE i.provinsi.id = :idProvinsi AND i.id = :idInstansi AND j.id = :idJabatan AND p.instansi = i.id")
    List<PegawaiModel> findPegawaiByProvinsi
    								(@Param("idProvinsi") BigInteger idProvinsi,
                                     @Param("idInstansi") BigInteger idInstansi,
                                     @Param("idJabatan") BigInteger idJabatan);
}
