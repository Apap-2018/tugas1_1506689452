package com.apap.tugas1.service;

import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.model.JabatanModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan){
		jabatanDb.save(jabatan);
	}
	
	@Override
	public JabatanModel getDetailById(Long id) {
		return jabatanDb.getOne(id);
	}
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}
	
	@Override
	public void updateJabatan(JabatanModel jabatanModel) {
		JabatanModel jabatan = jabatanDb.getOne(jabatanModel.getId());
		jabatan.setNama(jabatanModel.getNama());
		jabatan.setDeskripsi(jabatanModel.getDeskripsi());
		jabatan.setGaji_pokok(jabatanModel.getGaji_pokok());
		jabatanDb.save(jabatan);
	}
	
	@Override
	public void removeJabatan(Long id) {
		jabatanDb.deleteById(id);
	}
}
