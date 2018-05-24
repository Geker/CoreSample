package org.LuceneSolrSample;

 import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
 /**
 * 测试分词器
 * 分词器工作流程
 *     1.切分，将需要分词的内容进行切分成每个单词或者词语
 *     2.去除停用词，有些词在文本中出现的频率非常高，但是对文本所携带的信息基本不产生影响，例如英文的“a、an、the、of”，或中文的“的、了、着、是”，以及各种标点符号等，
 * 这样的词称为停用词（stop word）。文本经过分词之后，停用词通常被过滤掉，不会被进行索引。在检索的时候，用户的查询中如果含有停用词，
 * 检索系统也会将其过滤掉（因为用户输入的查询字符串也要进行分词处理）。排除停用词可以加快建立索引的速度，减小索引库文件的大小。
 *     3.对于英文字母，转为小写，因为搜索的时候不区分大小写
 * @author kencery
 *
 */
public class AnalyzerTest {

    /**
     * StandardAnalyzer分词法测试,对中文支持不是很好,将中文分词成1个字(单字分词)
     * @throws Exception
     */
    @Test
    public void StandardAnalyzerTest() throws Exception{
        //英文测试
        String text="An IndexWriter creaters and maintains an index.textFiles[i].isFile() ";
        Analyzer analyzer=new StandardAnalyzer();
        LuceneUtils.displayTokens(analyzer,text);
        //中文测试
        String text1="Lucene是全文检索框架";
        LuceneUtils.displayTokens(analyzer,text1);
    }

     /**
      * CJKAnalyzerTest分词法测试,对中文支持不是很好，将中文分词成2个字(二分法分词)
      *
      * @throws Exception
      */
    @Test
    public void CJKAnalyzerTest() throws Exception{
        //英文测试
        String text="An IndexWriter creaters and maintains an index.";
        Analyzer analyzer=new CJKAnalyzer();
        LuceneUtils.displayTokens(analyzer,text);
        //中文测试
        String text1="Lucene是全文检索框架";
        LuceneUtils.displayTokens(analyzer,text1);
    }

     /**
      * IKAnalyzerTest分词法测试,对中文支持很好，词库分词
      * @throws Exception
      */
    @Test
    public void IKAnalyzerTest() throws Exception{
        //英文测试
        String text="An IndexWriter creaters and maintains an index.";
        Analyzer analyzer=new IKAnalyzer();
        LuceneUtils.displayTokens(analyzer,text);
        //中文测试
        String text1="韩迎龙易淘食的Lucene是全文检索框架";
        LuceneUtils.displayTokens(analyzer,text1);
    }


}