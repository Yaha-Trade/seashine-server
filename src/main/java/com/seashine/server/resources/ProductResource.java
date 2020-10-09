package com.seashine.server.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.Image;
import com.seashine.server.domain.Product;
import com.seashine.server.dto.ProductListDTO;
import com.seashine.server.services.ImageService;
import com.seashine.server.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findAll() {
		List<Product> products = productService.findAll();

		return ResponseEntity.ok().body(products);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		Product product = productService.findById(id);

		return ResponseEntity.ok().body(product);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProductListDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "rowsPerPage", defaultValue = "50") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "reference") String orderBy,
			@RequestParam(value = "orderByDirection", defaultValue = "ASC") String orderByDirection,
			@RequestParam(value = "reference", defaultValue = "") String reference,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "factoryName", defaultValue = "") String factoryName) {

		Page<Product> products = (Page<Product>) productService.findPage(page, linesPerPage, orderBy,
				orderByDirection.toUpperCase(), reference, description, factoryName);

		Page<ProductListDTO> productsListDTO = products.map(product -> new ProductListDTO(product));

		return ResponseEntity.ok().body(productsListDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Product product) {
		product = productService.insert(product);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Product product, @PathVariable Integer id) {
		product.setId(id);
		product = productService.update(product);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		productService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/image/{idProduct}", method = RequestMethod.POST)
	public ResponseEntity<List<Integer>> uploadFile(@RequestParam("images") MultipartFile[] files,
			@PathVariable Integer idProduct) {

		List<Integer> imageIds = productService.uploadImages(files, idProduct);

		return ResponseEntity.ok().body(imageIds);
	}

	@RequestMapping(value = "/image/{idImage}", produces = MediaType.IMAGE_JPEG_VALUE, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable Integer idImage) {
		Image image = imageService.findById(idImage);
		byte[] imageBytes = null;
		try {
			imageBytes = imageService.getImageBytes(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentLength(imageBytes.length);

		return ResponseEntity.ok().headers(headers).body(imageBytes);
	}

	@RequestMapping(value = "/image/{idProduct}/{idImage}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteImage(@PathVariable Integer idProduct, @PathVariable Integer idImage) {
		productService.deleteImageById(idProduct, idImage);

		return ResponseEntity.noContent().build();
	}

}
