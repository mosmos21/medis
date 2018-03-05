package jp.co.unirita.medis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.template.BlockBaseForm;
import jp.co.unirita.medis.form.template.TemplateForm;
import jp.co.unirita.medis.logic.template.BlockLogic;
import jp.co.unirita.medis.logic.template.TemplateLogic;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RestController
@RequestMapping("/v1/templates")
public class TemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    ArgumentCheckLogic argumentCheckLogic;
    @Autowired
    BlockLogic blockLogic;
    @Autowired
    TemplateLogic templateLogic;

    /**
     * テンプレートブロックの情報一覧を取得する
     * @return テンプレートブロックのリスト
     */
    @GetMapping(value = "blocks")
    @ResponseStatus(HttpStatus.OK)
    public List<BlockBaseForm> getBlockList(){
        return blockLogic.getBlockList();
    }

    /**
     * テンプレートの内容を取得する
     * @param user ログインしているユーザ
     * @return テンプレートフォーム(@see jp.co.unirita.medis.form.template.TemplateForm)
     * @throws NotExistException 取得しようとしているテンプレートIDが存在していない場合に発生する例外
     */
    @GetMapping(value = "{templateId:^t[0-9]{10}$}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateForm getTemplate(
            @AuthenticationPrincipal User user,
            @PathVariable(value ="templateId") String templateId
    ) throws NotExistException {
        String employeeNumber = user.getEmployeeNumber();
        logger.info("[method: getTemplate] employeeNumber = " + employeeNumber);

        argumentCheckLogic.checkTemplateId(templateId);
        TemplateForm template = templateLogic.getTemplate(templateId);
        System.out.println(template);
        return template;
    }

    /**
     * テンプレートにつけられたタグ一覧を取得する
     * @param user ログインしているユーザ
     * @param templateId 取得するテンプレートID
     * @return タグ情報(@see jp.co.unirita.medis.domain.tag.Tag)のリスト
     * @throws NotExistException 取得しようとしているテンプレートIDが存在していない場合に発生する例外
     */
    @GetMapping(value = "{templateId:^t[0-9]{10}$}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getTemplateTagList(
            @AuthenticationPrincipal User user,
            @PathVariable(value ="templateId") String templateId
    ) throws NotExistException{
        logger.info("[method: getTemplateTagList] employeeNumber = " + user.getEmployeeNumber());
        argumentCheckLogic.checkTemplateId(templateId);
        return templateLogic.getTemplateTags(templateId);
    }

    /**
     * テンプレートの内容を更新する
     * @param user ログインしているユーザ
     * @param templateId 更新するテンプレートのテンプレートID
     * @return templateId 更新したテンプレートのテンプレートID
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws IdIssuanceUpperException 新規IDの発行が上限に達した場合に発生する例外
     * @throws NotExistException 更新しようとしているテンプレートIDが存在していない場合に発生する例外
     */
    @PostMapping(value = "{templateId:^t[0-9]{10}$}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateTemplate(
            @AuthenticationPrincipal User user,
            @RequestBody TemplateForm template
    ) throws AuthorityException, IdIssuanceUpperException, NotExistException {
        logger.info("[method: updateTemplate] employeeNumber = " + user.getEmployeeNumber());

        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
        argumentCheckLogic.checkTemplateId(template.getTemplateId());
        return templateLogic.update(template, user.getEmployeeNumber());
    }

    /**
     * テンプレートの公開状態を、変更する
     * @param user ログインしているユーザ
     * @param templateId 更新するテンプレートのテンプレートID
     * @param templatePublish public: 公開する, private: 非公開にする
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws NotExistException 更新するテンプレートIDが存在しない場合に発生する例外
     */
    @PostMapping(value = "{templateId:^t[0-9]{10}$}/{templatePublish:^public|private$}")
    @ResponseStatus(HttpStatus.CREATED)
    public void toggleTemplate(
            @AuthenticationPrincipal User user,
            @PathVariable(value = "templateId") String templateId,
            @PathVariable(value = "templatePublish") String templatePublish
    ) throws AuthorityException, NotExistException {
        logger.info("[method: toggleTemplate] employeeNumber = " + user.getEmployeeNumber() + ", publishType = " + templatePublish);
        boolean publish = templatePublish.equals("public");

        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
        argumentCheckLogic.checkTemplateId(templateId);
        templateLogic.toggleTemplatePublish(templateId, publish);
    }

    /**
     * テンプレートに付けられたタグを更新する
     * @param user ログインしているユーザ
     * @param templateId　付けられたタグを更新するテンプレートID
     * @param tags タグ情報(@see jp.co.unirita.medis.domain.tag.Tag)のリスト
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws IdIssuanceUpperException 新規IDの発行が上限に達した場合に発生する例外
     * @throws NotExistException 更新するテンプレートIDが存在しない場合に発生する例外
     */
    @PostMapping(value = "{templateId:^t[0-9]{10}$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTemplateTagList(
            @AuthenticationPrincipal User user,
            @PathVariable String templateId,
            @RequestBody List<Tag> tags
    ) throws AuthorityException, IdIssuanceUpperException, NotExistException {
        logger.info("[method: updateTemplateTagList] employeeNumber = " + user.getEmployeeNumber());

        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
        argumentCheckLogic.checkTemplateId(templateId);
        templateLogic.updateTags(templateId, tags);
    }

    /**
     * 新規テンプレートを保存し、テンプレートIDを付与する
     * @param user ログインしているユーザ
     * @param template テンプレートフォーム(@see jp.co.unirita.medis.form.template.TemplateForm)
     * @return templateId 新規作成したテンプレートのテンプレートID
     * @throws AuthorityException ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws IdIssuanceUpperException 新規IDの発行が上限に達した場合に発生する例外
     * @throws NotExistException ユーザが存在しない場合に発生する例外
     */
    @PutMapping(value = "new")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveTemplate(
            @AuthenticationPrincipal User user,
            @RequestBody TemplateForm template
    ) throws AuthorityException, IdIssuanceUpperException, NotExistException {
        logger.info("[method: saveTemplate] employeeNumber = " + user.getEmployeeNumber());

        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
        return templateLogic.save(template, user.getEmployeeNumber());
    }

    /**
     * 新規テンプレートについているタグを保存する
     * @param user ログインしているユーザ
     * @param templateId 付けられたタグを保存するテンプレートID
     * @param tags タグ情報(@see jp.co.unirita.medis.domain.tag.Tag)のリスト
     * @throws AuthorityException　ログインしているユーザに管理者権限がない場合に発生する例外
     * @throws IdIssuanceUpperException　新規IDの発行が上限に達した場合に発生する例外
     * @throws NotExistException　ユーザが存在しない場合に発生する例外
     */
    @PutMapping(value = "{templateId:^t[0-9]{10}$}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTemplateTagList(
            @AuthenticationPrincipal User user,
            @PathVariable String templateId,
            @RequestBody List<Tag> tags
     ) throws AuthorityException, IdIssuanceUpperException, NotExistException {
        logger.info("[method: saveTemplateTagList] employeeNumber = " + user.getEmployeeNumber());

        argumentCheckLogic.checkAdminAuthority(user.getEmployeeNumber());
        argumentCheckLogic.checkTemplateId(templateId);
        templateLogic.saveTags(templateId, tags);
    }
}
