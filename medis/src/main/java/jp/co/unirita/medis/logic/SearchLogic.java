package jp.co.unirita.medis.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;

@Service
@Transactional
public class SearchLogic {

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


	public List<DocumentInfo> getSearchResultList(String tagName) throws UnsupportedEncodingException {
		//デコードし、タグのリストを作成
		String tagParam = URLDecoder.decode(tagName, "UTF-8");
		System.out.println(tagParam);

		String[] param  = tagParam.split(",", 0);
		List<String> tagNameList = Arrays.asList(param);

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
		List<TemplateTag> fixedTag = new ArrayList<>();

		for (int i = 0; i < tagList.size(); i++) {
			fixedTag.addAll(templateTagRepository.findByTagId(tagIdList.get(i)));
		}

		List<String> templateList = new ArrayList<>();

		for (TemplateTag tempId : fixedTag) {
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
