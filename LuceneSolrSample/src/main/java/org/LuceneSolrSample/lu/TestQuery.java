package org.LuceneSolrSample.lu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TestQuery {
    public static void main(String[] args) throws IOException {
        // directory where your index is stored
        Path path = Paths.get("F:\\DevSoft\\lucene\\idx");

        Directory indexD = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(indexD);
        IndexSearcher searcher = new IndexSearcher(reader);
        TermQuery query = new TermQuery(new Term("body", "package"));

        TopDocs rs = searcher.search(query, 100);

        if (rs.totalHits > 0) {
            System.out.println(" 找到: " + rs.scoreDocs.length + "  个结果! ");
        }
    }

}