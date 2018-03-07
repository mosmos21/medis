package jp.co.unirita.medis.logic.util;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
public class TagLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String COMMENT_NOTIFICATION_TAG_ID = "g0000000000";

	@Autowired
	TagRepository tagRepository;

	public List<Tag> getTagList() {
		String pattern = "\\d{4}/\\d{2}/\\d{2}";
		Pattern p = Pattern.compile(pattern);
		List<Tag> tagInfoList = tagRepository.findByTagIdNotOrderByTagIdAsc(COMMENT_NOTIFICATION_TAG_ID); // コメント通知設定用のタグだけ除く
		List<Tag> tagList = new ArrayList<>();

		for (Tag add : tagInfoList) {
			if (p.matcher(add.getTagName()).find()) {
			} else {
				tagList.add(add);
			}
		}
		return tagList;
	}

	public String getNewTagId() throws IdIssuanceUpperException {
		List<Tag> list = tagRepository.findAll(new Sort(Sort.Direction.DESC, "tagId"));
		if (list.size() == 0) {
			return "n0000000000";
		}
		long idNum = Long.parseLong(list.get(0).getTagId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("IDの発行限界");
		}
		return String.format("n%010d", idNum + 1);
	}

	public String getNewSystemTagId() throws IdIssuanceUpperException {
		List<Tag> list = tagRepository.findAll(new Sort(Sort.Direction.DESC, "tagId"));
		if (list.size() == 0) {
			return "s0000000000";
		}
		long idNum = Long.parseLong(list.get(0).getTagId().substring(1));
		if (idNum == 9999999999L) {
			throw new IdIssuanceUpperException("IDの発行限界");
		}
		return String.format("s%010d", idNum + 1);
	}

	public Tag createTag(String value) throws Exception {
	    String id = getNewTagId();
	    Tag tag = new Tag(id, value);
	    tagRepository.saveAndFlush(tag);
	    return tag;
	}

	public List<Tag> applyTags(List<Tag> tags) throws Exception{
		List<String> newTags = tags.stream()
                .filter(tag -> tag.getTagId().equals(""))
                .filter(tag -> tagRepository.findByTagName(tag.getTagName()).size() == 0)
                .map(Tag::getTagName)
                .collect(Collectors.toList());
        for (String value : newTags) {
            createTag(value);
        }
        return tagRepository.findByTagNameIn(tags.stream().map(Tag::getTagName).collect(Collectors.toList()));
	}
}
