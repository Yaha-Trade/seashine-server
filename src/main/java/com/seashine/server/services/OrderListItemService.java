package com.seashine.server.services;

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
import org.springframework.web.bind.annotation.RequestHeader;

import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.Production;
import com.seashine.server.domain.enums.ProductionStatus;
import com.seashine.server.repositories.OrderListItemRepository;
import com.seashine.server.repositories.ProductionRepository;
import com.seashine.server.services.exception.DataIntegrityException;
import com.seashine.server.services.exception.ObjectNotFoundException;
import com.seashine.server.specs.OrderListItemSpecs;

@Service
public class OrderListItemService {

	@Autowired
	private OrderListItemRepository orderListItemRepository;

	@Autowired
	private OrderListService orderListService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductionRepository productionRepository;

	public OrderListItem findById(Integer id) {
		Optional<OrderListItem> obj = orderListItemRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + OrderListItem.class.getName()));
	}

	public Page<OrderListItem> getOrderListItems(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, Integer id, String factory, String productReference, String productDescription) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(
				getItemFilters(id, factory, productReference, productDescription, "", "", "", ""), pageRequest);
	}

	private Specification<OrderListItem> getItemFilters(Integer orderListId, String factory, String productReference,
			String productDescription, String customer, String season, String order, String quantityOfImages) {
		Specification<OrderListItem> orderListSpecs = Specification.where(null);

		if (orderListId != null) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterByOrderListId(orderListId));
		}

		if (!productReference.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByProductReference(productReference));
		}

		if (!season.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeBySeasonName(season));
		}

		if (!customer.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByCustomerName(customer));
		}

		if (!productDescription.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByProductDescription(productDescription));
		}

		if (!factory.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByFactoryName(factory));
		}

		if (!order.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLikeByOrderName(order));
		}

		if (!quantityOfImages.equals("")) {
			orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterByQuantity(quantityOfImages));
		}

		return orderListSpecs;
	}

	@Transactional
	public OrderListItem insert(OrderListItem orderListItem, Integer idOrderList,
			@RequestHeader("userId") Integer userId) {

		Production production = new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode(), null);

		productionRepository.save(production);

		orderListItem.setId(null);
		orderListItem.setProduction(production);
		orderListItem = orderListItemRepository.save(orderListItem);

		orderListService.updateTotals(
				orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "", "", "", "", "")), idOrderList,
				userId);

		return orderListItem;
	}

	public OrderListItem update(OrderListItem orderListItem, Integer idOrderList, Integer userId) {
		OrderListItem orderListItemDB = findById(orderListItem.getId());
		updateData(orderListItemDB, orderListItem);
		orderListItemRepository.save(orderListItemDB);

		orderListService.updateTotals(
				orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "", "", "", "", "")), idOrderList,
				userId);

		return orderListItemDB;
	}

	private void updateData(OrderListItem orderListItemDB, OrderListItem orderListItem) {
		orderListItemDB.setOrderList(orderListItem.getOrderList());
		orderListItemDB.setProduct(orderListItem.getProduct());
		orderListItemDB.setQuantityOfBoxes(orderListItem.getQuantityOfBoxes());
		orderListItemDB.setQuantityOfPiecesPerBox(orderListItem.getQuantityOfPiecesPerBox());
		orderListItemDB.setTotalCubage(orderListItem.getTotalCubage());
		orderListItemDB.setTotalPrice(orderListItem.getTotalPrice());
		orderListItemDB.setTotalQuantityOfPieces(orderListItem.getTotalQuantityOfPieces());
		orderListItemDB.setUnitPrice(orderListItem.getUnitPrice());
	}

	public void delete(Integer id, Integer idOrderList, Integer userId) {
		try {
			OrderListItem orderListItem = findById(id);
			orderListItemRepository.delete(orderListItem);
			orderListService.updateTotals(
					orderListItemRepository.findAll(getItemFilters(idOrderList, "", "", "", "", "", "", "")),
					idOrderList, userId);

			productService.delete(orderListItem.getProduct().getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("OrderListItem has orders!");
		}
	}

	public Integer checkIfProductIsInInOrder(Integer idOrderList, Integer idProductParent) {
		Specification<OrderListItem> orderListItemSpecs = Specification.where(null);
		orderListItemSpecs = orderListItemSpecs.and(getFilters(idProductParent, ""));
		orderListItemSpecs = orderListItemSpecs.and(getItemFilters(idOrderList, "", "", "", "", "", "", ""));

		List<OrderListItem> orderItemList = orderListItemRepository.findAll(orderListItemSpecs);

		return orderItemList.size() > 0 ? orderItemList.get(0).getId() : -1;
	}

	public Page<OrderListItem> findByParentProductId(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, Integer parentProductId, String customer) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(getFilters(parentProductId, customer), pageRequest);
	}

	private String checkOrderBy(String orderBy) {
		switch (orderBy) {
		case "customer":
			return "orderList.season.customer.name";
		case "factory":
			return "product.factory.name";
		case "season":
			return "orderList.season.name";
		case "order":
			return "orderList.name";
		case "quantityOfImages":
			return "product.quantityOfImages";
		}

		return orderBy;
	}

	private Specification<OrderListItem> getFilters(Integer parentProductId, String customer) {
		Specification<OrderListItem> orderListItemSpecs = Specification.where(null);

		if (!customer.equals("")) {
			orderListItemSpecs = orderListItemSpecs.and(OrderListItemSpecs.filterLikeByCustomerName(customer));
		}

		orderListItemSpecs = orderListItemSpecs.and(OrderListItemSpecs.filterByParentProductId(parentProductId));

		return orderListItemSpecs;
	}

	public Page<OrderListItem> getAllOrderListsItems(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, String customer, String season, String order, String factory,
			String productReference, String productDescription, String quantityOfImages) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(getItemFilters(null, factory, productReference, productDescription,
				customer, season, order, quantityOfImages), pageRequest);
	}

	public Page<OrderListItem> getAllOrderListItemsApproved(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, String customer, String season, String order, String factory,
			String productReference, String productDescription) {
		Specification<OrderListItem> orderListSpecs = Specification.where(null);

		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterOrderApproved());

		orderListSpecs = orderListSpecs
				.and(getItemFilters(null, factory, productReference, productDescription, customer, season, order, ""));

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(orderListSpecs, pageRequest);
	}

	public Page<OrderListItem> getAllProductsApproved(Integer page, Integer linesPerPage, String orderBy,
			String orderByDirection, String customer, String season, String order, String factory,
			String productReference, String productDescription) {
		Specification<OrderListItem> orderListSpecs = Specification.where(null);

		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterOrderApproved());
		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterLabelingApproved());
		orderListSpecs = orderListSpecs.and(OrderListItemSpecs.filterCertificationApproved());

		orderListSpecs = orderListSpecs
				.and(getItemFilters(null, factory, productReference, productDescription, customer, season, order, ""));

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(orderByDirection),
				checkOrderBy(orderBy));

		return orderListItemRepository.findAll(orderListSpecs, pageRequest);
	}
}
