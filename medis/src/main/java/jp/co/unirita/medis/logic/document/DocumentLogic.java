package jp.co.unirita.medis.logic.document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItem;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentContentForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.util.TagLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
public class DocumentLogic {

	private static final Logger logger = LoggerFactory.getLogger(DocumentLogic.class);

	private static final String TYPE_CREATE_DOCUMENT = "v0000000000";
	private static final String TYPE_UPDATE_DOCUMENT = "v0000000001";

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	DocumentTagRepository documentTagRepository;
	@Autowired
	DocumentItemRepository documentItemRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	@Autowired
	TagLogic tagLogic;

	public DocumentForm getDocument(String documentId) {
		Bookmark bookmark = bookmarkRepository.findOne("m0000000000");

		DocumentInfo info = documentInfoRepository.findOne(documentId);
		UserDetail detail = userDetailRepository.findOne(info.getEmployeeNumber());

		DocumentForm document = new DocumentForm();
		document.setEmployeeNumber(info.getEmployeeNumber());
		document.setName(
				new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString());
		document.setDocumentCreateDate(info.getDocumentCreateDate());
		document.setDocumentId(documentId);
		document.setTemplateId(info.getTemplateId());
		document.setDocumentName(info.getDocumentName());
		document.setContents(getDocumentContents(documentId));
		document.setSelected(bookmark == null ? false : bookmark.isSelected());
		return document;
	}

	public DocumentInfo getDocumentInfo(String documentId) {
		DocumentInfo documentInfo = documentInfoRepository.findOne(documentId);
		return documentInfo;
	}

	public List<Tag> getDocumentTags(String id) {
		return getDocumentTagStartWith(id, "n");
	}

	public List<Tag> getDocumentSystemTags(String id) {
		return getDocumentTagStartWith(id, "s");
	}

	private List<Tag> getDocumentTagStartWith(String id, String startStr){
		List<DocumentTag> documentTagList = documentTagRepository.findByDocumentId(id);
		List<String> tagIdList = documentTagList.stream()
				.map(DocumentTag::getTagId)
				.filter(str -> str.startsWith(startStr))
				.collect(Collectors.toList());
		List<Tag> tagList = tagRepository.findByTagIdIn(tagIdList);
		tagList.sort(Comparator.naturalOrder());
		return tagList;
	}

	public String update(DocumentForm documentForm, String employeeNumber) throws IdIssuanceUpperException {
		save(documentForm, employeeNumber);
		saveUpdateInfo(documentForm.getDocumentId(), TYPE_UPDATE_DOCUMENT, employeeNumber);
		return documentForm.getDocumentId();
	}

	public String saveDocumentInfo(DocumentForm document, String employeeNumber) throws IdIssuanceUpperException {
		String documentId = document.getDocumentId() == null ? createNewDocumentId() : document.getDocumentId();
		String templateId = document.getTemplateId();
		String documentName = document.getDocumentName();
		Timestamp documentCreateDate = new Timestamp(System.currentTimeMillis());
		boolean documentPublish = document.isPublish();
		DocumentInfo info = new DocumentInfo(documentId, documentName, employeeNumber, templateId, documentCreateDate,
				documentPublish);
		documentInfoRepository.saveAndFlush(info);
		return info.getDocumentId();
	}

	private List<DocumentContentForm> getDocumentContents(String documentId) {
		List<DocumentContentForm> contents = new ArrayList<>();

		List<DocumentItem> items = documentItemRepository
				.findByDocumentIdOrderByContentOrderAscLineNumberAsc(documentId);
		int before = 1;
		DocumentContentForm content = new DocumentContentForm();
		content.setContentOrder(1);
		for (DocumentItem item : items) {
			if (item.getContentOrder() != before) {
				contents.add(content);
				content = new DocumentContentForm();
				content.setContentOrder(item.getContentOrder());
				before = item.getContentOrder();
			}
			content.getItems().add(item.getValue());
		}
		contents.add(content);
		return contents;
	}

	public void toggleDocumentPublish(String documentId, boolean documentPublish) {
		DocumentInfo info = documentInfoRepository.findOne(documentId);
		info.setDocumentPublish(documentPublish);
		documentInfoRepository.save(info);
		logger.info("[method: toggleDocumentPublish] Update info of documentID '" + documentId + "' " + info);
	}

	public String save(DocumentForm documentForm, String employeeNumber) throws IdIssuanceUpperException {
		String id = saveDocumentInfo(documentForm, employeeNumber);
		documentForm.setDocumentId(id);
		documentItemRepository.deleteByDocumentId(documentForm.getDocumentId());
		saveDocumentContent(documentForm.getDocumentId(), documentForm.getContents());
		saveUpdateInfo(id, TYPE_CREATE_DOCUMENT, employeeNumber);
		return id;
	}

	public void saveTags(final String documentId, List<Tag> tags) throws IdIssuanceUpperException {
		documentTagRepository.deleteByDocumentId(documentId);
		DocumentInfo documentInfo = documentInfoRepository.findOne(documentId);
		int order = 1;
		for (Tag tag : tagLogic.applyTags(tags)) {
			if (tag.getTagId() == null) {
				tag.setTagId(tagLogic.getNewTagId());
			}
			documentTagRepository.save(new DocumentTag(documentId, order, tag.getTagId()));
			order++;
		}
		// システムタグ追加
		UserDetail detail = userDetailRepository.findOne(documentInfo.getEmployeeNumber());
		List<Tag> systemTagList = tagRepository.findByTagName(detail.getLastName() + " " + detail.getFirstName());
		if (systemTagList.size() == 0) {
			systemTagList.addAll(tagLogic
					.applySystemTag(Arrays.asList(new Tag("", detail.getLastName() + " " + detail.getFirstName()))));
		}
		documentTagRepository.saveAndFlush(new DocumentTag(documentId, order, systemTagList.get(0).getTagId()));
	}

	private void saveDocumentContent(String documentId, List<DocumentContentForm> contents) {
		for (DocumentContentForm content : contents) {
			saveDocumentItems(documentId, content.getContentOrder(), content.getItems());
		}
		documentItemRepository.flush();
	}

	private void saveDocumentItems(String documentId, int contentOrder, List<String> items) {
		int lineNumber = 1;
		for (String item : items) {
			documentItemRepository.save(new DocumentItem(documentId, contentOrder, lineNumber, item));
			lineNumber++;
		}
	}

	private void saveUpdateInfo(String documentId, String updateType, String employeeNumber) throws IdIssuanceUpperException {
		UpdateInfo info = new UpdateInfo();
		Timestamp updateDate = new Timestamp(System.currentTimeMillis());
		info.setUpdateId(createNewUpdateId());
		info.setDocumentId(documentId);
		info.setUpdateType(updateType);
		info.setEmployeeNumber(employeeNumber);
		info.setUpdateDate(updateDate);
		updateInfoRepository.saveAndFlush(info);
	}

	private synchronized String createNewDocumentId() throws IdIssuanceUpperException {
		List<DocumentInfo> list = documentInfoRepository.findAll(new Sort(Sort.Direction.DESC, "documentId"));
		if (list.size() == 0) {
			return "d0000000000";
		}
		long idNum = Long.parseLong(list.get(0).getDocumentId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("文書の発行限界");
		}
		return String.format("d%010d", idNum + 1);
	}

	private synchronized String createNewUpdateId() throws IdIssuanceUpperException {
		List<UpdateInfo> list = updateInfoRepository.findAll(new Sort(Sort.Direction.DESC, "updateId"));
		if (list.size() == 0) {
			return "u0000000000";
		}
		long idNum = Long.parseLong(list.get(0).getUpdateId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("更新IDの発行限界");
		}
		return String.format("u%010d", idNum + 1);
	}
}
