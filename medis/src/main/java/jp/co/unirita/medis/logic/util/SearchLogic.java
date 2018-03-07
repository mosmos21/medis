package jp.co.unirita.medis.logic.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.controller.TemplateController;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;

@Service
@Transactional
public class SearchLogic {

	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";

	@Autowired
	TagRepository tagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	TemplateTagRepository templateTagRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;


	public List<DocumentInfoForm> getSearchResult(String tagName) throws UnsupportedEncodingException {
		//デコードし、タグのリストを作成
//		String tagParam = URLDecoder.decode(tagName, "UTF-8");
//		System.out.println(tagParam);
//		String[] param  = tagParam.split(",", 0);

		String[] param  = tagName.split(",", 0);
		//タグの値が重複している
		List<String> tempTagNameList = Arrays.asList(param);

		//値を一意に
		List<String> tagNameList = tempTagNameList.stream().distinct().collect(Collectors.toList());

		for (String tag : tagNameList) {
			System.out.println(tag);
		}
		logger.info("[method: getSearchResult] tagName =" + String.join(",", tagNameList));

		//tagNameのidを取得
		List<Tag> tagList = new ArrayList<>();

		for (String name : tagNameList) {
			tagList.addAll(tagRepository.findByTagName(name));
		}

		List<String> tagIdList = new ArrayList<>();

		for (Tag tag : tagList) {
			tagIdList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTag = new ArrayList<>();

		for (String tag : tagIdList) {
			documentTag.addAll(documentTagRepository.findByTagId(tag));
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<TemplateTag> templateTag = new ArrayList<>();

		for (String tag : tagIdList) {
			templateTag.addAll(templateTagRepository.findByTagId(tag));
		}

		List<String> templateList = new ArrayList<>();

		for (TemplateTag tempId : templateTag) {
			templateList.add(tempId.getTemplateId());
		}

		//documentListのidのdocument_info一覧
		List<DocumentInfo> docDocInfo = new ArrayList<>();

		for (String doc : documentList) {
			docDocInfo.addAll(documentInfoRepository.findByDocumentId(doc));
		}

		List<String> docDocInfoId = new ArrayList<>();

		for (DocumentInfo diid : docDocInfo) {
			docDocInfoId.add(diid.getDocumentId());
		}

		//templateListのidが付いているdocument_info一覧
		List<DocumentInfo> tempDocInfo = new ArrayList<>();

		for (String temp : templateList) {
			tempDocInfo.addAll(documentInfoRepository.findByTemplateId(temp));
		}

		List<String> tempDocInfoId = new ArrayList<>();

		for (DocumentInfo tiid : tempDocInfo) {
			tempDocInfoId.add(tiid.getDocumentId());
		}

		//documentIdのマージ(値は重複している)
		List<String> documentIdListBeforeMap = Stream.concat(docDocInfoId.stream(), tempDocInfoId.stream()).collect(Collectors.toList());

		//値を一意に
		Set<String> set = new HashSet<>(documentIdListBeforeMap);
		List<String> documentIdList = new ArrayList<>(set);


		//各documentIdごとの最新のupdateIdをもったupdate_infoのリストの取得
		List<UpdateInfo> updateInfoList = new ArrayList<>();

		for (String ids : documentIdList) {
			updateInfoList.add(updateInfoRepository
					.findFirstByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(ids, TYPE_CREATE_DOCUMENT, TYPE_UPDATE_DOCUMENT));
		}

		//updateInfoListのdocumentIdの一覧の取得
		List<String> updateDocIdList = new ArrayList<>();

		for (UpdateInfo upDocId : updateInfoList) {
			updateDocIdList.add(upDocId.getDocumentId());
		}

		//updateDocIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		for (String ids : updateDocIdList) {
			documentInfoList.addAll(documentInfoRepository.findByDocumentId(ids));
		}

		//documentInfoListとupdateInfoListの値をDocumentInfoFormに格納
		List<DocumentInfoForm> documentInfoForm = new ArrayList<>();

		for(int i = 0; i < documentInfoList.size(); i++) {
			documentInfoForm.add(new DocumentInfoForm(documentInfoList.get(i), updateInfoList.get(i)));
		}

		documentInfoForm.sort(Comparator.comparing(DocumentInfoForm::getUpdateDate).reversed());

		return documentInfoForm;
	}
}
