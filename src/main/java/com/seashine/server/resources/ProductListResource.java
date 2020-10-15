package com.seashine.server.resources;

import java.io.IOException;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seashine.server.domain.ProductList;
import com.seashine.server.services.ProductListService;

@RestController
@RequestMapping(value = "/productlists")
public class ProductListResource {

	@Autowired
	private ProductListService productListService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ProductList productList) {
		productList = productListService.insert(productList);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productList.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/import/{idProductList}", method = RequestMethod.POST)
	public ResponseEntity<Void> importProducts(@RequestParam("importFile") MultipartFile file,
			@PathVariable Integer idProductList) {

		try {
			productListService.importProducts(file, idProductList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.noContent().build();
	}

}
