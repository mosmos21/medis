package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.logic.util.IdIssuanceLogic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdIssuanceLogicTest {

    @Autowired
    IdIssuanceLogic idIssuanceLogic;

    @Before
    @After
    @Sql("file:resources/sql/data.sql")
    public void setup() {
    }

    @Test
    public void 新規テンプレートID取得() {
        try {
            String id = idIssuanceLogic.createTemplateId();
            assertEquals("発行されたIDが正しくありません", "t0000000003", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規ドキュメントID取得() {
        try {
            String id = idIssuanceLogic.createDocumentId();
            assertEquals("発行されたIDが正しくありません", "d0000000024", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規タグID取得() {
        try {
            String id = idIssuanceLogic.createTagId();
            assertEquals("発行されたIDが正しくありません", "n0000000016", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規システムタグID取得() {
        try {
            String id = idIssuanceLogic.createSystemTagId();
            assertEquals("発行されたIDが正しくありません", "s0000000006", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規コメントID取得() {
        try {
            String id = idIssuanceLogic.createCommentId();
            assertEquals("発行されたIDが正しくありません", "o0000000001", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規アップデートID取得() {
        try {
            String id = idIssuanceLogic.createUpdateId();
            assertEquals("発行されたIDが正しくありません", "u0000000038", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }

    @Test
    public void 新規ブックマークID取得() {
        try {
            String id = idIssuanceLogic.createBookmarkId();
            assertEquals("発行されたIDが正しくありません", "m0000000001", id);
        }catch (Exception e) {
            fail("予期せないエラーが発生しました");
        }
    }
}
