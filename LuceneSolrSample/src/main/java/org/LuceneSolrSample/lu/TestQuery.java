package org.LuceneSolrSample.lu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TestQuery {
    public static void main(String[] args) throws IOException {
        // directory where your index is stored
        Path path = Paths.get("F:\\DevSoft\\lucene\\idx");

        Directory index = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);

//        Hits hits = null;
        String queryString = "alibaba";
        Query query = null;

        Analyzer analyzer = new StandardAnalyzer();
        try {
            QueryParser qp = new QueryParser("body", analyzer);
            query = qp.parse(queryString);
        }
        catch (ParseException e) {
        }
        if (searcher != null) {
              int hits = searcher.count(query);
            if (hits > 0) {
                System.out.println(" 找到: " + hits+ "  个结果! ");
            }
        }
    }

}