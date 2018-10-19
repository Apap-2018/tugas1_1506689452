package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;

import java.util.List;
import java.math.BigInteger;

public interface ProvinsiService {
	ProvinsiModel getDetailById(BigInteger id);
	List<ProvinsiModel> getAllProvinsi();
}

