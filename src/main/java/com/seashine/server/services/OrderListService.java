package com.seashine.server.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seashine.server.domain.BatteryData;
import com.seashine.server.domain.History;
import com.seashine.server.domain.Image;
import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.Remark;
import com.seashine.server.domain.User;
import com.seashine.server.domain.enums.OrderHistoryAction;
import com.seashine.server.domain.enums.OrderStatus;
import com.seashine.server.repositories.OrderListRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.OrderListSpecs;

@Service
public class OrderListService {

	@Autowired
	private OrderListRepository orderListRepository;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private I18nService i18nService;

	@Autowired
	private CertificationService certificationService;

	@Autowired
	private ProductService productService;

	public OrderList findById(Integer id) {
		Optional<OrderList> obj = orderListRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + OrderList.class.getName()));
	}

	public List<OrderList> findAll() {
		return orderListRepository.findAll();
	}

	public Page<OrderList> findPage(Integer page, Integer linesPerPage, String orderBy, String orderByDirection,
			String name, String customer, String season, Boolean isApproval) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListRepository.findAll(getFilters(name, customer, season, isApproval), pageRequest);
	}

	@Transactional
	public OrderList insert(OrderList orderList, Integer userId) {
		History history = addHistory(userId, OrderHistoryAction.CREATED);

		orderList.setId(null);
		orderList.setQuantityOfContainers(0);
		orderList.setQuantityOfProducts(0);
		orderList.setTotalCubage(new BigDecimal("0"));
		orderList.setTotalPrice(new BigDecimal("0"));
		orderList.setTotalOfReferences(0);
		orderList.setTotalOfBoxes(0);
		orderList.setStatus(OrderStatus.OPENED.getCode());
		orderList.getHistories().add(history);
		orderList = orderListRepository.save(orderList);

		return orderList;
	}

	public OrderList update(OrderList orderList, Integer userId) {
		OrderList orderListDB = findById(orderList.getId());
		updateData(orderListDB, orderList, userId);
		return orderListRepository.save(orderListDB);
	}

	public void delete(Integer id) {
		try {
			OrderList orderListDB = findById(id);
			orderListDB.getHistories().size();
			List<History> histories = orderListDB.getHistories();
			orderListRepository.deleteById(id);
			historyService.deleteAll(histories);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderList has products!");
		}
	}

	private void updateData(OrderList orderListDB, OrderList orderList, Integer userId) {
		orderListDB.setName(orderList.getName());
		orderListDB.setPurchaseDate(orderList.getPurchaseDate());
		orderListDB.setSeason(orderList.getSeason());
		orderListDB.setHistories(orderList.getHistories());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.EDITED));
	}

	private Specification<OrderList> getFilters(String name, String customer, String season, Boolean isApproval) {
		Specification<OrderList> orderListSpecs = Specification.where(null);

		if (!name.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeByName(name));
		}

		if (!customer.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeByCustomerName(customer));
		}

		if (!season.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterLikeBySeasonName(season));
		}

		if (isApproval) {
			orderListSpecs = orderListSpecs.and(OrderListSpecs.filterByStatusApproval());
		}

		return orderListSpecs;
	}

	public void updateTotals(List<OrderListItem> orderItems, Integer idOrderList, Integer userId) {
		BigDecimal totalPrice = new BigDecimal("0");
		BigDecimal totalCubage = new BigDecimal("0");
		Integer quantityOfProducts = 0;
		Integer totalOfReferences = 0;
		Integer totalOfBoxes = 0;

		for (OrderListItem orderListItem : orderItems) {
			totalPrice = totalPrice.add(orderListItem.getTotalPrice());
			totalCubage = totalCubage.add(orderListItem.getTotalCubage());
			quantityOfProducts += orderListItem.getTotalQuantityOfPieces();
			totalOfBoxes += orderListItem.getQuantityOfBoxes();
			totalOfReferences++;
		}

		OrderList orderList = findById(idOrderList);
		orderList.setQuantityOfContainers((int) Math.ceil(totalCubage.doubleValue() / 67));
		orderList.setQuantityOfProducts(quantityOfProducts);
		orderList.setTotalCubage(totalCubage);
		orderList.setTotalPrice(totalPrice);
		orderList.setTotalOfReferences(totalOfReferences);
		orderList.setTotalOfBoxes(totalOfBoxes);

		update(orderList, userId);
	}

	private String checkOrderBy(String orderBy) {
		switch (orderBy) {
		case "customer":
			return "season.customer.name";
		}

		return orderBy;
	}

	public OrderList sendToApproval(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.ON_APPROVAL.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.SENT_TO_APPROVAL));

		List<OrderListItem> orderListItems = orderListDB.getOrderListItems();
		for (OrderListItem orderListItem : orderListItems) {
			certificationService.open(orderListItem.getProduct().getCertification().getId());
			productService.open(orderListItem.getProduct().getId());
		}

		return orderListRepository.save(orderListDB);
	}

	public OrderList approve(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.APPROVED.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.APPROVED));

		List<OrderListItem> orderListItems = orderListDB.getOrderListItems();
		for (OrderListItem orderListItem : orderListItems) {
			certificationService.onApproval(orderListItem.getProduct().getCertification().getId());
			productService.onApproval(orderListItem.getProduct().getId());
		}

		return orderListRepository.save(orderListDB);
	}

	public OrderList reprove(Integer id, Integer userId) {
		OrderList orderListDB = findById(id);
		orderListDB.setStatus(OrderStatus.REPROVED.getCode());

		orderListDB.getHistories().add(addHistory(userId, OrderHistoryAction.REPROVED));

		List<OrderListItem> orderListItems = orderListDB.getOrderListItems();
		for (OrderListItem orderListItem : orderListItems) {
			certificationService.open(orderListItem.getProduct().getCertification().getId());
			productService.open(orderListItem.getProduct().getId());
		}

		return orderListRepository.save(orderListDB);
	}

	private History addHistory(Integer userId, OrderHistoryAction value) {
		String message = "";

		switch (value) {
		case CREATED:
			message = "ordercreated";
			break;

		case EDITED:
			message = "orderedited";
			break;

		case SENT_TO_APPROVAL:
			message = "senttoapproval";
			break;

		case APPROVED:
			message = "approvedtheorder";
			break;

		case REPROVED:
			message = "reprovedtheorder";
			break;
		}

		History history = new History(null, new Timestamp(System.currentTimeMillis()), userService.findById(userId),
				message, 1);
		history = historyService.insert(history);

		return history;
	}

	public byte[] exportOrder(Integer id, User user) {
		Map<String, String> i18n = i18nService.getMapByLanguageId(user.getLanguage().getId());

		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Order List");

			String[] headers = { i18n.get("picture"), i18n.get("factory"), i18n.get("reference"),
					i18n.get("description"), i18n.get("englishdescription"), i18n.get("quantityofpieces"),
					i18n.get("composition"), i18n.get("model"), i18n.get("color"), i18n.get("sound"), i18n.get("light"),
					i18n.get("motor"), i18n.get("metalpart"), i18n.get("clip"), i18n.get("line"), i18n.get("battery"),
					i18n.get("specialrequirements"), i18n.get("unitproductsize"), i18n.get("packingsize"),
					i18n.get("packing"), i18n.get("price"), i18n.get("ordermasterqty"), i18n.get("quantityofpieces"),
					i18n.get("quantityinner"), i18n.get("totalcubage"), i18n.get("cartonsize"),
					i18n.get("quantityofcontainers"), i18n.get("quantityofboxespercontainer"),
					i18n.get("netweightwithpacking"), i18n.get("netweightwithoutpacking"), i18n.get("picturewithpack"),
					i18n.get("picturewithoutpack"), i18n.get("remarks") };

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.RED.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.TOP);

			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerCellStyle);
			}

			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cs.setVerticalAlignment(VerticalAlignment.BOTTOM);

			OrderList orderList = findById(id);
			List<OrderListItem> orderListItems = orderList.getOrderListItems();

			int rowNum = 1;
			for (OrderListItem orderListItem : orderListItems) {
				Row row = sheet.createRow(rowNum);

				Image image1 = findImageByOrder(orderListItem.getProduct().getImages(), 1);
				if (image1 != null) {
					int imageId1 = workbook.addPicture(getImage(image1.getId()), HSSFWorkbook.PICTURE_TYPE_JPEG);

					CreationHelper helper1 = workbook.getCreationHelper();

					Drawing drawing1 = sheet.createDrawingPatriarch();

					ClientAnchor anchor1 = helper1.createClientAnchor();
					anchor1.setCol1(0);
					anchor1.setRow1(rowNum);

					Picture pict1 = drawing1.createPicture(anchor1, imageId1);
					pict1.resize(1, 1);
				}

				int columnIndex = 1;

				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getFactory().getName());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getReference());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getDescription());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getEnglishDescription());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getQuantityOfPieces());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getComposition());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getCertification().getModel());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getCertification().getColor());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getSound() == 1 ? i18n.get("yes")
								: i18n.get("no"));
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getLight() == 1 ? i18n.get("yes")
								: i18n.get("no"));
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getMotor() == 1 ? i18n.get("yes")
								: i18n.get("no"));
				row.createCell(columnIndex++).setCellValue(
						orderListItem.getProduct().getCertification().getMetalPart() == 1 ? i18n.get("yes")
								: i18n.get("no"));
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getClip() == 1 ? i18n.get("yes")
								: i18n.get("no"));
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getLine() == 1 ? i18n.get("yes")
								: i18n.get("no"));

				List<BatteryData> batteries = orderListItem.getProduct().getCertification().getBatteries();
				String battery = "";
				for (BatteryData bt : batteries) {
					battery = battery + bt.getQuantity() + "x " + bt.getBatteryType().getName() + " "
							+ bt.getVoltage().getName() + " "
							+ i18n.get(bt.getIncluded() == 1 ? "included" : "notincluded") + "\n";
				}

				Cell cellBattery = row.createCell(columnIndex++);
				cellBattery.setCellValue(battery);
				cellBattery.setCellStyle(cs);

				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getCertification().getSpecialRequirements());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getProductLength().doubleValue() + " x "
								+ orderListItem.getProduct().getProductWidth().doubleValue() + " x "
								+ orderListItem.getProduct().getProductHeight().doubleValue());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getPackingLength().doubleValue() + " x "
								+ orderListItem.getProduct().getPackingWidth().doubleValue() + " x "
								+ orderListItem.getProduct().getPackingHeight().doubleValue());

				Cell cellPacking = row.createCell(columnIndex++);
				cellPacking.setCellValue(orderListItem.getProduct().getPacking().getEnglishName() + "\n"
						+ orderListItem.getProduct().getPacking().getChineseName());
				cellPacking.setCellStyle(cs);

				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getPrice().doubleValue());
				row.createCell(columnIndex++).setCellValue(orderListItem.getQuantityOfBoxes());
				row.createCell(columnIndex++).setCellValue(orderListItem.getTotalQuantityOfPieces());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getQuantityInner());
				row.createCell(columnIndex++).setCellValue(orderListItem.getTotalCubage().doubleValue());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getBoxLength().doubleValue() + " x "
								+ orderListItem.getProduct().getBoxWidth().doubleValue() + " x "
								+ orderListItem.getProduct().getBoxHeight().doubleValue());
				row.createCell(columnIndex++).setCellValue(orderList.getQuantityOfContainers());
				row.createCell(columnIndex++).setCellValue(orderListItem.getProduct().getQuantityOfBoxesPerContainer());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getNetWeightWithPacking().doubleValue());
				row.createCell(columnIndex++)
						.setCellValue(orderListItem.getProduct().getNetWeightWithoutPacking().doubleValue());

				Image image2 = findImageByOrder(orderListItem.getProduct().getImages(), 2);
				if (image2 != null) {
					int imageId2 = workbook.addPicture(getImage(image2.getId()), HSSFWorkbook.PICTURE_TYPE_JPEG);

					CreationHelper helper2 = workbook.getCreationHelper();

					Drawing drawing2 = sheet.createDrawingPatriarch();

					ClientAnchor anchor2 = helper2.createClientAnchor();
					anchor2.setCol1(columnIndex++);
					anchor2.setRow1(rowNum);

					Picture pict2 = drawing2.createPicture(anchor2, imageId2);
					pict2.resize(1, 1);
				}

				Image image3 = findImageByOrder(orderListItem.getProduct().getImages(), 3);
				if (image3 != null) {
					int imageId3 = workbook.addPicture(getImage(image3.getId()), HSSFWorkbook.PICTURE_TYPE_JPEG);

					CreationHelper helper3 = workbook.getCreationHelper();

					Drawing drawing3 = sheet.createDrawingPatriarch();

					ClientAnchor anchor3 = helper3.createClientAnchor();
					anchor3.setCol1(columnIndex++);
					anchor3.setRow1(rowNum);

					Picture pict3 = drawing3.createPicture(anchor3, imageId3);
					pict3.resize(1, 1);
				}

				List<Remark> remarks = orderListItem.getProduct().getRemarks();
				String remark = "";
				for (Remark re : remarks) {
					remark = remark + re.getName() + "\n";
				}

				Cell cellRemark = row.createCell(columnIndex++);
				cellRemark.setCellValue(remark);
				cellRemark.setCellStyle(cs);

				row.setHeight((short) 1000);

				rowNum++;
			}

			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}

			sheet.setColumnWidth(0, 3000);

			byteArrayOutputStream = new ByteArrayOutputStream();
			workbook.write(byteArrayOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteArrayOutputStream.toByteArray();
	}

	private byte[] getImage(Integer idImage) {
		Image image = imageService.findById(idImage);
		byte[] imageBytes = null;
		try {
			imageBytes = imageService.getImageBytes(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageBytes;
	}

	private Image findImageByOrder(List<Image> images, Integer order) {
		Image image = null;

		for (Image img : images) {
			if (img.getNrOrder() == order) {
				return img;
			}
		}

		return image;
	}

}
