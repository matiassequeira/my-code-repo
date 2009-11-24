/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.resource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * Resource reader
 * 
 * @version Nov 12, 2009 7:46:12 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class ResourceReader
{
	
	private static Logger logger= Logger.getLogger(ResourceReader.class.getName());
	
    public ResourceReader(ResourceDef source)
    {
        rootPath = source.getRootPath();
        includeFolder = source.isIncludeFolder();
        includeFile = source.isIncludeFile();
        type = source.getType();
        
        files = new Vector<File>();
        loadFiles(new File(rootPath));
    }

    protected String rootPath;

    protected boolean includeFolder = false;

    protected boolean includeFile = false;

    protected String type;
    
    private void loadFiles(File path)
    {
        File[] files = path.listFiles();
        if(files==null){
        	throw new IllegalArgumentException("Fail to open folder "+path);
        }
        for (File file : files)
        {
            if (needIgnoreFile(file))
            {
                continue;
            }
            if (includeFile && file.isFile())
            {
                this.files.add(file);
            }
            if (file.isDirectory())
            {
                if (includeFolder)
                {
                    this.files.add(file);
                }
                loadFiles(file);
            }
        }
        logger.info("Scanned "+path);
    }
    
    /**
     * Determine whether the specified file need ignore while indexing
     * @param file
     * @return true if need ignore
     */
    private static boolean needIgnoreFile(File file){
        //Avoid NPE, should never happen
        if(file==null){
            return true;
        }
        if(!file.exists()){
            return true;
        }
        //hide some system files such as .DS_Store on Mac OS, or other hidden files
        if(file.isHidden()){
            return true;
        }
        return false;
    }

    public Iterator<Document> iterator()
    {
        return new Itr();
    }

    private Document generateDocument(File file)
    {
        Document doc = new Document();
        String name = file.getName();
        String suffix = "";
        if(!file.isDirectory())
        {
            int pos = name.lastIndexOf(".");
            if(pos > 0)
            {
                suffix = name.substring(pos + 1);
            }
        }
        
        doc.add(new Field("name", name, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("type", type, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("suffix", suffix, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("lastModified", sdf.format(new Date(file.lastModified())), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("timestamp", Long.toString(file.lastModified()), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("isFolder", Boolean.toString(file.isDirectory()), Field.Store.YES, Field.Index.NO));
        return doc;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d H:mm");
    
    private List<File> files;

    private class Itr implements Iterator<Document>
    {
        int cursor = 0;

        /**
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext()
        {
            return (cursor < files.size());
        }

        /**
         * @see java.util.Iterator#next()
         */
        public Document next()
        {
            File file = files.get(cursor);
            cursor++;
            return generateDocument(file);
        }

        /**
         * @see java.util.Iterator#remove()
         * @deprecated unsupport
         */
        public void remove()
        {

        }
    }
}
