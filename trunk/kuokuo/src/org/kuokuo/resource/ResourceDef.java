/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.resource;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Folder source
 * 
 * @version Nov 12, 2009 6:40:51 PM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class ResourceDef
{
    private String rootPath;

    private String type;

    private boolean checkFolder = true;

    private boolean checkFile = true;

    private Set<Pattern> excludes;

    /**
     * @return Returns the path.
     */
    public String getRootPath()
    {
        return rootPath;
    }

    /**
     * @param rootPath
     *            The path to set.
     */
    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }

    /**
     * @return Returns the type.
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return Returns the checkFolder.
     */
    public boolean isIncludeFolder()
    {
        return checkFolder;
    }

    /**
     * @param checkFolder
     *            The checkFolder to set.
     */
    public void setIncludeFolder(boolean checkFolder)
    {
        this.checkFolder = checkFolder;
    }

    /**
     * @return Returns the checkFile.
     */
    public boolean isIncludeFile()
    {
        return checkFile;
    }

    /**
     * @param checkFile
     *            The checkFile to set.
     */
    public void setIncludeFile(boolean checkFile)
    {
        this.checkFile = checkFile;
    }

    public void addExclude(String exclude)
    {
        if (excludes == null)
            excludes = new HashSet<Pattern>();
        
        if(exclude != null)
        {
            exclude = exclude.toLowerCase();
            exclude = exclude.replace('.', '#').replaceAll("#", "\\\\.").replace('*', '#').replaceAll("#", ".*").replace('?', '#').replaceAll("#", ".?");
            exclude = "^" + exclude + "$";
            Pattern p = Pattern.compile(exclude);
            excludes.add(p);
        }
    }

    public Set<Pattern> getExcludes()
    {
        return excludes;
    }
}
