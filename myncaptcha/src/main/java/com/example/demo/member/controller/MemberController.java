package com.example.demo.member.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.domain.Choice;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.ApiCall;
import com.example.demo.member.service.ChoiceService;
import com.example.demo.member.service.MemberService;

@Controller
public class MemberController {

	// @Autowired
	@Resource(name = "memberService")
	private MemberService mservice;

	@Resource(name = "apiCall")
	private ApiCall api;

	@Resource(name = "choiceService")
	private ChoiceService cservice;

	private static final int GET_ANSWER_IMAGE = 1;
	private static final int GET_NOT_ANSWER_IMAGE = 0;
	private static final int GET_FIRST_INDEX = 0;

	@RequestMapping("/index")
	public void index() {
	}

	@RequestMapping("/join")
	public void join(Model model) throws Exception {

		Random random = new Random();
		final int MAX_IMG_COUNT = 8;
		int answerCount = 1 + random.nextInt(3);
		int notAnswerCount = MAX_IMG_COUNT - answerCount;
		int LIMIT = 1;
		
		List<String> NotAnswerImgList = Arrays.asList("fire-station", "gas-station", "church", "convenience-store",
				"subway-station", "daiso", "franchise", "school", "apartment");

		List<String> AnswerImgList = Arrays.asList("sky", "bridge", "indoor");
		
		List<String> Images = new ArrayList<String>();
		String answerImg = "";
		
		if (cservice.getAll() != null) {
			cservice.remove();
		}

		Collections.shuffle(NotAnswerImgList);
		Collections.shuffle(AnswerImgList);

		for (int i = 0; i < notAnswerCount; i++) {
			String response = ApiCall.getImageRandomList(NotAnswerImgList.get(i), LIMIT);
			String url = GetUrl(response, api, GET_NOT_ANSWER_IMAGE);
			Images.add(url);
		}

		for (int i = 0; i < answerCount; i++) {
			String response = ApiCall.getImageRandomList(AnswerImgList.get(GET_FIRST_INDEX), LIMIT);
			String url = GetUrl(response, api, GET_ANSWER_IMAGE);
			Images.add(url);
			answerImg = AnswerImgList.get(GET_FIRST_INDEX);
		}
		
//		List <String> notAnswerImages = getImg(MAX_IMG_COUNT, answerCount, LIMIT, GET_NOT_ANSWER_IMAGE);
//		List <String> answerImages = getImg(MAX_IMG_COUNT, answerCount, LIMIT, GET_ANSWER_IMAGE);
//		notAnswerImages.addAll(answerImages);
		
		Collections.shuffle(Images);

		answerImg += " " + answerCount + "개를 찾으세요";

		model.addAttribute("Images", Images);
		model.addAttribute("answerImg", answerImg);
		model.addAttribute("answerCount", answerCount);
		
	}

	@RequestMapping("/check")
	@ResponseBody
	public Boolean check(@RequestBody List<Choice> userSelected) throws Exception {

		ArrayList<Choice> answer = cservice.getAll();
		boolean bool = true;
		for (int i = 0; i < answer.size(); i++) {
			Choice c = cservice.search(userSelected.get(i).getBzstNo());
			if (c == null) {
				return false;
			}
		}
		return bool;
	}

	@RequestMapping("/reload")
	@ResponseBody
	public void reload() {

	}

	@RequestMapping("/add")
	public String add(Member m) throws Exception {
		mservice.add(m);
		return "/index";
	}

	@RequestMapping("/login")
	public void login() {

	}

	@RequestMapping("/loginform")
	public String loginform(HttpServletRequest request) throws Exception {
		String view = "";
		Member m = new Member();
		m.setId(request.getParameter("id"));
		m.setPwd(request.getParameter("pwd"));
		boolean flag = mservice.login(m);
		if (flag) {
			HttpSession session = request.getSession();
			session.setAttribute("id", m.getId());
			view = "/result";
		} else {
			view = "/index";
		}
		return view;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("id");
		session.invalidate();
		return "/index";
	}

	@RequestMapping("/list")
	public void list(Model model) throws Exception {
		List<Member> list = mservice.getAll();
		model.addAttribute("list", list);
	}

	public List<String> getImg(int maxCount, int answerCount, int limit, int getImg) throws Exception {
		
		List<String> images = new ArrayList<String>();
		String answerImg = "";

		List<String> NotAnswerImgList = Arrays.asList("fire-station", "gas-station", "church", "convenience-store",
				"subway-station", "daiso", "franchise", "school", "apartment");

		List<String> AnswerImgList = Arrays.asList("sky", "bridge", "indoor");
		
		if (cservice.getAll() != null) {
			cservice.remove();
		}
		
		Collections.shuffle(NotAnswerImgList);
		Collections.shuffle(AnswerImgList);

		if (getImg == 1) {
			for (int i = 0; i < answerCount; i++) {
				String response = ApiCall.getImageRandomList(AnswerImgList.get(GET_FIRST_INDEX), limit);
				String url = GetUrl(response, api, GET_ANSWER_IMAGE);
				images.add(url);
				answerImg = AnswerImgList.get(GET_FIRST_INDEX);
			}
		} else {

			for (int i = 0; i < maxCount - answerCount; i++) {
				String response = ApiCall.getImageRandomList(NotAnswerImgList.get(i), limit);
				String url = GetUrl(response, api, GET_NOT_ANSWER_IMAGE);
				images.add(url);
			}
		}
		Collections.shuffle(images);
		return images;

	}

	// JOSN을 다루기 편한 GSON이라는 라이브러리 존재함. 참고.
	private String GetUrl(String response, ApiCall api, int getImg) throws Exception {

		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(response);
		JSONArray jsonArray = (JSONArray) obj;
		JSONObject jsonObj = (JSONObject) jsonArray.get(GET_FIRST_INDEX);

		int bzstNo = Integer.parseInt(String.valueOf(jsonObj.get("bzstNo")));
		String panoTypeCd = (String) jsonObj.get("panoTypeCd");
		int width = 130;
		int height = 130;
		String url = ApiCall.getURL(bzstNo, panoTypeCd, width, height);

		if (getImg == GET_ANSWER_IMAGE) {
			cservice.add(new Choice(String.valueOf(bzstNo), String.valueOf(panoTypeCd)));
		}
		return url;
	}

}
