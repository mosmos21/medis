package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItem;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.form.BookmarkForm;

@Service
@Transactional
public class BookmarkListLogic {

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	DocumentItemRepository documentItemRepository;


	public List<BookmarkForm> getBookmarkList(String employeeNumber, Integer maxSize) {

		//ユーザがお気に入りしている文書idの一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumber(employeeNumber);
		List<String> documentIdList = new ArrayList<>();

		for (Bookmark doclist : bookmark) {
			documentIdList.add(doclist.getDocumentId());
		}

		//上で取得した文書idのdocument_infoを取得
		List<DocumentInfo> documentInfo = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			documentInfo.addAll(documentInfoRepository.findByDocumentId(documentIdList.get(i)));
		}

		//contentOther(documentTitle)の取得
		List<DocumentItem> documentItem = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			documentItem.addAll(documentItemRepository.findByDocumentIdAndContentOrderAndLineNumber(documentIdList.get(i), 1, 1));
		}
		return null;
	}
}