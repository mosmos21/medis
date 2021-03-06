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

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.logic.util.TagLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/tag-insert.sql"})
public class TagLogicTest {

    @Autowired
    TagLogic tagLogic;

    @Test
    public void タグ一覧取得() {
        List<Tag> tagList = tagLogic.getTagList();
        assertEquals("タグ一覧の取得ができませんでした", 4, tagList.size());
    }
/*
    @Test
    public void 新規システムタグID取得_フォーマット() throws IdIssuanceUpperException {

        String tagId = tagLogic.createSystemTagId();
        assertEquals("新規システムタグIDの取得（フォーマット）が正しく動作していません", "s%010d", tagId);
    }
*/
    @Test
    public void タグID振り分け() throws IdIssuanceUpperException {
		List<Tag> tagList = new ArrayList<>(); //除外対象を含むテストデータ
		for (int i = 0; i < 5; i++) {
			Tag tag = new Tag();
			tag.setTagId("");
			tag.setTagName("testTag" + i);
	    	tagList.add(tag);
			tag = new Tag();
			tag.setTagId("n000000009" + i);
			tag.setTagName("dummyTag" + i);
	    	tagList.add(tag);
		}
        List<Tag> result = tagLogic.applyTags(tagList);

        List<Tag> testData = new ArrayList<>(); //比較用のデータ
		for (int i = 0; i < 5; i++) {
			Tag tag = new Tag();
			tag.setTagId("n000000000" + (i+4)); //初期データ分+4
			tag.setTagName("testTag" + i);
			testData.add(tag);
		}
        assertEquals("タグID振り分けが正しく動作していません", testData, result);
    }

}
