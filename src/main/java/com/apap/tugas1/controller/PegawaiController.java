package com.apap.tugas1.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
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
		List<JabatanModel> jPegawai = pegawai.getJabatanList();
		int gaji = (int) (jPegawai.get(0).getGaji_pokok() + 
					(jPegawai.get(0).getGaji_pokok() * (pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100)));
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jPegawai", jPegawai);
		model.addAttribute("gaji", gaji);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
    public String viewTermudaTertua(@RequestParam("id") long id, Model model){
        InstansiModel instansi = instansiService.getDetailById(id);

        PegawaiModel pegawaiTermuda = instansi.getPegawaiTermuda();
        PegawaiModel pegawaiTertua = instansi.getPegawaiTertua();
        
        List<JabatanModel> jPegawaiTermuda = pegawaiTermuda.getJabatanList();
        List<JabatanModel> jPegawaiTertua = pegawaiTertua.getJabatanList();
        
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
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String searchPegawai(@RequestParam(value="idProvinsi",required=false) String idProvinsi,
								@RequestParam(value="idInstansi",required=false) String idInstansi,
								@RequestParam(value="idJabatan",required=false) String idJabatan,Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		List<PegawaiModel> listPegawai = pegawaiService.getAllPegawai();
		
		if ((idProvinsi==null || idProvinsi.equals("")) && (idInstansi==null||idInstansi.equals("")) && (idJabatan==null||idJabatan.equals(""))) {
		}
		else {
			if (idProvinsi!=null && !idProvinsi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai : listPegawai) {
					if(((Long)pegawai.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
						temp.add(pegawai);
					}
				}
				listPegawai = temp;
				model.addAttribute("idProvinsi", idProvinsi);
			}
			else {
				model.addAttribute("idProvinsi", "");
			}
		}
			if (idInstansi!=null&&!idInstansi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai : listPegawai) {
					if(((Long)pegawai.getInstansi().getId()).toString().equals(idInstansi)){
						temp.add(pegawai);
					}
				}
				listPegawai = temp;
				model.addAttribute("idInstansi", idInstansi);
			}
			else {
				model.addAttribute("idInstansi", "");
			}
			if (idJabatan!=null&&!idJabatan.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel pegawai : listPegawai) {
					for (JabatanModel jabatan : pegawai.getJabatanList()) {
						if(((Long)jabatan.getId()).toString().equals(idJabatan)) {
							temp.add(pegawai);
							break;
						}
					}
					
				}
				listPegawai = temp;
				model.addAttribute("idJabatan", idJabatan);
			}
			else {
				model.addAttribute("idJabatan", "");
			}
			model.addAttribute("listPegawai", listPegawai);
			return "cari-pegawai";
		}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatanList(new ArrayList<JabatanModel>());
		pegawai.getJabatanList().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params= {"submitPegawai"}, method = RequestMethod.POST)
    public String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai,Model model, BindingResult result, RedirectAttributes redirectAttrs) {
		String nip = "";
		
		nip += pegawai.getInstansi().getId();
		
		String[] tanggalLahir = pegawai.getTanggal_lahir().toString().split("-");
		String tanggalLahirStr = tanggalLahir[2] + tanggalLahir[1] + tanggalLahir[0].substring(2, 4);
		nip += tanggalLahirStr;
		
		nip += pegawai.getTahun_masuk();
		
		int temp = 1;
		for (PegawaiModel peg : pegawai.getInstansi().getInstansi_pegawai()) {
			if (peg.getTahun_masuk().equals(pegawai.getTahun_masuk()) && peg.getTanggal_lahir().equals(pegawai.getTanggal_lahir())) {
				temp += 1;
			}
		}
		nip += "0" + temp;
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		redirectAttrs.addFlashAttribute("message", "Pegawai dengan NIP "+ nip + " berhasil ditambahkan");
		return "redirect:/pegawai?nip="+pegawai.getNip();
    }
	
	@RequestMapping(value = "/pegawai/tambah", params= {"addRow"})
    public String addRowJabatan(final PegawaiModel pegawai, final BindingResult bindingResult, Model model) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList<JabatanModel>());
		}
		pegawai.getJabatanList().add(new JabatanModel());
    	model.addAttribute("pegawai", pegawai);
    	
    	List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "tambah-pegawai";
    }
	
	@RequestMapping(value="/pegawai/tambah", params= {"removeRow"})
	public String removeRowJabatan(
			final PegawaiModel pegawai, final BindingResult bindingResult, 
			final HttpServletRequest req, Model model) {
		final int rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pegawai.getJabatanList().remove(rowId);
	    model.addAttribute("pegawai", pegawai);
	    
	    List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam(value = "nip", required = true) String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		model.addAttribute("pegawai", pegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", params= {"submitPegawai"}, method = RequestMethod.POST)
    public String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai,Model model, BindingResult result, RedirectAttributes redirectAttrs) {
		String nip = "";
		
		nip += pegawai.getInstansi().getId();
		
		String[] tanggalLahir = pegawai.getTanggal_lahir().toString().split("-");
		String tanggalLahirStr = tanggalLahir[2] + tanggalLahir[1] + tanggalLahir[0].substring(2, 4);
		nip += tanggalLahirStr;
		
		nip += pegawai.getTahun_masuk();
		
		int temp = 1;
		for (PegawaiModel peg : pegawai.getInstansi().getInstansi_pegawai()) {
			if (peg.getTahun_masuk().equals(pegawai.getTahun_masuk()) && peg.getTanggal_lahir().equals(pegawai.getTanggal_lahir())) {
				temp += 1;
			}
		}
		nip += "0" + temp;
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		redirectAttrs.addFlashAttribute("message", "Pegawai dengan NIP "+ nip + " berhasil diubah");
		return "redirect:/pegawai?nip="+pegawai.getNip();
    }
	
	@RequestMapping(value = "/pegawai/ubah", params= {"addRow"})
    public String updateRowJabatan(final PegawaiModel pegawai, final BindingResult bindingResult, Model model) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList<JabatanModel>());
		}
		pegawai.getJabatanList().add(new JabatanModel());
    	model.addAttribute("pegawai", pegawai);
    	
    	List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "update-pegawai";
    }
	
	@RequestMapping(value="/pegawai/ubah", params= {"removeRow"})
	public String removeJabatan(
			final PegawaiModel pegawai, final BindingResult bindingResult, 
			final HttpServletRequest req, Model model) {
		final int rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pegawai.getJabatanList().remove(rowId);
	    model.addAttribute("pegawai", pegawai);
	    
	    List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "update-pegawai";
	}
}
