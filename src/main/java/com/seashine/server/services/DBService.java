package com.seashine.server.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.config.SecurityConfig;
import com.seashine.server.domain.BatteryData;
import com.seashine.server.domain.BatteryType;
import com.seashine.server.domain.Certification;
import com.seashine.server.domain.Customer;
import com.seashine.server.domain.Factory;
import com.seashine.server.domain.I18n;
import com.seashine.server.domain.Language;
import com.seashine.server.domain.OrderList;
import com.seashine.server.domain.OrderListItem;
import com.seashine.server.domain.Packing;
import com.seashine.server.domain.Product;
import com.seashine.server.domain.Production;
import com.seashine.server.domain.Season;
import com.seashine.server.domain.ShowRoom;
import com.seashine.server.domain.User;
import com.seashine.server.domain.Voltage;
import com.seashine.server.domain.enums.CertificationStatus;
import com.seashine.server.domain.enums.LabelingStatus;
import com.seashine.server.domain.enums.Languages;
import com.seashine.server.domain.enums.OrderStatus;
import com.seashine.server.domain.enums.ProductionStatus;
import com.seashine.server.domain.enums.Profile;
import com.seashine.server.repositories.BatteryTypeRepository;
import com.seashine.server.repositories.CustomerRepository;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.repositories.I18nRepository;
import com.seashine.server.repositories.LanguageRepository;
import com.seashine.server.repositories.OrderListItemRepository;
import com.seashine.server.repositories.OrderListRepository;
import com.seashine.server.repositories.PackingRepository;
import com.seashine.server.repositories.ProductRepository;
import com.seashine.server.repositories.ProductionRepository;
import com.seashine.server.repositories.SeasonRepository;
import com.seashine.server.repositories.ShowRoomRepository;
import com.seashine.server.repositories.UserRepository;
import com.seashine.server.repositories.VoltageRepository;

@Service
public class DBService {

	@Autowired
	private SecurityConfig pe;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private I18nRepository i18nRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private FactoryRepository factoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PackingRepository packingRepository;

	@Autowired
	private VoltageRepository voltageRepository;

	@Autowired
	private BatteryTypeRepository batteryTypeRepository;

	@Autowired
	private ShowRoomRepository showRoomRepository;

	@Autowired
	private SeasonRepository seasonRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderListRepository orderListRepository;

	@Autowired
	private OrderListItemRepository orderListItemRepository;

	@Autowired
	private ProductionRepository productionRepository;

	public void instantiateTestDataBase() throws ParseException {
		Set<Integer> profiles = new HashSet<Integer>();
		profiles.add(Profile.ADMINISTRADOR.getCode());

		Language english = new Language(Languages.ENGLISH.getCode(), "English", "en");
		Language chinese = new Language(Languages.CHINESE.getCode(), "Chinese", "zh");

		User jean = new User(null, "Jean Briesemeister", "jean@yahacomex.com.br", "jean",
				pe.bCryptPasswordEncoder().encode("123"), english, profiles);

		User miranda = new User(null, "Rafael Miranda", "rafaelmiranda@yahacomex.com.br", "miranda",
				pe.bCryptPasswordEncoder().encode("456"), chinese, profiles);

		User doctor = new User(null, "Doutor Estranho", "doutor@yahacomex.com.br", "doctor",
				pe.bCryptPasswordEncoder().encode("789"), english, profiles);

		List<Factory> factoryList = new ArrayList<Factory>();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
		Random r = new Random();

		int i = 1;
		for (i = 0; i < 25; i++) {
			factoryList.add(new Factory(null, Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))), "" + (i * 2), "" + (i * 3),
					"" + (i * 4), "" + (i * 5), "" + (i * 6), "" + (i * 7), "" + (i * 8), "" + (i * 9), "" + (i * 10)));

			if (factoryList.size() == 100000) {
				factoryRepository.saveAll(factoryList);
				factoryList.clear();
			}
		}

		if (factoryList.size() > 0) {
			factoryRepository.saveAll(factoryList);
		}

		List<Customer> customerList = new ArrayList<Customer>();
		customerList.add(new Customer(null, "Yaha"));
		customerList.add(new Customer(null, "Jean"));
		customerList.add(new Customer(null, "Miranda"));
		customerList.add(new Customer(null, "Iron Man"));

		List<Packing> packingList = new ArrayList<Packing>();
		packingList.add(new Packing(null, "PVC", "透明蛋"));
		packingList.add(new Packing(null, "Display box", "包装"));
		packingList.add(new Packing(null, "Blister card", "透明蛋"));
		packingList.add(new Packing(null, "Window box", "三角开窗盒"));
		packingList.add(new Packing(null, "Insert card", "绑版盒"));

		List<Voltage> voltageList = new ArrayList<Voltage>();
		voltageList.add(new Voltage(null, "1,5V"));
		voltageList.add(new Voltage(null, "4,5V"));

		List<BatteryType> batteryTypeList = new ArrayList<BatteryType>();
		batteryTypeList.add(new BatteryType(null, "A"));
		batteryTypeList.add(new BatteryType(null, "AA"));
		batteryTypeList.add(new BatteryType(null, "AAA"));
		batteryTypeList.add(new BatteryType(null, "C"));
		batteryTypeList.add(new BatteryType(null, "D"));
		batteryTypeList.add(new BatteryType(null, "F"));
		batteryTypeList.add(new BatteryType(null, "AG13"));
		batteryTypeList.add(new BatteryType(null, "LR41"));
		batteryTypeList.add(new BatteryType(null, "LR54"));
		batteryTypeList.add(new BatteryType(null, "LR44"));
		batteryTypeList.add(new BatteryType(null, "9V"));
		batteryTypeList.add(new BatteryType(null, "AG3"));
		batteryTypeList.add(new BatteryType(null, "LF36"));
		batteryTypeList.add(new BatteryType(null, "LT56"));
		batteryTypeList.add(new BatteryType(null, "AG10"));

		List<ShowRoom> showRoomList = new ArrayList<ShowRoom>();
		showRoomList.add(new ShowRoom(null, "On Top"));
		showRoomList.add(new ShowRoom(null, "HK"));
		showRoomList.add(new ShowRoom(null, "LC"));
		showRoomList.add(new ShowRoom(null, "YS"));
		showRoomList.add(new ShowRoom(null, "CBH"));
		showRoomList.add(new ShowRoom(null, "Hong Teng"));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Season> seasonsList = new ArrayList<Season>();
		seasonsList.add(new Season(null, "First season", new java.sql.Date(sdf.parse("31/12/2020").getTime()),
				customerList.get(0)));
		seasonsList.add(new Season(null, "Second season", new java.sql.Date(sdf.parse("01/01/2021").getTime()),
				customerList.get(1)));
		seasonsList.add(new Season(null, "Third season", new java.sql.Date(sdf.parse("01/03/2021").getTime()),
				customerList.get(2)));

		List<BatteryData> batteryDataList = new ArrayList<BatteryData>();
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(1), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(2), voltageList.get(1)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(3), voltageList.get(1)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(1), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(2), voltageList.get(1)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(3), voltageList.get(1)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));
		batteryDataList.add(new BatteryData(null, 1, 1, 1, batteryTypeList.get(4), voltageList.get(0)));

		List<Certification> certificationList = new ArrayList<Certification>();
		certificationList.add(new Certification(null, "1", 1, "Composition 1", 1, "Red", 1, 1, 1, 1, 1, 1, "Special 1",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(0))));
		certificationList.add(new Certification(null, "2", 1, "Composition 2", 1, "Blue", 1, 1, 1, 1, 1, 1, "Special 2",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(1))));
		certificationList.add(new Certification(null, "3", 1, "Composition 3", 1, "Green", 1, 1, 1, 1, 1, 1,
				"Special 3", CertificationStatus.OPENED.getCode(),
				Arrays.asList(batteryDataList.get(2), batteryDataList.get(3))));
		certificationList.add(new Certification(null, "4", 1, "Composition 4", 1, "Red", 1, 1, 1, 1, 1, 1, "Special 4",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(4))));
		certificationList.add(new Certification(null, "5", 1, "Composition 5", 1, "Blue", 1, 1, 1, 1, 1, 1, "Special 5",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(5))));
		certificationList.add(new Certification(null, "6", 1, "Composition 6", 1, "Green", 1, 1, 1, 1, 1, 1,
				"Special 6", CertificationStatus.OPENED.getCode(),
				Arrays.asList(batteryDataList.get(6), batteryDataList.get(7))));
		certificationList.add(new Certification(null, "7", 1, "Composition 7", 1, "Red", 1, 1, 1, 1, 1, 1, "Special 7",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(8))));
		certificationList.add(new Certification(null, "8", 1, "Composition 8", 1, "Blue", 1, 1, 1, 1, 1, 1, "Special 8",
				CertificationStatus.OPENED.getCode(), Arrays.asList(batteryDataList.get(9))));
		certificationList.add(new Certification(null, "9", 1, "Composition 9", 1, "Green", 1, 1, 1, 1, 1, 1,
				"Special 9", CertificationStatus.OPENED.getCode(),
				Arrays.asList(batteryDataList.get(10), batteryDataList.get(11))));

		List<Product> productsList = new ArrayList<Product>();
		productsList.add(new Product(null, "1", "Product 01", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(0), factoryList.get(0), certificationList.get(0), null,
				null, null, 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "2", "Product 02", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(0), factoryList.get(5), certificationList.get(1), null,
				null, null, 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "3", "Product 03", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(1), factoryList.get(3), certificationList.get(2), null,
				null, null, 0, LabelingStatus.OPENED.getCode()));

		productsList.add(new Product(null, "1", "Product 01", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(0), factoryList.get(0), certificationList.get(3), null,
				null, productsList.get(0), 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "2", "Product 02", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(0), factoryList.get(5), certificationList.get(4), null,
				null, productsList.get(1), 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "3", "Product 03", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(1), factoryList.get(3), certificationList.get(5), null,
				null, productsList.get(2), 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "2", "Product 02", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(0), factoryList.get(5), certificationList.get(6), null,
				null, productsList.get(1), 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "3", "Product 03", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(1), factoryList.get(3), certificationList.get(7), null,
				null, productsList.get(2), 0, LabelingStatus.OPENED.getCode()));
		productsList.add(new Product(null, "3", "Product 03", 1, 1, new BigDecimal("14.99"), new BigDecimal("22.5"),
				new BigDecimal("32.5"), new BigDecimal("43.5"), new BigDecimal("53"), new BigDecimal("53.2"),
				new BigDecimal("53.3"), new BigDecimal("62.3"), new BigDecimal("64.5"), new BigDecimal("75.5"),
				new BigDecimal("87.5"), new BigDecimal("96.6"), new BigDecimal("74.5"), new BigDecimal("52.5"),
				new BigDecimal("22.5"), 3, 4, packingList.get(1), factoryList.get(3), certificationList.get(8), null,
				null, productsList.get(2), 0, LabelingStatus.OPENED.getCode()));

		List<OrderList> orderList = new ArrayList<OrderList>();
		orderList.add(new OrderList(null, "First order", new java.sql.Date(sdf.parse("25/12/2020").getTime()),
				OrderStatus.OPENED.getCode(), new BigDecimal("150"), new BigDecimal("50"), 10, 10, 10, 15,
				seasonsList.get(0), null, null));

		orderList.add(new OrderList(null, "Second order", new java.sql.Date(sdf.parse("31/01/2020").getTime()),
				OrderStatus.OPENED.getCode(), new BigDecimal("150"), new BigDecimal("50"), 10, 10, 10, 15,
				seasonsList.get(1), null, null));

		orderList.add(new OrderList(null, "Third order", new java.sql.Date(sdf.parse("25/03/2020").getTime()),
				OrderStatus.OPENED.getCode(), new BigDecimal("150"), new BigDecimal("50"), 10, 10, 10, 15,
				seasonsList.get(1), null, null));

		List<Production> productionList = new ArrayList<Production>();

		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));
		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));
		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));
		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));
		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));
		productionList.add(new Production(null, null, null,
				"所有产品不得使用侵权包装。大货生产前必须由我司确认大货样后才能生产。如没有内箱，则外箱必须用AB双高强以上纸质制作。产品以及所含配件必须完全按照原客户要求制作，包括颜色，材料，贴纸要求等。如有任何变动请及时与我司联系，待我司与客人沟通确认后方可进行大货生产。我司将对每款产品进行摔箱测试，如摔箱后产品损坏率超过3%，工厂必须无偿加固包装直至损坏率降低至3％以下。请按实际重量填入外箱侧唛！如客人更改装箱数量，请工厂根据实际制作并告知实际尺寸以便我司计算体积安排出货",
				"注意:工厂必须按照我司生产要求按时按质按量交货.如因质量,数量等问题而引起客户索赔退货等经济法律纠纷,则损失应由工厂承担.\r\n"
						+ "1.如样品/产品之设计,外观,商标等均为客户所有;非经客户授权,任何情况下厂方不得抄袭,仿造,使用,生产或销售客版产品,也不得在样品房,展厅或展会展示客版产品／商标等相近或雷同的产品,否则,我司将永久取消一切与工厂的合作.\r\n"
						+ "2.确认:工厂必须生产之前提供大货样品确认,我司确认正确签名之后才能开始生产.\r\n" + "3.产品应严格按照客户要求生产,如有不同,厂家需承担一切后果及费用. \r\n"
						+ "4.自下订单之后至出货前,我司安排质检,请工厂及时通知我司查货员上门检查质量,生产时间和交货时间如有所变动必须提前至少1周通知我司以便我司及时与客户协商交货时间.如厂方不及时通知,一切后果由厂商自负\r\n"
						+ "5. 付款方式:出货后30天内付清，供应商如需出货后立即结款，则按总金额97%结算",
				ProductionStatus.WAITING_START.getCode()));

		List<OrderListItem> orderListItems = new ArrayList<OrderListItem>();
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(3), orderList.get(0), productionList.get(0)));
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(4), orderList.get(0), productionList.get(1)));
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(5), orderList.get(0), productionList.get(2)));
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(6), orderList.get(1), productionList.get(3)));
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(7), orderList.get(1), productionList.get(4)));
		orderListItems.add(new OrderListItem(null, 10, 10, 100, new BigDecimal("150"), new BigDecimal("50"),
				new BigDecimal(150), productsList.get(8), orderList.get(2), productionList.get(5)));

		List<I18n> i18nList = new ArrayList<I18n>();

		i18nList.add(new I18n(null, "yahatrade", "Yaha Trade", english));
		i18nList.add(new I18n(null, "yahatrade", "Yaha Trade", chinese));

		i18nList.add(new I18n(null, "copyright", "Copyright ©", english));
		i18nList.add(new I18n(null, "copyright", "版權所有©", chinese));

		i18nList.add(new I18n(null, "seashine", "Seashine", english));
		i18nList.add(new I18n(null, "seashine", "Seashine", chinese));

		i18nList.add(new I18n(null, "user", "User", english));
		i18nList.add(new I18n(null, "user", "用戶", chinese));

		i18nList.add(new I18n(null, "password", "Password", english));
		i18nList.add(new I18n(null, "password", "密碼", chinese));

		i18nList.add(new I18n(null, "login", "Login", english));
		i18nList.add(new I18n(null, "login", "登錄", chinese));

		i18nList.add(new I18n(null, "forgotpassword", "Forgot password?", english));
		i18nList.add(new I18n(null, "forgotpassword", "忘記密碼？", chinese));

		i18nList.add(new I18n(null, "error", "Error", english));
		i18nList.add(new I18n(null, "error", "錯誤", chinese));

		i18nList.add(new I18n(null, "loginerror", "Username or password is invalid!", english));
		i18nList.add(new I18n(null, "loginerror", "用戶名或密碼無效！", chinese));

		i18nList.add(new I18n(null, "english", "English", english));
		i18nList.add(new I18n(null, "english", "英語", chinese));

		i18nList.add(new I18n(null, "chinese", "Chinese", english));
		i18nList.add(new I18n(null, "chinese", "中文", chinese));

		i18nList.add(new I18n(null, "close", "Close", english));
		i18nList.add(new I18n(null, "close", "關", chinese));

		i18nList.add(new I18n(null, "disconnected", "Your user has been disconnected. Please log in again!", english));
		i18nList.add(new I18n(null, "disconnected", "您的用戶已斷開連接。請再次登錄！", chinese));

		i18nList.add(new I18n(null, "newpassworddirection", "Enter your email to generate a new password", english));
		i18nList.add(new I18n(null, "newpassworddirection", "輸入您的電子郵件以生成新密碼", chinese));

		i18nList.add(new I18n(null, "email", "E-mail", english));
		i18nList.add(new I18n(null, "email", "電子郵件", chinese));

		i18nList.add(new I18n(null, "submit", "Submit", english));
		i18nList.add(new I18n(null, "submit", "提交", chinese));

		i18nList.add(new I18n(null, "backlogin", "Back to login", english));
		i18nList.add(new I18n(null, "backlogin", "回到登入", chinese));

		i18nList.add(new I18n(null, "emailnotfound", "E-mail not found", english));
		i18nList.add(new I18n(null, "emailnotfound", "電子郵件沒有找到", chinese));

		i18nList.add(new I18n(null, "success", "Success", english));
		i18nList.add(new I18n(null, "success", "成功", chinese));

		i18nList.add(new I18n(null, "emailsuccess", "E-mail successfully sent", english));
		i18nList.add(new I18n(null, "emailsuccess", "電子郵件發送成功", chinese));

		i18nList.add(new I18n(null, "userprofile", "Profile", english));
		i18nList.add(new I18n(null, "userprofile", "個人資料", chinese));

		i18nList.add(new I18n(null, "logout", "Logout", english));
		i18nList.add(new I18n(null, "logout", "登出", chinese));

		i18nList.add(new I18n(null, "home", "Home", english));
		i18nList.add(new I18n(null, "home", "主頁", chinese));

		i18nList.add(new I18n(null, "register", "Register", english));
		i18nList.add(new I18n(null, "register", "寄存器", chinese));

		i18nList.add(new I18n(null, "factory", "Factory", english));
		i18nList.add(new I18n(null, "factory", "廠", chinese));

		i18nList.add(new I18n(null, "customer", "Customer", english));
		i18nList.add(new I18n(null, "customer", "顧客", chinese));

		i18nList.add(new I18n(null, "edit", "Edit", english));
		i18nList.add(new I18n(null, "edit", "編輯", chinese));

		i18nList.add(new I18n(null, "delete", "Delete", english));
		i18nList.add(new I18n(null, "delete", "刪除", chinese));

		i18nList.add(new I18n(null, "add", "Add", english));
		i18nList.add(new I18n(null, "add", "加", chinese));

		i18nList.add(new I18n(null, "name", "Name", english));
		i18nList.add(new I18n(null, "name", "名稱", chinese));

		i18nList.add(new I18n(null, "address", "Address", english));
		i18nList.add(new I18n(null, "address", "地址", chinese));

		i18nList.add(new I18n(null, "contact", "Contact", english));
		i18nList.add(new I18n(null, "contact", "聯繫", chinese));

		i18nList.add(new I18n(null, "bankaccount", "Bank account number", english));
		i18nList.add(new I18n(null, "bankaccount", "銀行帳號", chinese));

		i18nList.add(new I18n(null, "nextpage", "Next page", english));
		i18nList.add(new I18n(null, "nextpage", "下一頁", chinese));

		i18nList.add(new I18n(null, "noregisters", "No matching records found", english));
		i18nList.add(new I18n(null, "noregisters", "分類", chinese));

		i18nList.add(new I18n(null, "sortfor", "Sort for", english));
		i18nList.add(new I18n(null, "sortfor", "排序為", chinese));

		i18nList.add(new I18n(null, "sort", "Sort", english));
		i18nList.add(new I18n(null, "sort", "未能找到匹配的記錄", chinese));

		i18nList.add(new I18n(null, "previouspage", "Previous page", english));
		i18nList.add(new I18n(null, "previouspage", "上一頁", chinese));

		i18nList.add(new I18n(null, "rowsperpage", "Rows per page:", english));
		i18nList.add(new I18n(null, "rowsperpage", "每頁行數：", chinese));

		i18nList.add(new I18n(null, "of", "of", english));
		i18nList.add(new I18n(null, "of", "的", chinese));

		i18nList.add(new I18n(null, "search", "Search", english));
		i18nList.add(new I18n(null, "search", "搜索", chinese));

		i18nList.add(new I18n(null, "download", "Download", english));
		i18nList.add(new I18n(null, "download", "下載", chinese));

		i18nList.add(new I18n(null, "print", "Print", english));
		i18nList.add(new I18n(null, "print", "打印", chinese));

		i18nList.add(new I18n(null, "viewcolumns", "View columns", english));
		i18nList.add(new I18n(null, "viewcolumns", "查看欄", chinese));

		i18nList.add(new I18n(null, "filter", "Filter", english));
		i18nList.add(new I18n(null, "filter", "過濾", chinese));

		i18nList.add(new I18n(null, "all", "All", english));
		i18nList.add(new I18n(null, "all", "所有", chinese));

		i18nList.add(new I18n(null, "filters", "Filters", english));
		i18nList.add(new I18n(null, "filters", "篩選器", chinese));

		i18nList.add(new I18n(null, "reset", "Reset", english));
		i18nList.add(new I18n(null, "reset", "重啟", chinese));

		i18nList.add(new I18n(null, "showcolumns", "Show columns", english));
		i18nList.add(new I18n(null, "showcolumns", "顯示列", chinese));

		i18nList.add(new I18n(null, "showhidecolumns", "Show/hide columns", english));
		i18nList.add(new I18n(null, "showhidecolumns", "顯示/隱藏列", chinese));

		i18nList.add(new I18n(null, "rowselected", "row(s) selected", english));
		i18nList.add(new I18n(null, "rowselected", "選擇的行", chinese));

		i18nList.add(new I18n(null, "deleteselectedrows", "Delete selected rows", english));
		i18nList.add(new I18n(null, "deleteselectedrows", "刪除選定的行", chinese));

		i18nList.add(new I18n(null, "gotopage", "Go to page", english));
		i18nList.add(new I18n(null, "gotopage", "轉到頁面", chinese));

		i18nList.add(new I18n(null, "page", "Page", english));
		i18nList.add(new I18n(null, "page", "頁", chinese));

		i18nList.add(new I18n(null, "id", "ID", english));
		i18nList.add(new I18n(null, "id", "編號", chinese));

		i18nList.add(new I18n(null, "factorydata", "Factory data", english));
		i18nList.add(new I18n(null, "factorydata", "工廠數據", chinese));

		i18nList.add(new I18n(null, "cancel", "Cancel", english));
		i18nList.add(new I18n(null, "cancel", "取消", chinese));

		i18nList.add(new I18n(null, "save", "Save", english));
		i18nList.add(new I18n(null, "save", "保存", chinese));

		i18nList.add(new I18n(null, "saveandexit", "Save and exit", english));
		i18nList.add(new I18n(null, "saveandexit", "保存並退出", chinese));

		i18nList.add(new I18n(null, "telephone", "Telephone", english));
		i18nList.add(new I18n(null, "telephone", "電話", chinese));

		i18nList.add(new I18n(null, "mobilephone", "Mobile phone", english));
		i18nList.add(new I18n(null, "mobilephone", "移動電話", chinese));

		i18nList.add(new I18n(null, "qqnumber", "QQ Number", english));
		i18nList.add(new I18n(null, "qqnumber", "QQ號碼", chinese));

		i18nList.add(new I18n(null, "requiredfield", "Required field", english));
		i18nList.add(new I18n(null, "requiredfield", "必填項目", chinese));

		i18nList.add(new I18n(null, "customerdata", "Customer data", english));
		i18nList.add(new I18n(null, "customerdata", "客戶資料", chinese));

		i18nList.add(new I18n(null, "wanttodelete", "Do you really want to delete?", english));
		i18nList.add(new I18n(null, "wanttodelete", "您真的要刪除嗎？", chinese));

		i18nList.add(new I18n(null, "packing", "Packing", english));
		i18nList.add(new I18n(null, "packing", "填料", chinese));

		i18nList.add(new I18n(null, "packingdata", "Packing data", english));
		i18nList.add(new I18n(null, "packingdata", "包裝數據", chinese));

		i18nList.add(new I18n(null, "reference", "Reference", english));
		i18nList.add(new I18n(null, "reference", "參考", chinese));

		i18nList.add(new I18n(null, "description", "Description", english));
		i18nList.add(new I18n(null, "description", "描述", chinese));

		i18nList.add(new I18n(null, "product", "Product", english));
		i18nList.add(new I18n(null, "product", "產品", chinese));

		i18nList.add(new I18n(null, "productdata", "Product data", english));
		i18nList.add(new I18n(null, "productdata", "產品資料", chinese));

		i18nList.add(new I18n(null, "price", "Price", english));
		i18nList.add(new I18n(null, "price", "價錢", chinese));

		i18nList.add(new I18n(null, "quantityinner", "Inner", english));
		i18nList.add(new I18n(null, "quantityinner", "內", chinese));

		i18nList.add(new I18n(null, "quantityofpieces", "Quantity of pieces", english));
		i18nList.add(new I18n(null, "quantityofpieces", "件數", chinese));

		i18nList.add(new I18n(null, "productwidth", "Product width", english));
		i18nList.add(new I18n(null, "productwidth", "產品寬度", chinese));

		i18nList.add(new I18n(null, "productheight", "Product height", english));
		i18nList.add(new I18n(null, "productheight", "產品高度", chinese));

		i18nList.add(new I18n(null, "productlength", "Product length", english));
		i18nList.add(new I18n(null, "productlength", "產品長度", chinese));

		i18nList.add(new I18n(null, "boxwidth", "Carton width", english));
		i18nList.add(new I18n(null, "boxwidth", "產品寬度", chinese));

		i18nList.add(new I18n(null, "boxheight", "Carton height", english));
		i18nList.add(new I18n(null, "boxheight", "紙箱高度", chinese));

		i18nList.add(new I18n(null, "boxlength", "Carton length", english));
		i18nList.add(new I18n(null, "boxlength", "紙箱長度", chinese));

		i18nList.add(new I18n(null, "boxcubage", "CBM carton", english));
		i18nList.add(new I18n(null, "boxcubage", "煤层气纸箱", chinese));

		i18nList.add(new I18n(null, "boxgrossweight", "GW carton", english));
		i18nList.add(new I18n(null, "boxgrossweight", "纸箱", chinese));

		i18nList.add(new I18n(null, "boxnetweight", "NW carton", english));
		i18nList.add(new I18n(null, "boxnetweight", "西北纸箱", chinese));

		i18nList.add(new I18n(null, "packingwidth", "Packing width", english));
		i18nList.add(new I18n(null, "packingwidth", "包裝寬度", chinese));

		i18nList.add(new I18n(null, "packingheight", "Packing height", english));
		i18nList.add(new I18n(null, "packingheight", "包裝高度", chinese));

		i18nList.add(new I18n(null, "packinglength", "Packing length", english));
		i18nList.add(new I18n(null, "packinglength", "包裝長度", chinese));

		i18nList.add(new I18n(null, "quantityofboxespercontainer", "Carton qty 40HC", english));
		i18nList.add(new I18n(null, "quantityofboxespercontainer", "装箱数40HC", chinese));

		i18nList.add(new I18n(null, "quantityofpiecespercontainer", "Pieces qty 40HC", english));
		i18nList.add(new I18n(null, "quantityofpiecespercontainer", "数量40HC", chinese));

		i18nList.add(new I18n(null, "netweightwithpacking", "NW with packing", english));
		i18nList.add(new I18n(null, "netweightwithpacking", "净重/包", chinese));

		i18nList.add(new I18n(null, "netweightwithoutpacking", "NW without packing", english));
		i18nList.add(new I18n(null, "netweightwithoutpacking", "不含包装", chinese));

		i18nList.add(new I18n(null, "certification", "Certification", english));
		i18nList.add(new I18n(null, "certification", "資質認證", chinese));

		i18nList.add(new I18n(null, "picture", "Picture", english));
		i18nList.add(new I18n(null, "picture", "圖片", chinese));

		i18nList.add(new I18n(null, "englishdescription", "English description", english));
		i18nList.add(new I18n(null, "englishdescription", "英文說明", chinese));

		i18nList.add(new I18n(null, "quantityofparts", "Quantity of parts", english));
		i18nList.add(new I18n(null, "quantityofparts", "零件数量", chinese));

		i18nList.add(new I18n(null, "composition", "Composition", english));
		i18nList.add(new I18n(null, "composition", "組成", chinese));

		i18nList.add(new I18n(null, "model", "Model", english));
		i18nList.add(new I18n(null, "model", "模型", chinese));

		i18nList.add(new I18n(null, "color", "Color", english));
		i18nList.add(new I18n(null, "color", "顏色", chinese));

		i18nList.add(new I18n(null, "specialrequirements", "Special requirements", english));
		i18nList.add(new I18n(null, "specialrequirements", "特殊要求", chinese));

		i18nList.add(new I18n(null, "sound", "Sound", english));
		i18nList.add(new I18n(null, "sound", "聲音", chinese));

		i18nList.add(new I18n(null, "light", "Light", english));
		i18nList.add(new I18n(null, "light", "光", chinese));

		i18nList.add(new I18n(null, "motor", "Motor", english));
		i18nList.add(new I18n(null, "motor", "發動機", chinese));

		i18nList.add(new I18n(null, "metalpart", "Metal part", english));
		i18nList.add(new I18n(null, "metalpart", "金屬零件", chinese));

		i18nList.add(new I18n(null, "clip", "Clip", english));
		i18nList.add(new I18n(null, "clip", "夾", chinese));

		i18nList.add(new I18n(null, "line", "Line", english));
		i18nList.add(new I18n(null, "line", "線", chinese));

		i18nList.add(new I18n(null, "quantity", "Quantity", english));
		i18nList.add(new I18n(null, "quantity", "數量", chinese));

		i18nList.add(new I18n(null, "type", "Type", english));
		i18nList.add(new I18n(null, "type", "類型", chinese));

		i18nList.add(new I18n(null, "voltage", "Voltage", english));
		i18nList.add(new I18n(null, "voltage", "電壓", chinese));

		i18nList.add(new I18n(null, "included", "Included", english));
		i18nList.add(new I18n(null, "included", "已包含", chinese));

		i18nList.add(new I18n(null, "notincluded", "Not included", english));
		i18nList.add(new I18n(null, "notincluded", "不包含", chinese));

		i18nList.add(new I18n(null, "batterytype", "Battery Type", english));
		i18nList.add(new I18n(null, "batterytype", "電池類型", chinese));

		i18nList.add(new I18n(null, "battery", "Battery", english));
		i18nList.add(new I18n(null, "battery", "電池", chinese));

		i18nList.add(new I18n(null, "actions", "Actions", english));
		i18nList.add(new I18n(null, "actions", "動作", chinese));

		i18nList.add(new I18n(null, "season", "Season", english));
		i18nList.add(new I18n(null, "season", "季节", chinese));

		i18nList.add(new I18n(null, "seasondata", "Season data", english));
		i18nList.add(new I18n(null, "seasondata", "季節數據", chinese));

		i18nList.add(new I18n(null, "scheduleddate", "Purchase start forecast", english));
		i18nList.add(new I18n(null, "scheduleddate", "购买开始预测", chinese));

		i18nList.add(new I18n(null, "invaliddate", "Invalid date", english));
		i18nList.add(new I18n(null, "invaliddate", "失效日期", chinese));

		i18nList.add(new I18n(null, "importlist", "Import list", english));
		i18nList.add(new I18n(null, "importlist", "匯入清單", chinese));

		i18nList.add(new I18n(null, "selectfile", "Select file", english));
		i18nList.add(new I18n(null, "selectfile", "選擇文件", chinese));

		i18nList.add(new I18n(null, "showroom", "Showroom", english));
		i18nList.add(new I18n(null, "showroom", "陈列室", chinese));

		i18nList.add(new I18n(null, "order", "Order", english));
		i18nList.add(new I18n(null, "order", "订购", chinese));

		i18nList.add(new I18n(null, "purchasedate", "Purchase date", english));
		i18nList.add(new I18n(null, "purchasedate", "購買日期", chinese));

		i18nList.add(new I18n(null, "orderdata", "Order data", english));
		i18nList.add(new I18n(null, "orderdata", "訂單資料", chinese));

		i18nList.add(new I18n(null, "orderlist", "Order list", english));
		i18nList.add(new I18n(null, "orderlist", "訂單", chinese));

		i18nList.add(new I18n(null, "orderitemdata", "Order item data", english));
		i18nList.add(new I18n(null, "orderitemdata", "訂單商品數據", chinese));

		i18nList.add(new I18n(null, "back", "Back", english));
		i18nList.add(new I18n(null, "back", "背部", chinese));

		i18nList.add(new I18n(null, "next", "Next", english));
		i18nList.add(new I18n(null, "next", "下一個", chinese));

		i18nList.add(new I18n(null, "finish", "Finish", english));
		i18nList.add(new I18n(null, "finish", "完", chinese));

		i18nList.add(new I18n(null, "width", "Width", english));
		i18nList.add(new I18n(null, "width", "寬度", chinese));

		i18nList.add(new I18n(null, "height", "Height", english));
		i18nList.add(new I18n(null, "height", "高度", chinese));

		i18nList.add(new I18n(null, "length", "Length", english));
		i18nList.add(new I18n(null, "length", "長度", chinese));

		i18nList.add(new I18n(null, "ordermasterqty", "Order master qty", english));
		i18nList.add(new I18n(null, "ordermasterqty", "订单主数量", chinese));

		i18nList.add(new I18n(null, "piecesperboxes", "Pcs/carton", english));
		i18nList.add(new I18n(null, "piecesperboxes", "件/箱", chinese));

		i18nList.add(new I18n(null, "totalboxes", "Qty cartons", english));
		i18nList.add(new I18n(null, "totalboxes", "数量纸箱", chinese));

		i18nList.add(new I18n(null, "totalpieces", "Total pieces", english));
		i18nList.add(new I18n(null, "totalpieces", "总件", chinese));

		i18nList.add(new I18n(null, "totalcubage", "Total cbm", english));
		i18nList.add(new I18n(null, "totalcubage", "煤层气总量", chinese));

		i18nList.add(new I18n(null, "unitprice", "Unit price", english));
		i18nList.add(new I18n(null, "unitprice", "單價", chinese));

		i18nList.add(new I18n(null, "totalprice", "Total price", english));
		i18nList.add(new I18n(null, "totalprice", "總價", chinese));

		i18nList.add(new I18n(null, "totalofreferences", "Total references", english));
		i18nList.add(new I18n(null, "totalofreferences", "參考總數", chinese));

		i18nList.add(new I18n(null, "productalreadyexits", "This product is already in order. Do you want to edit it?",
				english));
		i18nList.add(new I18n(null, "productalreadyexits", "該產品已經訂購。 您要編輯嗎？", chinese));

		i18nList.add(new I18n(null, "ok", "Ok", english));
		i18nList.add(new I18n(null, "ok", "好", chinese));

		i18nList.add(new I18n(null, "purchasehistory", "Purchase history", english));
		i18nList.add(new I18n(null, "purchasehistory", "購買記錄", chinese));

		i18nList.add(new I18n(null, "quantityofcontainers", "Qty containers", english));
		i18nList.add(new I18n(null, "quantityofcontainers", "數量容器", chinese));

		i18nList.add(new I18n(null, "selectproduct", "Select product", english));
		i18nList.add(new I18n(null, "selectproduct", "選擇產品", chinese));

		i18nList.add(new I18n(null, "editproductdata", "Edit product data", english));
		i18nList.add(new I18n(null, "editproductdata", "編輯產品數據", chinese));

		i18nList.add(new I18n(null, "productaddedorder", "Product added to order", english));
		i18nList.add(new I18n(null, "productaddedorder", "產品已添加到訂單", chinese));

		i18nList.add(new I18n(null, "addnewproduct", "Add new product", english));
		i18nList.add(new I18n(null, "addnewproduct", "新增產品", chinese));

		i18nList.add(new I18n(null, "remarks", "Remarks", english));
		i18nList.add(new I18n(null, "remarks", "备注", chinese));

		i18nList.add(new I18n(null, "savedwithsuccess", "Record successfully saved", english));
		i18nList.add(new I18n(null, "savedwithsuccess", "記錄成功保存", chinese));

		i18nList.add(new I18n(null, "couldnotdelete", "Couldn't delete. Record has relationships.", english));
		i18nList.add(new I18n(null, "couldnotdelete", "無法刪除。 記錄具有關係。", chinese));

		i18nList.add(new I18n(null, "deletesuccess", "Record successfully deleted", english));
		i18nList.add(new I18n(null, "deletesuccess", "記錄成功刪除", chinese));

		i18nList.add(new I18n(null, "status", "Status", english));
		i18nList.add(new I18n(null, "status", "状态", chinese));

		i18nList.add(new I18n(null, "quantityofimages", "Qty images", english));
		i18nList.add(new I18n(null, "quantityofimages", "圖片數量", chinese));

		i18nList.add(new I18n(null, "picturewithpack", "Picture with pack", english));
		i18nList.add(new I18n(null, "picturewithpack", "帶包裝圖片", chinese));

		i18nList.add(new I18n(null, "picturewithoutpack", "Picture without pack", english));
		i18nList.add(new I18n(null, "picturewithoutpack", "圖片不包", chinese));

		i18nList.add(new I18n(null, "picture4", "Picture 4", english));
		i18nList.add(new I18n(null, "picture4", "圖片4", chinese));

		i18nList.add(new I18n(null, "picture5", "Picture 5", english));
		i18nList.add(new I18n(null, "picture5", "圖片5", chinese));

		i18nList.add(new I18n(null, "picture6", "Picture 6", english));
		i18nList.add(new I18n(null, "picture6", "圖片6", chinese));

		i18nList.add(new I18n(null, "languagedata", "Language data", english));
		i18nList.add(new I18n(null, "languagedata", "語言資料", chinese));

		i18nList.add(new I18n(null, "language", "Language", english));
		i18nList.add(new I18n(null, "language", "語言", chinese));

		i18nList.add(new I18n(null, "history", "History", english));
		i18nList.add(new I18n(null, "history", "歷史", chinese));

		i18nList.add(new I18n(null, "message", "Message", english));
		i18nList.add(new I18n(null, "message", "信息", chinese));

		i18nList.add(new I18n(null, "on", "on", english));
		i18nList.add(new I18n(null, "on", "上", chinese));

		i18nList.add(new I18n(null, "wrote", "wrote", english));
		i18nList.add(new I18n(null, "wrote", "寫了", chinese));

		i18nList.add(new I18n(null, "sendtoapproval", "Send to approval", english));
		i18nList.add(new I18n(null, "sendtoapproval", "發送給批准", chinese));

		i18nList.add(new I18n(null, "opened", "Opened", english));
		i18nList.add(new I18n(null, "opened", "開了", chinese));

		i18nList.add(new I18n(null, "onapproval", "On approval", english));
		i18nList.add(new I18n(null, "onapproval", "待批准", chinese));

		i18nList.add(new I18n(null, "wishtosendtoapproval", "Do you want to submit for approval?", english));
		i18nList.add(new I18n(null, "wishtosendtoapproval", "您要提交批准嗎？", chinese));

		i18nList.add(new I18n(null, "userdata", "User data", english));
		i18nList.add(new I18n(null, "userdata", "用戶數據", chinese));

		i18nList.add(new I18n(null, "ordercreated", "created the order", english));
		i18nList.add(new I18n(null, "ordercreated", "創建訂單", chinese));

		i18nList.add(new I18n(null, "orderedited", "edited the order", english));
		i18nList.add(new I18n(null, "orderedited", "編輯訂單", chinese));

		i18nList.add(new I18n(null, "senttoapproval", "sent the order to approval", english));
		i18nList.add(new I18n(null, "senttoapproval", "已將訂單發送給批准", chinese));

		i18nList.add(new I18n(null, "approvedtheorder", "approved the order", english));
		i18nList.add(new I18n(null, "approvedtheorder", "批准訂單", chinese));

		i18nList.add(new I18n(null, "reprovedtheorder", "reproved the order", english));
		i18nList.add(new I18n(null, "reprovedtheorder", "驗證訂單", chinese));

		i18nList.add(new I18n(null, "orderisinapproval", "Order is already in approval", english));
		i18nList.add(new I18n(null, "orderisinapproval", "訂單已被批准", chinese));

		i18nList.add(new I18n(null, "ordersuccesssenttoapprov", "Order successfully submitted for approval", english));
		i18nList.add(new I18n(null, "ordersuccesssenttoapprov", "訂單已成功提交批准", chinese));

		i18nList.add(new I18n(null, "orderapproval", "Order approval", english));
		i18nList.add(new I18n(null, "orderapproval", "訂單批准", chinese));

		i18nList.add(new I18n(null, "approve", "Approve", english));
		i18nList.add(new I18n(null, "approve", "批准", chinese));

		i18nList.add(new I18n(null, "reprove", "Reprove", english));
		i18nList.add(new I18n(null, "reprove", "責備", chinese));

		i18nList.add(new I18n(null, "wanttoapprove", "Do you want to approve?", english));
		i18nList.add(new I18n(null, "wanttoapprove", "您要批准嗎？", chinese));

		i18nList.add(new I18n(null, "wanttoreprove", "Do you want to reprove?", english));
		i18nList.add(new I18n(null, "wanttoreprove", "您要譴責嗎？", chinese));

		i18nList.add(new I18n(null, "ordersuccessapproved", "Order successfully approved", english));
		i18nList.add(new I18n(null, "ordersuccessapproved", "訂單成功獲批", chinese));

		i18nList.add(new I18n(null, "orderreproved", "Order reproved", english));
		i18nList.add(new I18n(null, "orderreproved", "訂單已驗證", chinese));

		i18nList.add(new I18n(null, "approved", "Approved", english));
		i18nList.add(new I18n(null, "approved", "已批准", chinese));

		i18nList.add(new I18n(null, "reproved", "Reproved", english));
		i18nList.add(new I18n(null, "reproved", "已驗證", chinese));

		i18nList.add(new I18n(null, "export", "Export", english));
		i18nList.add(new I18n(null, "export", "出口", chinese));

		i18nList.add(new I18n(null, "qtybattery", "Battery quantity", english));
		i18nList.add(new I18n(null, "qtybattery", "電池數量", chinese));

		i18nList.add(new I18n(null, "batteryincluded", "Battery included", english));
		i18nList.add(new I18n(null, "batteryincluded", "含電池", chinese));

		i18nList.add(new I18n(null, "unitproductsize", "Unit product size", english));
		i18nList.add(new I18n(null, "unitproductsize", "單位產品尺寸", chinese));

		i18nList.add(new I18n(null, "packingsize", "Packing size", english));
		i18nList.add(new I18n(null, "packingsize", "包裝尺寸", chinese));

		i18nList.add(new I18n(null, "cartonsize", "Carton size", english));
		i18nList.add(new I18n(null, "cartonsize", "紙箱尺寸", chinese));

		i18nList.add(new I18n(null, "yes", "Yes", english));
		i18nList.add(new I18n(null, "yes", "是", chinese));

		i18nList.add(new I18n(null, "no", "No", english));
		i18nList.add(new I18n(null, "no", "沒有", chinese));

		i18nList.add(new I18n(null, "labeling", "Labeling", english));
		i18nList.add(new I18n(null, "labeling", "貼標", chinese));

		i18nList.add(new I18n(null, "certificationsuccessapproved", "Certification successfully approved", english));
		i18nList.add(new I18n(null, "certificationsuccessapproved", "認證成功通過", chinese));

		i18nList.add(new I18n(null, "certificationreproved", "Certification reproved", english));
		i18nList.add(new I18n(null, "certificationreproved", "認證通過", chinese));

		i18nList.add(new I18n(null, "labelingsuccessapproved", "Labeling successfully approved", english));
		i18nList.add(new I18n(null, "labelingsuccessapproved", "標籤成功獲得批准", chinese));

		i18nList.add(new I18n(null, "labelingreproved", "Labeling reproved", english));
		i18nList.add(new I18n(null, "labelingreproved", "標籤經過驗證", chinese));

		i18nList.add(new I18n(null, "orderlistfactory", "Order list factory", english));
		i18nList.add(new I18n(null, "orderlistfactory", "訂單清單工廠", chinese));

		i18nList.add(new I18n(null, "waitingproduction", "Waiting production", english));
		i18nList.add(new I18n(null, "waitingproduction", "等待生產", chinese));

		i18nList.add(new I18n(null, "ordergenerated", "Order generated", english));
		i18nList.add(new I18n(null, "ordergenerated", "訂單產生", chinese));

		i18nList.add(new I18n(null, "viewproductdata", "View product data", english));
		i18nList.add(new I18n(null, "viewproductdata", "查看產品數據", chinese));

		i18nList.add(new I18n(null, "productiondata", "Production data", english));
		i18nList.add(new I18n(null, "productiondetails", "生產數據", chinese));

		i18nList.add(new I18n(null, "received", "Received", english));
		i18nList.add(new I18n(null, "received", "已收到", chinese));

		i18nList.add(new I18n(null, "delivery", "Delivery", english));
		i18nList.add(new I18n(null, "delivery", "交貨", chinese));

		i18nList.add(new I18n(null, "qualityinspectionrequirements", "Quality inspection requirements", english));
		i18nList.add(new I18n(null, "qualityinspectionrequirements", "質量檢驗要求", chinese));

		i18nList.add(new I18n(null, "orderterms", "Order terms", english));
		i18nList.add(new I18n(null, "orderterms", "訂單條款", chinese));

		languageRepository.saveAll(Arrays.asList(english, chinese));
		userRepository.saveAll(Arrays.asList(jean, miranda, doctor));
		i18nRepository.saveAll(i18nList);
		customerRepository.saveAll(customerList);
		packingRepository.saveAll(packingList);
		voltageRepository.saveAll(voltageList);
		batteryTypeRepository.saveAll(batteryTypeList);
		showRoomRepository.saveAll(showRoomList);
		seasonRepository.saveAll(seasonsList);
		productRepository.saveAll(productsList);
		productionRepository.saveAll(productionList);
		orderListRepository.saveAll(orderList);
		orderListItemRepository.saveAll(orderListItems);
	}
}
