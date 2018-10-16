package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;

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
	private String viewJabatan(@RequestParam("id") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getDetailById(id);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	 private String viewAllJabatan(Model model) {
		 List<JabatanModel> listJabatan = jabatanService.getAllJabatan(); 
		 model.addAttribute("listJabatan", listJabatan);
		 return "viewall-jabatan";
	 }
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
    private String update(@RequestParam("id") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getDetailById(id);
		model.addAttribute("jabatan", jabatan);
        return "update-jabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
    private String updateSubmit(@ModelAttribute JabatanModel jabatan, BindingResult result, RedirectAttributes redirectAttrs) {
    	jabatan.setId(jabatan.getId());
    	jabatanService.updateJabatan(jabatan);
    	redirectAttrs.addFlashAttribute("message", "Jabatan berhasil diubah");
        return "redirect:/jabatan/view?id=" + jabatan.getId();
    }
    
    @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String delete(@RequestParam(value = "id") Long id, Model model) {
		try {
			jabatanService.removeJabatan(id);
			return "deleted";
		} catch (Exception e) {
			return "delete-fail";
		}	
	}
}
