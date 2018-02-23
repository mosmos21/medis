package jp.co.unirita.medis.logic.util;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagLogic {

    @Autowired
    TagRepository tagRepository;

    public String getNewTagId() throws Exception{
        List<Tag> list = tagRepository.findAll(new Sort(Sort.Direction.DESC, "tagId"));
        if(list.size() == 0) {
            return "n0000000000";
        }
        long idNum = Long.parseLong(list.get(0).getTagId().substring(1));
        if(idNum == 9999999999L) {
            throw new Exception("IDの発行限界");
        }
        return String.format("n%010d", idNum + 1);
    }

    public String getNewSystemTagId() throws Exception{
        List<Tag> list = tagRepository.findAll(new Sort(Sort.Direction.DESC, "tagId"));
        if(list.size() == 0) {
            return "s0000000000";
        }
        long idNum = Long.parseLong(list.get(0).getTagId().substring(1));
        if(idNum == 9999999999L) {
            throw new Exception("IDの発行限界");
        }
        return String.format("s%010d", idNum + 1);
    }
}
