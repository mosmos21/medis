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
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;

@Service
@Transactional
public class SearchLogic {

	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

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


	public List<DocumentInfo> getSearchResult(String tagName) throws UnsupportedEncodingException {
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

		for (String tag : tagNameList) {
		logger.info("[method: getSearchResult] tagName =" + tag);
		}

		//tagNameのidを取得
		List<Tag> tagList = new ArrayList<>();

		for (int i = 0; i < tagNameList.size(); i++) {
			tagList.addAll(tagRepository.findByTagName(tagNameList.get(i)));
		}

		List<String> tagIdList = new ArrayList<>();

		for (Tag tag : tagList) {
			tagIdList.add(tag.getTagId());
		}

		//文書についているタグが付いている文書の一覧
		List<DocumentTag> documentTag = new ArrayList<>();

		for (int i = 0; i < tagList.size(); i++) {
			documentTag.addAll(documentTagRepository.findByTagId(tagIdList.get(i)));
		}
		List<String> documentList = new ArrayList<>();

		for (DocumentTag docId : documentTag) {
			documentList.add(docId.getDocumentId());
		}

		//テンプレートについているタグが付いている文書の一覧
		List<TemplateTag> templateTag = new ArrayList<>();

		for (int i = 0; i < tagList.size(); i++) {
			templateTag.addAll(templateTagRepository.findByTagId(tagIdList.get(i)));
		}

		List<String> templateList = new ArrayList<>();

		for (TemplateTag tempId : templateTag) {
			templateList.add(tempId.getTemplateId());
		}

		//documentListのidのdocument_info一覧
		List<DocumentInfo> docDocInfo = new ArrayList<>();

		for (int i = 0; i < documentList.size(); i++) {
			docDocInfo.addAll(documentInfoRepository.findByDocumentId(documentList.get(i)));
		}

		List<String> docDocInfoId = new ArrayList<>();

		for (DocumentInfo diid : docDocInfo) {
			docDocInfoId.add(diid.getDocumentId());
		}

		//templateListのidが付いているdocument_info一覧
		List<DocumentInfo> tempDocInfo = new ArrayList<>();

		for (int i = 0; i < templateList.size(); i++) {
			tempDocInfo.addAll(documentInfoRepository.findByTemplateId(templateList.get(i)));
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

		//documentIdListのdocumentInfoの取得
		List<DocumentInfo> documentInfo = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			documentInfo.addAll(documentInfoRepository.findByDocumentId(documentIdList.get(i)));
		}

		documentInfo.sort(new Comparator<DocumentInfo>(){
			@Override
			public int compare(DocumentInfo i1, DocumentInfo i2) {
				return i2.getDocumentId().compareTo(i1.getDocumentId());
			}
		});

		return documentInfo;

	}

}
