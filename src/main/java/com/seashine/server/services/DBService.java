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
import com.seashine.server.domain.Factory;
import com.seashine.server.domain.I18n;
import com.seashine.server.domain.Language;
import com.seashine.server.domain.User;
import com.seashine.server.domain.enums.Languages;
import com.seashine.server.domain.enums.Profile;
import com.seashine.server.repositories.FactoryRepository;
import com.seashine.server.repositories.I18nRepository;
import com.seashine.server.repositories.LanguageRepository;
import com.seashine.server.repositories.UserRepository;

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

		Set<String> telephones = new HashSet<String>();
		Set<String> mobilePhones = new HashSet<String>();
		Set<String> qqNumbers = new HashSet<String>();

		telephones.add("123");
		telephones.add("456");
		telephones.add("789");

		mobilePhones.add("101112");
		mobilePhones.add("131415");
		mobilePhones.add("161718");

		qqNumbers.add("192021");
		qqNumbers.add("222324");
		qqNumbers.add("252627");

		List<Factory> factoryList = new ArrayList<Factory>();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
		Random r = new Random();

		int i = 1;
		for (i = 0; i < 77; i++) {
			factoryList.add(new Factory(null, Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))),
					Character.toString(alphabet.charAt(r.nextInt(alphabet.length()))), null, null, null));

			if (factoryList.size() == 100000) {
				factoryRepository.saveAll(factoryList);
				factoryList.clear();
			}
		}

		if (factoryList.size() > 0) {
			factoryRepository.saveAll(factoryList);
		}

		factoryList.clear();

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

		languageRepository.saveAll(Arrays.asList(english, chinese));
		userRepository.saveAll(Arrays.asList(jean, miranda, doctor));
		i18nRepository.saveAll(i18nList);
	}
}
