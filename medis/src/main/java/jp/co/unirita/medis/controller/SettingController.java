package jp.co.unirita.medis.controller;

import jp.co.unirita.medis.domain.toppage.Toppage;
import jp.co.unirita.medis.domain.toppage.ToppageRepository;
import jp.co.unirita.medis.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingController {

    @Autowired
    ToppageRepository toppageRepository;

    @RequestMapping(value = "me/toppage", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Toppage> getToppageOrder(@AuthenticationPrincipal User user) {
        List<Toppage> order = toppageRepository.findAllByEmployeeNumberOrderByToppageOrderAsc(user.getEmployeeNumber());
        return order;
    }
}
