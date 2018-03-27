package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.bookmark.BookmarkRepository;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documentitem.DocumentItemRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfoRepository;
import jp.co.unirita.medis.form.document.DocumentContentForm;
import jp.co.unirita.medis.form.document.DocumentForm;
import jp.co.unirita.medis.logic.document.DocumentLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/user_detail-insert.sql", "file:resources/sql/document_info-insert.sql",
		"file:resources/sql/update_info-insert.sql", "file:resources/sql/bookmark_bookmarklogictest-insert.sql",
		"file:resources/sql/tag-insert.sql", "file:resources/sql/user_detail-insert.sql",
		"file:resources/sql/document_tag_monitoring_logic_test-insert.sql",
		"file:resources/sql/document_item-insert.sql" })
public class DocumentLogicTest {

    @Autowired
    DocumentLogic documentLogic;
    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    DocumentTagRepository documentTagRepository;
	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	DocumentItemRepository documentItemRepository;
	@Autowired
	UpdateInfoRepository updateInfoRepository;
	@Autowired
	CommentRepository commentRepository;

    @Test
    public void 文書の取得() {
        DocumentForm result = documentLogic.getDocument("97958","d0000000000");

    	DocumentForm testData = new DocumentForm();
    	testData.setEmployeeNumber("97958");
    	testData.setName("浅野 雅則");
    	testData.setDocumentCreateDate(result.getDocumentCreateDate());
    	testData.setDocumentId("d0000000000");
    	testData.setTemplateId("t0000000000");
    	testData.setDocumentName("ITパスポート⑪まとめテスト");
		testData.setContents(文章データ());

        assertEquals("文書の取得が正しく動作していません",testData , result);
    }

    @Test
    public void 文書情報の取得() {
    	DocumentInfo result = documentLogic.getDocumentInfo("d0000000000");

    	DocumentInfo testData = new DocumentInfo();
    	testData.setEmployeeNumber("97958");
    	testData.setDocumentCreateDate(result.getDocumentCreateDate());
    	testData.setDocumentId("d0000000000");
    	testData.setTemplateId("t0000000000");
    	testData.setDocumentName("ITパスポート⑪まとめテスト");
    	testData.setDocumentPublish(true);

    	assertEquals("文書情報の取得が正しく動作していません",testData , result);
    }

    @Test
    public void 一般タグの取得() {
    	List<Tag> result = documentLogic.getDocumentTags("d0000000001");

    	List<Tag> testData = new ArrayList<>();
    	Tag tag = new Tag();
    	tag.setTagId("n0000000001");
    	tag.setTagName("2018年度新人研修");
    	testData.add(tag);

    	assertEquals("一般タグの取得が正しく動作していません",testData , result);
    }

    @Test
    public void システムタグの取得() {
    	List<Tag> result = documentLogic.getDocumentSystemTags("d0000000001");

    	List<Tag> testData = new ArrayList<>();
    	Tag tag = new Tag();
    	tag.setTagId("s0000000001");
    	tag.setTagName("システム");
    	testData.add(tag);

    	assertEquals("一般タグの取得が正しく動作していません",testData , result);
    }

    @Test
    public void 文書の更新() throws IdIssuanceUpperException {
    	DocumentForm form = new DocumentForm();
    	form.setDocumentId("d0000000000");
    	form.setDocumentName("updateTest");
    	form.setTemplateId("t0000000002");
    	form.setPublish(false);
    	form.setContents(文章データ());
    	String resultId = documentLogic.save(form,"99999");
    	DocumentInfo result = documentInfoRepository.findOne(resultId);

    	DocumentInfo testData = new DocumentInfo();
    	testData.setDocumentId("d0000000000");
    	testData.setDocumentName("updateTest");
    	testData.setEmployeeNumber("99999");
    	testData.setDocumentCreateDate(result.getDocumentCreateDate());
    	testData.setTemplateId("t0000000002");
    	testData.setDocumentPublish(false);

    	assertEquals("文書の更新が正しく動作していません",testData , result);
    }

    @Test
    public void 公開設定の変更() {
    	documentLogic.toggleDocumentPublish("d0000000000", false);

    	boolean result = documentInfoRepository.findOne("d0000000000").isDocumentPublish();

    	assertEquals("公開設定の変更が正しく動作していません",false , result);
    }

    @Test
    public void タグの保存() throws IdIssuanceUpperException {
    	List<Tag> tagList = new ArrayList<>();
    	tagList.add(new Tag("n0000000001","2018年度新人研修"));
    	tagList.add(new Tag("n0000000002","ITパスポート研修"));
    	documentLogic.saveTags("d0000000003", tagList);
    	List<DocumentTag> result = documentTagRepository.findByDocumentId("d0000000003");

    	List<DocumentTag> testData = new ArrayList<>();
    	testData.add(new DocumentTag("d0000000003",1,"n0000000001"));
    	testData.add(new DocumentTag("d0000000003",2,"n0000000002"));
    	testData.add(new DocumentTag("d0000000003",3,"s0000000002"));

    	assertEquals("タグの保存が正しく動作していません",testData , result);
    }

    @Test
    public void 文書の削除() throws IdIssuanceUpperException {
    	documentLogic.deleteDocument("d0000000001");

		int result = 0;
		if (bookmarkRepository.findOne("d0000000001") != null)
			result+=1;
		if (!documentTagRepository.findByDocumentId("d0000000001").isEmpty())
			result+=10;
		if (!documentItemRepository.findByDocumentIdOrderByContentOrderAscLineNumberAsc("d0000000001").isEmpty())
			result+=100;
		if (commentRepository.findOne("d0000000001") != null)
			result+=1000;
		if (updateInfoRepository.findOne("d0000000001") != null)
			result+=10000;
		if (documentInfoRepository.findOne("d0000000001") != null)
			result+=100000;

		assertEquals("文書の削除が正しく動作していません", 0, result);
	}

    private List<DocumentContentForm> 文章データ(){
		List<DocumentContentForm> contentList = new ArrayList<>();

		DocumentContentForm content = new DocumentContentForm();
		content.setContentOrder(1);
		ArrayList<String> text = new ArrayList<>();
		text.add("小テスト、再テスト");
		text.add("まとめテスト");
		content.setItems(text);
		contentList.add(content);

		content = new DocumentContentForm();
		content.setContentOrder(2);
		text = new ArrayList<>();
		text.add("苦手分野");
		text.add("これまでの小テストと今回のまとめテストを通じて、システム構成・ネットワークの分野が苦手という事が分かった。アクセスやリクエスト処理などのイメージがつかめていなっかたので、分かる人に教わろうと思う。");
		text.add("効率の良い勉強方法");
		text.add(
				"明日の模擬テストのためにシステム構成・ネットワークの分野を中心に勉強し、過去問道場を使って対策を練ろうと思う。");
		text.add("OSS");
		text.add("OSSはソースコードの改良や再配布は許されているが、元のソフトウェアの著作権は放棄していないという事が分かった。");
		content.setItems(text);
		contentList.add(content);

		content = new DocumentContentForm();
		content.setContentOrder(3);
		text = new ArrayList<>();
		text.add("データベースの講義の前に、予習しておけることは何かありますか。");
		content.setItems(text);
		contentList.add(content);

		return contentList;
    }
}
