package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

import java.util.List;

public interface JabatanService {
	JabatanModel getDetailById(Long id);
	List<JabatanModel> getAllJabatan();
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan);
	void removeJabatan(Long id);
}

