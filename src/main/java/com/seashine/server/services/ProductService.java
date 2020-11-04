package com.seashine.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seashine.server.domain.BatteryData;
import com.seashine.server.domain.Certification;
import com.seashine.server.domain.Image;
import com.seashine.server.domain.Product;
import com.seashine.server.repositories.BatteryDataRepository;
import com.seashine.server.repositories.CertificationRepository;
import com.seashine.server.repositories.ProductRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.ProductSpecs;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private BatteryDataRepository batteryDataRepository;

	@Autowired
	private ImageService imageService;

	public Product findById(Integer id) {
		Optional<Product> obj = productRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Product.class.getName()));
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Page<Product> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String reference, String description, String factory) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection), orderBy);

		return productRepository.findAll(getFilters(reference, description, factory), pageRequest);
	}

	@Transactional
	public Product insert(Product product) {
		List<BatteryData> batteries = batteryDataRepository.saveAll(product.getCertification().getBatteries());
		product.getCertification().setBatteries(batteries);

		Certification certification = certificationRepository.save(product.getCertification());

		product.setId(null);
		product.setCertification(certification);
		product = productRepository.save(product);

		return product;
	}

	public Product update(Product product) {
		Product productDB = findById(product.getId());

		List<BatteryData> batteries = batteryDataRepository.saveAll(product.getCertification().getBatteries());
		product.getCertification().setBatteries(batteries);

		Certification certification = certificationRepository.save(product.getCertification());

		productDB.getRemarks().clear();
		productDB.getRemarks().addAll(product.getRemarks());

		product.setCertification(certification);
		updateData(productDB, product);

		return productRepository.save(productDB);
	}

	public void delete(Integer id) {
		try {
			Product product = findById(id);
			List<Image> images = product.getImages();
			productRepository.deleteById(id);
			for (Image image : images) {
				imageService.delete(image);
			}
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

	private Specification<Product> getFilters(String reference, String description, String factory) {
		Specification<Product> productsSpecs = Specification.where(null);

		if (!reference.equals("")) {
			productsSpecs = productsSpecs.and(ProductSpecs.filterLikeByReference(reference));
		}

		if (!description.equals("")) {
			productsSpecs = productsSpecs.and(ProductSpecs.filterLikeByDescription(description));
		}

		if (!factory.equals("")) {
			productsSpecs = productsSpecs.and(ProductSpecs.filterLikeByFactoryName(factory));
		}

		productsSpecs = productsSpecs.and(ProductSpecs.filterOnlyProductModels());

		return productsSpecs;
	}

	public List<Integer> uploadImages(MultipartFile[] files, Integer idProduct, Integer order) {
		List<Image> images = new ArrayList<Image>();
		List<Integer> imageIds = new ArrayList<Integer>();
		for (MultipartFile multipartFile : files) {
			Image image = imageService.saveImage(multipartFile, order);
			images.add(image);
			imageIds.add(image.getId());
			order++;
		}
		Product productDB = findById(idProduct);
		productDB.getImages().addAll(images);
		productDB.setQuantityOfImages(productDB.getImages().size());
		productRepository.save(productDB);

		return imageIds;
	}

	public void deleteImageById(Integer idProduct, Integer idImage) {
		Product productDB = findById(idProduct);
		productDB.getImages().remove(imageService.findById(idImage));
		productDB.setQuantityOfImages(productDB.getImages().size());
		productRepository.save(productDB);
		imageService.deleteById(idImage);
	}

}
