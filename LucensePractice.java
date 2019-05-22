package com.itheima.lucense;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LucensePractice {
    @Test
    public void CreateIndexBase() throws Exception {
        Directory directory= FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath());
        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(new IKAnalyzer()));
        File dir = new File("E:\\黑马web阶段视频下载\\Lucene\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            String filePath = file.getPath();
            String fileContent = FileUtils.readFileToString(file);
            long fileSize = FileUtils.sizeOf(file);
            Document document = new Document();
            document.add(new TextField("fileName",fileName, Field.Store.YES));
            document.add(new StoredField("filePath",filePath));
            document.add(new TextField("fileContent",fileContent,Field.Store.YES));
            document.add(new TextField("fileSize",fileSize+"",Field.Store.YES));
            document.add(new LongPoint("fileSize",fileSize));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }
    @Test
    public void searchIndex() throws Exception {
       Directory directory=FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Query query = new TermQuery(new Term("fileContent","计算机"));
        TopDocs topDocs = indexSearcher.search(query, 2);
        long totalHits = topDocs.totalHits;
        System.out.println(totalHits);
        ScoreDoc[] docs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : docs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            String fileName = doc.get("fileName");
            String fileContext = doc.get("fileContent");
            System.out.println(fileName);
            System.out.println("------------------------------------------");
            System.out.println(fileContext);
            System.out.println();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        }

    }
}
