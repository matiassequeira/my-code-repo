/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.kuokuo.client.Search;
import org.kuokuo.resource.ResourceDef;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * configuration
 * 
 * @version Nov 18, 2009 9:49:00 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class Configuration
{
    private static Configuration instance;

    public static Configuration getInstance()
    {
        if (instance == null)
        {
            instance = new Configuration();
        }
        return instance;
    }

    private static final String CONFIG_FILE = "/kuokuo.cfg.xml";

    private Configuration()
    {
        try
        {
            loadConfiguration();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadConfiguration() throws Exception
    {
        InputStream is = this.getClass().getResourceAsStream(CONFIG_FILE);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element root = document.getDocumentElement();
        for (Node node = root.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if ("version".equals(node.getNodeName()) && node instanceof Element)
            {
                version = ((Element) node).getTextContent();
            }
            if ("index".equals(node.getNodeName()) && node instanceof Element)
            {
                loadIndexElement((Element) node);
            }
            if ("resources".equals(node.getNodeName()) && node instanceof Element)
            {
                loadResourcesElement((Element) node);
            }
        }

    }

    private void loadIndexElement(Element el)
    {
        for (Node node = el.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if ("interval".equals(el.getNodeName()) && node instanceof Element)
            {
                indexInterval = Integer.parseInt(((Element) node).getTextContent());
            }
        }
    }

    private void loadResourcesElement(Element el)
    {
        resources = new Vector<ResourceDef>();
        for (Node node = el.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if ("resource".equals(node.getNodeName()) && node instanceof Element)
            {
                ResourceDef def = loadResourceElememt((Element) node);
                if (def != null)
                {
                    resources.add(def);
                }
            }
        }
    }

    private ResourceDef loadResourceElememt(Element el)
    {
        if (!el.hasAttribute("path"))
        {
            return null;
        }
        ResourceDef def = new ResourceDef();
        def.setRootPath(el.getAttribute("path"));
        def.setType((el.hasAttribute("type")) ? el.getAttribute("type") : Search.TYPE_OTHER);
        def.setIncludeFolder((el.hasAttribute("include-folder")) ? Boolean.parseBoolean(el.getAttribute("include-folder")) : true);
        def.setIncludeFile((el.hasAttribute("include-file")) ? Boolean.parseBoolean(el.getAttribute("include-file")) : false);
        return def;
    }

    /**
     * index interval, unit is minutes and 3 hours is default;
     */
    private int indexInterval = 3 * 60;

    public int getIndexInterval()
    {
        return indexInterval;
    }

    private String version = "";

    public String getVersion()
    {
        return version;
    }

    private List<ResourceDef> resources;

    public List<ResourceDef> getResourceDefs()
    {
        return resources;
    }
}
