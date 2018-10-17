package com.apap.tugas1.controller;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.InstansiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		List<JabatanModel> jPegawai = pegawai.getJabatan();
		int gaji = (int) (jPegawai.get(0).getGaji_pokok() + 
					(jPegawai.get(0).getGaji_pokok() * (pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100)));
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jPegawai", jPegawai);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
    public String viewTermudaTertua(@RequestParam("id") Long id, Model model){
        InstansiModel instansi = instansiService.getDetailById(id);

        PegawaiModel pegawaiTermuda = instansi.getPegawaiTermuda();
        PegawaiModel pegawaiTertua = instansi.getPegawaiTertua();
        
        List<JabatanModel> jPegawaiTermuda = pegawaiTermuda.getJabatan();
        List<JabatanModel> jPegawaiTertua = pegawaiTertua.getJabatan();
        
        int gajiPegawaiTermuda = (int) (jPegawaiTermuda.get(0).getGaji_pokok() +
        					(jPegawaiTermuda.get(0).getGaji_pokok() * (pegawaiTermuda.getInstansi().getProvinsi().getPresentase_tunjangan()/100)));
        
        int gajiPegawaiTertua = (int) (jPegawaiTertua.get(0).getGaji_pokok() +
				(jPegawaiTertua.get(0).getGaji_pokok() * (pegawaiTertua.getInstansi().getProvinsi().getPresentase_tunjangan()/100)));

        model.addAttribute("pegawaiTermuda", pegawaiTermuda);
        model.addAttribute("jPegawaiTermuda", jPegawaiTermuda);
        model.addAttribute("gajiPegawaiTermuda", gajiPegawaiTermuda);

        model.addAttribute("pegawaiTertua", pegawaiTertua);
        model.addAttribute("jPegawaiTertua", jPegawaiTertua);
        model.addAttribute("gajiPegawaiTertua", gajiPegawaiTertua);
        return"view-termuda-tertua";
    }
}
