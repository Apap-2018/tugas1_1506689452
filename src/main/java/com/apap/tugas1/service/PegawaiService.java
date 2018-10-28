package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;
import java.util.List;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNIP(String nip);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
}

