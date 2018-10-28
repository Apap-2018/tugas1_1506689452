package com.apap.tugas1.service;

import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.model.ProvinsiModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public ProvinsiModel getDetailById(long id) {
		return provinsiDb.findById(id).get();
	}
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}
}
