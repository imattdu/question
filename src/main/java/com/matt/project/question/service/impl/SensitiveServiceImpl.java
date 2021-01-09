package com.matt.project.question.service.impl;


import com.matt.project.question.service.SensitiveService;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author matt
 * @create 2021-01-09 15:10
 */
@Service
public class SensitiveServiceImpl implements InitializingBean, SensitiveService {

    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "敏感词";

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            InputStream in = contextClassLoader.getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(read);
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                word = word.trim();
                addWord(word);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TrieNode {

        private Boolean end = false;

        private Map<Character, TrieNode> subNodes = new HashMap<Character, TrieNode>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        public Boolean isKeyWordEnd() {
            return this.end;
        }

        public void setKeyWordEnd(Boolean end) {
            this.end = end;
        }
    }

    private TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode tempNode = root;

        for (int i = 0; i < word.length(); i++) {
            Character ch = word.charAt(i);
            if (isSymbol(ch)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(ch);
            if (node == null) {
                node = new TrieNode();
                tempNode.addSubNode(ch, node);
            }
            tempNode = node;
            if (i == word.length() - 1) {
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String word) {

        if (StringUtils.isBlank(word)) {
            return word;
        }

        StringBuilder result = new StringBuilder();
        String replacement = DEFAULT_REPLACEMENT;
        TrieNode tempNode = root;
        Integer start = 0;
        Integer position = 0;

        while (position < word.length()) {

            Character ch = word.charAt(position);

            // 非法字符
            if (isSymbol(ch)) {
                if (tempNode == root) {
                    result.append(ch);
                    ++start;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(ch);
            if (tempNode == null) {
                result.append(word.charAt(start));
                position = start + 1;
                start = position;
                tempNode = root;
            } else if (tempNode.isKeyWordEnd()) {
                result.append(replacement);
                position = position + 1;
                start = position;
                tempNode = root;
            } else {
                position++;
            }
        }
        result.append(word.substring(start));

        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        SensitiveServiceImpl s = new SensitiveServiceImpl();
        //s.afterPropertiesSet();
        s.addWord("色情");
        System.out.println(s.filter("eee色  情"));
    }
}
