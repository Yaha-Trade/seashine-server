package com.seashine.server.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seashine.server.config.SecurityConfig;
import com.seashine.server.domain.BatteryType;
import com.seashine.server.domain.Customer;
import com.seashine.server.domain.Factory;
import com.seashine.server.domain.I18n;
import com.seashine.server.domain.Language;
import com.seashine.server.domain.Packing;
import com.seashine.server.domain.User;
import com.seashine.server.domain.Voltage;
import com.seashine.server.domain.enums.Languages;
import com.seashine.server.domain.enums.Profile;
import com.seashine.server.repositories.BatteryTypeRepository;
import com.seashine.server.repositories.CustomerRepository;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.repositories.I18nRepository;
import com.seashine.server.repositories.LanguageRepository;
import com.seashine.server.repositories.PackingRepository;
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

	public void instantiateTestDataBase() throws ParseException {
		Set<Integer> profiles = new HashSet<Integer>();
		profiles.add(Profile.ADMINISTRADOR.getCode());

		Language english = new Language(Languages.ENGLISH.getCode(), "English", "en");
		Language chinese = new Language(Languages.CHINESE.getCode(), "Chinese", "zh");

		User jean = new User(null, "Jean Briesemeister", "jean@yahacomex.com.br", "jean",
				pe.bCryptPasswordEncoder().encode("123"), english, profiles);

		User miranda = new User(null, "Rafael Miranda", "rafaelmiranda@yahacomex.com.br", "miranda",
				pe.bCryptPasswordEncoder().encode("456"), chinese, profiles);

		User doctor = new User(null, "Doutor Estranho", null, "doctor", pe.bCryptPasswordEncoder().encode("789"),
				english, profiles);

		List<Factory> factoryList = new ArrayList<Factory>();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
		Random r = new Random();

		int i = 1;
		for (i = 0; i < 77; i++) {
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

		factoryList.clear();

		List<Customer> customerList = new ArrayList<Customer>();
		customerList.add(new Customer(null, "Yaha"));
		customerList.add(new Customer(null, "Jean"));
		customerList.add(new Customer(null, "Mirando"));
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

		languageRepository.saveAll(Arrays.asList(english, chinese));
		userRepository.saveAll(Arrays.asList(jean, miranda, doctor));
		i18nRepository.saveAll(i18nList);
		customerRepository.saveAll(customerList);
		packingRepository.saveAll(packingList);
		voltageRepository.saveAll(voltageList);
		batteryTypeRepository.saveAll(batteryTypeList);
	}
}
