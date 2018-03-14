package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.logic.util.SearchLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/document_info-delete.sql", "file:resources/sql/document_info-insert.sql",
		"file:resources/sql/template_tag_monitoringlogictest-delete.sql",
		"file:resources/sql/template_tag_monitoringlogictest-insert.sql",
		"file:resources/sql/document_tag_monitoringlogictest-delete.sql",
		"file:resources/sql/document_tag_monitoringlogictest-insert.sql",
		"file:resources/sql/tag-delete.sql","file:resources/sql/tag-insert.sql" })
public class SearchLogicTest {

    @Autowired
    SearchLogic searchLogic;
    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    UserDetailRepository userDetailRepository;


    @Test
    public void 検索動作確認() {
    	List<DocumentInfoForm> testData = new ArrayList<>();
    	DocumentInfo info = documentInfoRepository.findOne("d0000000001");
    	testData.add(new DocumentInfoForm(info, userDetailRepository.findOne(info.getEmployeeNumber())));
    	info = documentInfoRepository.findOne("d0000000003");
    	testData.add(new DocumentInfoForm(info, userDetailRepository.findOne(info.getEmployeeNumber())));

    	List<String> tagNameList = new ArrayList<>(Arrays.asList( "2018年度新人研修","小テスト"));
    	List<DocumentInfoForm> documentList = searchLogic.findDocuments(tagNameList);
        assertEquals("ユーザのロードができませんでした", testData, documentList);
    }
}
