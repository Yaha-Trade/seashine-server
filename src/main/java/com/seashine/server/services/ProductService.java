package com.seashine.server.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.Certification;
import com.seashine.server.domain.Product;
import com.seashine.server.repositories.CertificationRepository;
import com.seashine.server.repositories.ProductRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.CustomSpecification;
import com.seashine.server.specs.SearchCriteria;
import com.seashine.server.specs.SearchOperation;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CertificationRepository certificationRepository;

	public Product findById(Integer id) {
		Optional<Product> obj = productRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Product.class.getName()));
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Page<Product> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String reference, String description) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return productRepository.findAll(getFilters(reference, description), pageRequest);
	}

	@Transactional
	public Product insert(Product product) {
		Certification certification = certificationRepository.save(product.getCertification());

		product.setId(null);
		product.setCertification(certification);
		product = productRepository.save(product);

		return product;
	}

	public Product update(Product product) {
		Product productDB = findById(product.getId());
		Certification certification = certificationRepository.save(product.getCertification());
		product.setCertification(certification);
		updateData(productDB, product);
		return productRepository.save(productDB);
	}

	public void delete(Integer id) {
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Product has orders");
		}
	}

	private void updateData(Product productDB, Product product) {
		productDB.setReference(product.getReference());
		productDB.setDescription(product.getDescription());
		productDB.setBoxCubage(product.getBoxCubage());
		productDB.setBoxGrossWeight(product.getBoxGrossWeight());
		productDB.setBoxNetWeight(product.getBoxNetWeight());
		productDB.setBoxHeight(product.getBoxHeight());
		productDB.setBoxWidth(product.getBoxWidth());
		productDB.setBoxLength(product.getBoxLength());
		productDB.setNetWeightWithoutPacking(product.getNetWeightWithoutPacking());
		productDB.setNetWeightWithPacking(product.getNetWeightWithPacking());
		productDB.setFactory(product.getFactory());
		productDB.setPacking(product.getPacking());
		productDB.setPackingHeight(product.getPackingHeight());
		productDB.setPackingLength(product.getPackingLength());
		productDB.setPackingWidth(product.getPackingWidth());
		productDB.setPrice(product.getPrice());
		productDB.setProductHeight(product.getProductHeight());
		productDB.setProductLength(product.getProductLength());
		productDB.setProductWidth(product.getProductWidth());
		productDB.setQuantityInner(product.getQuantityInner());
		productDB.setQuantityOfBoxesPerContainer(product.getQuantityOfBoxesPerContainer());
		productDB.setQuantityOfPieces(product.getQuantityOfPieces());
		productDB.setQuantityOfPiecesPerContainer(product.getQuantityOfPiecesPerContainer());
		productDB.setCertification(product.getCertification());
	}

	private CustomSpecification<Product> getFilters(String reference, String description) {
		CustomSpecification<Product> specs = new CustomSpecification<Product>();

		if (!reference.equals("")) {
			specs.add(new SearchCriteria("reference", reference, SearchOperation.MATCH));
		}

		if (!description.equals("")) {
			specs.add(new SearchCriteria("description", description, SearchOperation.MATCH));
		}

		// TODO filter by factory and packing

		return specs;
	}

}
