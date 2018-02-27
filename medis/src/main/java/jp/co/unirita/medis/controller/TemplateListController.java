package jp.co.unirita.medis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.template.TemplateListLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;

@RestController
@RequestMapping("/v1/templates")
public class TemplateListController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateListController.class);

    @Autowired
    ArgumentCheckLogic argumentCheckLogic;
    @Autowired
    TemplateListLogic templateListLogic;

    /**
     * すべてのテンプレート情報のリストを取得する
     * @param user ログインしているユーザ
     * @return テンプレート情報(@see jp.co.unirita.medis.domain.templateinfo.TemplateInfo)のリスト
     * @throws AuthorityException ログイン中のユーザに管理者権限がない場合に発生する例外
     */
    @GetMapping
    public List<TemplateInfo> getAllTemplateInfoList(@AuthenticationPrincipal User user) throws AuthorityException {
        String employeeNumber = user.getEmployeeNumber();
        logger.info("[method: getAllTemplateInfoList] employeeNumber = " + employeeNumber);
      //argumentCheckLogic.checkAdminAuthority(employeeNumber);
        return templateListLogic.getAllTemplateInfoList();
    }

    /**
     * 公開済みのテンプレート情報のリストを取得する
     * @param user ログインしているユーザ
     * @return テンプレート情報(@see jp.co.unirita.medis.domain.templateinfo.TemplateInfo)のリスト
     */
    @GetMapping(value = "public")
    public List<TemplateInfo> getTemplateInfoList(@AuthenticationPrincipal User user) {
        String employeeNumber = user.getEmployeeNumber();
        logger.info("[method: getTemplateInfoList] employeeNumber" + employeeNumber);
        return templateListLogic.getAllTemplateInfoList(true);
    }
}
