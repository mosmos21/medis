package jp.co.unirita.medis.controller;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.logic.util.SearchLogic;

@RequestMapping("/v1/search")

@RestController
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	SearchLogic searchLogic;


	/**
     * タグ検索結果の文書一覧を取得する
     * @param user ログインしているユーザ
     * @param tagNames 検索したタグの名前をカンマ区切りで繋げた文字列
     * @return 更新Id情報(@see jp.co.unirita.medis.form.DocumentInfoForm)のリスト
     * @throws UnsupportedEncodingException 文字のエンコーディングがサポートされていない場合に発生する例外
     */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<DocumentInfoForm> findDocuments(
		@AuthenticationPrincipal User user,
		@RequestParam(value = "tags", required = false) String tagNames
	) throws UnsupportedEncodingException {
//		return searchLogic.getSearchResult(tagName);
		logger.info("[method: findDocuments] tagNames = " + tagNames);
		return searchLogic.findDocuments(Arrays.asList(tagNames.split(",")));
	}
}
