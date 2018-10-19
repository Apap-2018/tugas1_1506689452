package com.apap.tugas1.controller;

import java.util.List;
import java.math.BigInteger;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;

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
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;

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
    public String viewTermudaTertua(@RequestParam("id") BigInteger id, Model model){
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", new PegawaiModel());
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, BindingResult result, RedirectAttributes redirectAttrs) {
		pegawaiService.addPegawai(pegawai);
		redirectAttrs.addFlashAttribute("message", "Pegawai berhasil ditambahkan");
		return "redirect:/pegawai/tambah";
	}
	
	@RequestMapping(value = "/instansi", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "idInstansi", required = true) BigInteger idInstansi) {
	    InstansiModel instansi = instansiService.getDetailById(idInstansi);
	    return instansi; 
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
    public String searchPegawai(@RequestParam(value="provinsi", required = false) BigInteger provinsi,
                                @RequestParam(value="instansi", required = false) BigInteger instansi,
                                @RequestParam(value="jabatan", required = false) BigInteger jabatan, Model model){
        List<PegawaiModel> listPegawai = pegawaiService.getAllPegawai(provinsi, instansi, jabatan);
        InstansiModel hasilInstansi = instansiService.getDetailById(instansi);
        JabatanModel hasilJabatan = jabatanService.getDetailById(jabatan);

        for (int i = 0 ; i < listPegawai.size() ; i++){
            System.out.println(listPegawai.get(i));
        }
        
        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();

        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("listPegawai", listPegawai);

        model.addAttribute("hasilInstansi", hasilInstansi);
        model.addAttribute("hasilJabatan", hasilJabatan);
        return "cari-pegawai";
    }
}
