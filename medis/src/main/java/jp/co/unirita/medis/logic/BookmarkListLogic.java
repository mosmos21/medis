package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.contentflame.ContentFlame;
import jp.co.unirita.medis.domain.contentflame.ContentFlameRepository;
import jp.co.unirita.medis.domain.contentother.ContentOther;
import jp.co.unirita.medis.domain.contentother.ContentOtherRepository;
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
	ContentFlameRepository contentFlameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;


	public List<BookmarkForm> getBookmarkList(String employeeNumber, int maxSize) {

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

		for (DocumentInfo documentInfo2 : documentInfo) {
			System.out.println(documentInfo2);
		}

		//contentOther(documentTitle)の取得
		List<ContentFlame> contentFlame = new ArrayList<>();

		for (String str : documentIdList) {
			System.out.println(str);
		}

		for (int i = 0; i < documentIdList.size(); i++) {
			contentFlame.addAll(contentFlameRepository.findByDocumentIdAndContentOrderAndLineNumber(documentIdList.get(i), 1, 1));
		}

		for (ContentFlame contentFlame2 : contentFlame) {
			System.out.println(contentFlame2);
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFlame contentflame : contentFlame) {
			contentIdList.add(contentflame.getContentId());
		}

		for (String string : contentIdList) {
			System.out.println(string);
		}

		List<ContentOther> contentOther = new ArrayList<>();

		for (int i = 0; i < contentIdList.size(); i++) {
			contentOther.addAll(contentOtherRepository.findByContentId(contentIdList.get(i)));
		}

		for (ContentOther contentOther2 : contentOther) {
			System.out.println(contentOther2);
		}

		List<String> contentMainList = new ArrayList<>();

		for (ContentOther contentother : contentOther) {
			contentMainList.add(contentother.getContentMain());
		}

		for (String string : contentMainList) {
			System.out.println(string);
		}

		//BookmarkListにdocumentInfoとcontentMainListを格納
		List<BookmarkForm> bookmarkForm = new ArrayList<>();

		for(int i = 0; i < contentMainList.size(); i++) {
			bookmarkForm.add(new BookmarkForm(documentInfo.get(i), contentMainList.get(i)));
		}

		if (maxSize != -1) {
			bookmarkForm = bookmarkForm.subList(0, maxSize);
		}

		return bookmarkForm;

	}


}