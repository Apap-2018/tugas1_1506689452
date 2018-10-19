package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;

import java.util.List;
import java.math.BigInteger;

public interface InstansiService {
	InstansiModel getDetailById(BigInteger id);
	List<InstansiModel> getAllInstansi();
}

