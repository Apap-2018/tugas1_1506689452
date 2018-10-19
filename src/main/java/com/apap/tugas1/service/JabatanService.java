package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

import java.util.List;
import java.math.BigInteger;

public interface JabatanService {
	JabatanModel getDetailById(BigInteger id);
	List<JabatanModel> getAllJabatan();
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan);
	void removeJabatan(JabatanModel jabatan);
}

