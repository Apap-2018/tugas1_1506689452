package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "instansi")
public class InstansiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@JsonIgnore
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<PegawaiModel> instansi_pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_provinsi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private ProvinsiModel provinsi;

	public List<PegawaiModel> getInstansi_pegawai() {
		return instansi_pegawai;
	}

	public void setInstansi_pegawai(List<PegawaiModel> instansi_pegawai) {
		this.instansi_pegawai = instansi_pegawai;
	}

	public ProvinsiModel getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(ProvinsiModel provinsi) {
		this.provinsi = provinsi;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	
	public PegawaiModel getPegawaiTermuda() {
        Collections.sort(this.instansi_pegawai, Comparator.comparing(PegawaiModel::getTanggal_lahir));
        return this.instansi_pegawai.get(instansi_pegawai.size()-1);
    }

    public PegawaiModel getPegawaiTertua() {
    	Collections.sort(this.instansi_pegawai, Comparator.comparing(PegawaiModel::getTanggal_lahir));
        return this.instansi_pegawai.get(0);
    }
}
