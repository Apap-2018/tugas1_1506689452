package com.apap.tugas1.service;

import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.model.PegawaiModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
    public PegawaiModel getPegawaiDetailByNIP(String nip){
        return  pegawaiDb.findByNip(nip);
    }
	
	@Override
	public void addPegawai(PegawaiModel pegawai){
		pegawaiDb.save(pegawai);
	}
	
	@Override
    public List<PegawaiModel> getAllPegawai() {
        return pegawaiDb.findAll();
    }
}
