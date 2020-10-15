package com.seashine.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seashine.server.domain.ProductList;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.repositories.ProductListRepository;
import com.seashine.server.repositories.ProductRepository;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class ProductListService {

	@Autowired
	private ProductListRepository productListRepository;

	@Autowired
	private FactoryRepository factoryRepository;

	@Autowired
	private ProductRepository productRepository;

	public ProductList findById(Integer id) {
		Optional<ProductList> obj = productListRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + ProductList.class.getName()));
	}

	@Transactional
	public ProductList insert(ProductList productList) {
		productList.setId(null);
		productList = productListRepository.save(productList);

		return productList;
	}

	public void importProducts(MultipartFile file, Integer idProductList) throws IOException {
		ProductList productList = findById(idProductList);

		if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("xls")) {
			readXLS(file.getInputStream());
		} else {
			readXLSX(file.getInputStream());
		}
	}

	public void readXLS(InputStream is) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);

		searchRows(sheet.iterator());

		workbook.close();
	}

	public void readXLSX(InputStream is) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet sheet = workbook.getSheetAt(0);
		searchRows(sheet.iterator());

		workbook.close();
	}

	private void searchRows(Iterator<Row> rowIterator) {
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
			}
		}
	}
}
