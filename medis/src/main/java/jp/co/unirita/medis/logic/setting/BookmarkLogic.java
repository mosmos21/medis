package jp.co.unirita.medis.logic.setting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;

@Service
@Transactional
public class BookmarkLogic {

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;


	public List<DocumentInfo> getBookmarkList(String employeeNumber, Integer maxSize) {

		//ユーザがお気に入りしている文書idの一覧を取得
		List<Bookmark> bookmark = bookmarkRepository.findByEmployeeNumberAndSelected(employeeNumber, true);
		List<String> documentIdList = new ArrayList<>();

		for (Bookmark doclist : bookmark) {
			documentIdList.add(doclist.getDocumentId());
		}

		//上で取得した文書idのdocument_infoを取得
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

		if (maxSize != -1 && documentInfo.size() > maxSize) {
			documentInfo = documentInfo.subList(0, maxSize);
		}

		return documentInfo;
	}


	public void updateBookmark(String employeeNumber, String documentId) {
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
			String body = lastBookmarkId.substring(1,11);
			int temp = Integer.parseInt(body);
			temp++;
			body = String.format("%010d", temp);
			lastBookmarkId = head + body;

			Bookmark bookmark = new Bookmark();
			bookmark.setBookmarkId(lastBookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			bookmark.setSelected(true);

			bookmarkRepository.saveAndFlush(bookmark);

		} else {
			//既にあるブックマークIDのフラグを変更
			List<Bookmark> bookmarkList = bookmarkRepository.findByEmployeeNumberAndDocumentId(employeeNumber, documentId);

			String bookmarkId = bookmarkList.get(0).getBookmarkId();

			boolean flug = bookmarkList.get(0).isSelected();

			Bookmark bookmark = new Bookmark();

			bookmark.setBookmarkId(bookmarkId);
			bookmark.setEmployeeNumber(employeeNumber);
			bookmark.setDocumentId(documentId);
			if (flug) {
				bookmark.setSelected(false);
			} else {
				bookmark.setSelected(true);
			}

			bookmarkRepository.saveAndFlush(bookmark);

		}
	}
}