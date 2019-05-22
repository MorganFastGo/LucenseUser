package com.itheima.lucense;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class LucenseSearch {
@Test
    public void TermQueryTest() throws IOException {
        Directory directory =FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath());
        IndexReader indexReader= DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Query query = new TermQuery(new Term("fileContent","spring"));
        TopDocs topDocs=indexSearcher.search(query,10);
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            String fileName = doc.get("fileName");
            String filePath = doc.get("filePath");
            System.out.println(fileName);
            System.out.println(filePath);
        }
    }

    @Test
    public void RangeQueryTest() throws IOException {
        Directory directory=FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath());
        IndexReader indexReader  = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Query query = LongPoint.newRangeQuery("fileSize" ,01, 1000);
        TopDocs topDocs=indexSearcher.search(query,20);
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            String fileName = doc.get("fileName");
            System.out.println(fileName);
        }
    }

    private IndexSearcher indexSearcher;
    @Before
    public void init() throws IOException {
        Directory directory=FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath());
        IndexReader indexReader=DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    @Test

    public void QueryParseTest() throws ParseException, IOException {
        QueryParser queryParser = new QueryParser("fileName",new IKAnalyzer());
        Query query = queryParser.parse("查询spring");
        method(query);
    }

    private void method(Query query) throws IOException {
       TopDocs topDocs= indexSearcher.search(query,10);
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            String fileName = doc.get("fileName");
            String filePath = doc.get("filePath");
            System.out.println(fileName);
            System.out.println(filePath);
        }
    }

}
