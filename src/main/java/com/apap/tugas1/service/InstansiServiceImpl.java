package com.apap.tugas1.service;

import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.model.InstansiModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public InstansiModel getDetailById(Long id) {
		return instansiDb.getOne(id);
	}
	
	@Override
	public List<InstansiModel> getAllInstansi() {
		return instansiDb.findAll();
	}
}
