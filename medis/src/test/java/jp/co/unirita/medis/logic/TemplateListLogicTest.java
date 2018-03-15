package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.domain.templateinfo.TemplateInfo;
import jp.co.unirita.medis.logic.template.TemplateListLogic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/template_info-insert.sql"})
public class TemplateListLogicTest {

    @Autowired
    TemplateListLogic templateListLogic;

    @Test
    public void テンプレート情報を全件取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getAllTemplateInfoList();
        assertEquals("情報が全件取得できませんでした", 10, templateInfoList.size());
    }

    @Test
    public void 公開済みのものを全件取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getAllTemplateInfoList(true);
        assertEquals("公開済みが全件取得できませんでした", 5, templateInfoList.size());
    }

    @Test
    public void 下書きのものを取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getAllTemplateInfoList(false);
        assertEquals("下書きが全件取得できませんでした", 5, templateInfoList.size());
    }

    @Test
    public void 社員番号9999のすべてのテンプレート情報を取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getAllTemplateInfoList("99999");
        assertEquals("社員番号99999の情報が全件取得できませんでした", 6, templateInfoList.size());
    }

    @Test
    public void 存在しない社員番号のテンプレート情報を取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getAllTemplateInfoList("00000");
        assertEquals("不明な情報が取得されています", 0, templateInfoList.size());
    }

    @Test
    public void 社員番号9999の公開済みのテンプレート情報を取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getTemplateInfoList("99999", true);
        assertEquals("社員番号99999の公開済みが全件取得できませんでした", 3, templateInfoList.size());
    }

    @Test
    public void 社員番号9999の下書きのテンプレート情報を取得する場合() {
        List<TemplateInfo> templateInfoList = templateListLogic.getTemplateInfoList("99999", false);
        assertEquals("社員番号99999の公開済みが全件取得できませんでした", 3, templateInfoList.size());
    }
}
