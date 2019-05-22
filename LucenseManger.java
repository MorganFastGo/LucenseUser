package com.itheima.lucense;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class LucenseManger {
    @Test
    public void DeletLucense() throws Exception {
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath()), new IndexWriterConfig());
        indexWriter.deleteDocuments(new Term("fileContent","spring"));
        indexWriter.close();
    }

    @Test
    public void deletAllLucense() throws IOException {
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath()), new IndexWriterConfig());
        indexWriter.deleteAll();
        indexWriter.close();
    }

    @Test
    public void  addDocument() throws IOException {
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath()), new IndexWriterConfig());
        Document document = new Document();
        document.add(new TextField("fileName","这是新添加的", Field.Store.YES));
        document.add(new TextField("fileContent","新添加的内容",Field.Store.YES));
        document.add(new TextField("filePath","f://index",Field.Store.YES));
        indexWriter.addDocument(document);
        indexWriter.close();

    }
    @Test
    public void updateDocument()throws Exception {
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\WebStage\\Lucense\\IndexBase").toPath()), new IndexWriterConfig());
        Document document = new Document();
        document.add(new TextField("fileName","这是新添加的", Field.Store.YES));
        document.add(new TextField("fileContent","新添加的内容",Field.Store.YES));
        document.add(new TextField("filePath","f://index",Field.Store.YES));
        indexWriter.updateDocument(new Term("fileContent","spring"),document);
        indexWriter.close();
    }
}
