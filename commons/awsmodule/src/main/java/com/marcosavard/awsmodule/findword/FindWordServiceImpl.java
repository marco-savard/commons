package com.marcosavard.awsmodule.findword;

import java.util.List;
import com.marcosavard.common.ling.fr.dic.FrDictionaryReader;
import org.springframework.stereotype.Service;

@Service
class FindWordServiceImpl implements FindWordService {
    @Override
    public String[] findWords(String pattern) {
        FrDictionaryReader reader = new FrDictionaryReader();
        List<String> words = reader.readWordsMatching(pattern);
        return words.toArray(new String[0]);
    }


}
