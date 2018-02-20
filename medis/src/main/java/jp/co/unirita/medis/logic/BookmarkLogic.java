package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.Comparator;
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
public class BookmarkLogic {

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	ContentFlameRepository contentFlameRepository;
	@Autowired
	ContentOtherRepository contentOtherRepository;


	public List<BookmarkForm> getBookmarkList(String employeeNumber, Integer maxSize) {

		//ユーザがお気に入りしている文書idの一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndIsChoiced(employeeNumber, true);
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
		List<ContentFlame> contentFlame = new ArrayList<>();

		for (int i = 0; i < documentIdList.size(); i++) {
			contentFlame.addAll(contentFlameRepository.findByDocumentIdAndContentOrderAndLineNumber(documentIdList.get(i), 1, 1));
		}

		List<String> contentIdList = new ArrayList<>();

		for (ContentFlame contentflame : contentFlame) {
			contentIdList.add(contentflame.getContentId());
		}

		List<ContentOther> contentOther = new ArrayList<>();

		for (int i = 0; i < contentIdList.size(); i++) {
			contentOther.addAll(contentOtherRepository.findByContentId(contentIdList.get(i)));
		}

		List<String> contentMainList = new ArrayList<>();

		for (ContentOther contentother : contentOther) {
			contentMainList.add(contentother.getContentMain());
		}

		//BookmarkListにdocumentInfoとcontentMainListを格納
		List<BookmarkForm> bookmarkForm = new ArrayList<>();

		for(int i = 0; i < contentMainList.size(); i++) {
			bookmarkForm.add(new BookmarkForm(documentInfo.get(i), contentMainList.get(i)));
		}

		bookmarkForm.sort(new Comparator<BookmarkForm>(){
			@Override
			public int compare(BookmarkForm i1, BookmarkForm i2) {
				return i2.getDocumentId().compareTo(i1.getDocumentId());
			}
		});

		if (maxSize != -1 && bookmarkForm.size() > maxSize) {
			bookmarkForm = bookmarkForm.subList(0, maxSize);
		}

		return bookmarkForm;
	}


	public void updatebookmark(String employeeNumber, String documentId) {
		int count = bookmarkRepository.countByEmployeeNumberAndDocumentId(employeeNumber, documentId);

		if (count == 0) {
			//最新のIDを取得し、DBに登録するするIDに変換
			List<Bookmark> bookmarkList = bookmarkRepository.findByOrderByBookmarkIdDesc();

			List<String> bookmarkIdList = new ArrayList<>();
			for (Bookmark bookmark : bookmarkList) {
				bookmarkIdList.add(bookmark.getBookmarkId());
			}

			String lastBookmarkId = bookmarkIdList.get(0);
			String head = lastBookmarkId.substring(0, 1);
			String foot = lastBookmarkId.substring(1,11);
			int temp = Integer.parseInt(foot);
			temp++;
			foot = String.format("%010d", temp);
			lastBookmarkId = head + foot;

			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(lastBookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			bookmark.setChoiced(true);

			bookmarkRepository.saveAndFlush(bookmark);

		} else {
			//既にあるブックマークIDのフラグを変更
			List<Bookmark> bookmarkList = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);

			String bookmarkId = bookmarkList.get(0).getBookmarkId();

			boolean flug = bookmarkList.get(0).isChoiced();

			Bookmark bookmark = new Bookmark();

			bookmark.setBookmarkId(bookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			if (flug) {
				bookmark.setChoiced(false);
			} else {
				bookmark.setChoiced(true);
			}

			bookmarkRepository.saveAndFlush(bookmark);

		}
	}
}