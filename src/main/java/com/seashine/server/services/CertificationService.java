package com.seashine.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Certification;
import com.seashine.server.domain.enums.CertificationStatus;
import com.seashine.server.repositories.CertificationRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class CertificationService {

	@Autowired
	private CertificationRepository certificationRepository;

	public Certification findById(Integer id) {
		Optional<Certification> obj = certificationRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Certification.class.getName()));
	}

	public void approve(Integer id) {
		Certification certification = findById(id);
		certification.setStatus(CertificationStatus.APPROVED.getCode());
		certificationRepository.save(certification);
	}

	public void reprove(Integer id) {
		Certification certification = findById(id);
		certification.setStatus(CertificationStatus.REPROVED.getCode());
		certificationRepository.save(certification);
	}

	public void open(Integer id) {
		Certification certification = findById(id);
		certification.setStatus(CertificationStatus.OPENED.getCode());
		certificationRepository.save(certification);
	}

	public void onApproval(Integer id) {
		Certification certification = findById(id);
		certification.setStatus(CertificationStatus.ON_APPROVAL.getCode());
		certificationRepository.save(certification);
	}
}
