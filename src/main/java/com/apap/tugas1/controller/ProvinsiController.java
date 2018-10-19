package com.apap.tugas1.controller;

import java.util.List;
import java.math.BigInteger;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.ProvinsiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Controller
public class ProvinsiController {
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/provinsi/instansi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> findAllInstansi(@RequestParam(value = "id", required = true) BigInteger id) {
	    ProvinsiModel provinsi = provinsiService.getDetailById(id);
	    return provinsi.getProvinsi_instansi();
	}
}
