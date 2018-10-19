package com.apap.tugas1.service;

import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.model.ProvinsiModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.math.BigInteger;


@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public ProvinsiModel getDetailById(BigInteger id) {
		return provinsiDb.findById(id);
	}
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}
}
