/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.resource;


/**
 * Folder source
 * 
 * @version Nov 12, 2009 6:40:51 PM
 * @author Dingmeng Xue (xue_dingmeng@emc.com)
 */
public class ResourceDef
{
    public static final String TYPE_MOVIE = "movie";

    public static final String TYPE_GAME = "game";

    public static final String TYPE_MUSIC = "music";

    public static final String TYPE_OTHER = "other";
    
    private String rootPath;

    private String type;

    private boolean checkFolder = true;

    private boolean checkFile = true;

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
}
