package org.LuceneSolrSample.lu;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestQuery {
    public static void main(String[] args) throws IOException {
        // directory where your index is stored
        Path path = Paths.get("E:\\lucene\\idx");

        Directory idxD = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(idxD);
        IndexSearcher searcher = new IndexSearcher(reader);

//        Hits hits = null;
        String queryString = "ourselves";
         Term term=new Term("body", queryString);

        Query query=new TermQuery(term);
        TopDocs topDocs=searcher.search(query, 1000);
        System.out.println("共检索出 " + topDocs.totalHits + " 条记录");
        System.out.println();

    }

}