package jp.co.unirita.medis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.toppage.Toppage;
import jp.co.unirita.medis.domain.toppage.ToppageRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;

@RestController
@RequestMapping("/settings")
public class SettingController {

	@Autowired
	ToppageRepository toppageRepository;
	@Autowired
	UserDetailRepository userdetailRepository;

	@RequestMapping(value = "me/toppage", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<Toppage> getToppageOrder(@AuthenticationPrincipal User user) {
		List<Toppage> order = toppageRepository.findAllByEmployeeNumberOrderByToppageOrderAsc(user.getEmployeeNumber());
		return order;
	}

	@RequestMapping(value = "me", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<UserDetail> getUserDetail(@AuthenticationPrincipal UserDetail userdetail) {
		List<UserDetail> detail = userdetailRepository.findAllByEmployeeNumber(userdetail.getEmployeeNumber());
		return detail;
	}
}