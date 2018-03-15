package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.bookmark.Bookmark;
import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.logic.setting.BookmarkLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/user_detail-delete.sql", "file:resources/sql/user_detail-insert.sql",
		"file:resources/sql/bookmark-delete.sql", "file:resources/sql/bookmark_bookmarklogictest-insert.sql",
		"file:resources/sql/update_info-delete.sql", "file:resources/sql/update_info-insert.sql",
		"file:resources/sql/document_info-delete.sql", "file:resources/sql/document_info_bookmarklogictest-insert.sql" })
public class BookmarkLogicTest {
	@Autowired
	BookmarkLogic bookmarkLogic;
	@Autowired
	BookmarkRepository bookmarkRepository;

	@Test
	public void ユーザguのお気に入り情報を取得() {
		List<DocumentInfoForm> bookmarkInfoList = bookmarkLogic.getBookmarkList("gu");
		assertEquals("ユーザguのお気に入りが取得できませんでした", 2, bookmarkInfoList.size());
	}

	@Test
	public void ユーザmedsisのお気に入り情報を取得() {
		List<DocumentInfoForm> bookmarkInfoList = bookmarkLogic.getBookmarkList("medis");
		assertEquals("ユーザmedisのお気に入りが取得できませんでした", 3, bookmarkInfoList.size());
	}

	@Test
	public void m0000000006のお気に入り情報をfalseからtrueに更新() throws IdIssuanceUpperException {
		Bookmark bookmark = new Bookmark();
		bookmark.setBookmarkId("m0000000006");
		bookmark.setDocumentId("d0000000013");
		bookmark.setEmployeeNumber("medis");
		bookmark.setSelected(true);
		bookmarkLogic.updateBookmark("medis", "d0000000013", true);
		assertEquals("m0000000006のお気に入り情報を更新できませんでした", bookmark, bookmarkRepository.findOne("m0000000006"));
	}

	@Test
	public void m0000000006のお気に入り情報をtrueからfalseに更新() throws IdIssuanceUpperException {
		Bookmark bookmark = new Bookmark();
		bookmark.setBookmarkId("m0000000006");
		bookmark.setDocumentId("d0000000013");
		bookmark.setEmployeeNumber("medis");
		bookmark.setSelected(false);
		bookmarkLogic.updateBookmark("medis", "d0000000013", false);
		assertEquals("m0000000006のお気に入り情報を更新できませんでした", bookmark, bookmarkRepository.findOne("m0000000006"));
	}

	@Test
	public void bookmarkIdがない時に新規登録() throws IdIssuanceUpperException {
		Bookmark newBookmark = new Bookmark();
		Bookmark bookmark = new Bookmark();
		newBookmark.setBookmarkId(bookmarkLogic.getNewBookmarkId());
		newBookmark.setEmployeeNumber("gu");
		newBookmark.setDocumentId("d0000000015");
		newBookmark.setSelected(true);

		bookmark.setBookmarkId("m0000000007");
		bookmark.setEmployeeNumber("gu");
		bookmark.setDocumentId("d0000000015");
		bookmark.setSelected(true);
		assertEquals("m0000000008の新規登録ができませんでした", newBookmark, bookmark);

	}

}