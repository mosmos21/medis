package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.form.document.DocumentInfoForm;
import jp.co.unirita.medis.logic.document.DocumentListLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/user_detail-insert.sql", "file:resources/sql/document_info-insert.sql",
		"file:resources/sql/update_info-insert.sql" })
public class DocumentListLogicTest {

    @Autowired
    DocumentListLogic documentListLogic;

    @Test
    public void ドキュメントリストの取得_publishTypeを指定しない場合() {
        List<DocumentInfoForm> result = documentListLogic.getDocumentList("97966",null);
        assertEquals("ユーザの文書一覧の取得(publishTypeを指定しない場合)が正しく動作していません",4 , result.size());
    }

    @Test
    public void ドキュメントリストの取得_publishTypeを指定した場合() {
        List<DocumentInfoForm> result = documentListLogic.getDocumentList("97966","public");
        assertEquals("ユーザの文書一覧の取得(publishTypeを指定した場合)が正しく動作していません",2 , result.size());
    }
}
