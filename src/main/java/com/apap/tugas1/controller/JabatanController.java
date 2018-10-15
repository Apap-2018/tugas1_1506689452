package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah-jabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, BindingResult result, RedirectAttributes redirectAttrs) {
		jabatanService.addJabatan(jabatan);
		redirectAttrs.addFlashAttribute("message", "Jabatan berhasil ditambahkan");
		return "redirect:/jabatan/tambah";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(Model model) {
//		JabatanModel jabatan = new JabatanModel();
//		model.addAttribute("jabatan", jabatan);
//		List<JabatanModel> jabatan = new ArrayList<>();
//		model.addAttribute("jabatan", jabatan);
//		JabatanModel jabatan = jabatanService.getDetailById(id);
		List<JabatanModel> jabatans = jabatanService.getAllJabatan();
		model.addAttribute("jabatans", jabatans);
		return "view-jabatan";
	}
}
