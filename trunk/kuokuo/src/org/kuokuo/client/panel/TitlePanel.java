/**
 * Copyright  1994-2009. EMC Corporation. All Rights Reserved.
 */
package org.kuokuo.client.panel;

import org.kuokuo.client.Search;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Title Panel
 * 
 * @version Nov 15, 2009 1:34:27 AM
 * @author Dingmeng (xuedm79@gmail.com)
 */
public class TitlePanel extends HorizontalPanel implements ClickHandler, KeyPressHandler
{
    protected Button btnSearch;

    protected TextBox txtQuery;

    protected Button btnLucky;

    protected CheckBox chkAll;

    protected CheckBox chkMovie;

    protected CheckBox chkMusic;

    protected CheckBox chkGame;

    protected CheckBox chkBook;

    protected CheckBox chkOther;

    protected SimplePanel logoSpacer;
    
    public TitlePanel()
    {
        SimplePanel logoSpacer = new SimplePanel();
        logoSpacer.setWidth("160px");
        this.add(logoSpacer);
        this.setStylePrimaryName("title");
        
        VerticalPanel searchPanel = new VerticalPanel();

        SimplePanel spacer = new SimplePanel();
        spacer.setHeight("50px");
        searchPanel.add(spacer);
        
        HorizontalPanel firstRow = new HorizontalPanel();
        firstRow.setSpacing(5);
        firstRow.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        txtQuery = new TextBox();
        txtQuery.setWidth("300px");
        txtQuery.addKeyPressHandler(this);
        firstRow.add(txtQuery);
        
        btnSearch = new Button("搜索");
        firstRow.add(btnSearch);
        btnSearch.addClickHandler(this);
        searchPanel.add(firstRow);

        HorizontalPanel secondRow = new HorizontalPanel();
        chkAll = new CheckBox("全部");
        chkMovie = new CheckBox("电影");
        chkMusic = new CheckBox("音乐");
        chkGame = new CheckBox("游戏");
        chkBook = new CheckBox("图书");
        chkOther = new CheckBox("其它");
        chkAll.setValue(true);
        chkMovie.setValue(true);
        chkGame.setValue(true);
        chkMusic.setValue(true);
        chkBook.setValue(true);
        chkOther.setValue(true);
        chkAll.addClickHandler(this);
        chkMovie.addClickHandler(this);
        chkMusic.addClickHandler(this);
        chkGame.addClickHandler(this);
        chkOther.addClickHandler(this);
        chkBook.addClickHandler(this);
        secondRow.setSpacing(5);
        secondRow.add(chkAll);
        secondRow.add(chkMovie);
        secondRow.add(chkMusic);
        secondRow.add(chkGame);
        secondRow.add(chkBook);
        secondRow.add(chkOther);
        chkAll.setEnabled(false);
        chkMovie.setEnabled(false);
        chkMusic.setEnabled(false);
        chkGame.setEnabled(false);
        chkBook.setEnabled(false);
        chkOther.setEnabled(false);

        searchPanel.add(secondRow);

        this.add(searchPanel);
    }

    /**
     * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event)
    {
        if (event.getSource().equals(chkAll))
        {
            chkMovie.setValue(chkAll.getValue());
            chkMusic.setValue(chkAll.getValue());
            chkGame.setValue(chkAll.getValue());
            chkOther.setValue(chkAll.getValue());
            chkBook.setValue(chkAll.getValue());
        }
        if (event.getSource().equals(chkMovie) || event.getSource().equals(chkMusic) || event.getSource().equals(chkGame) || event.getSource().equals(chkOther)|| event.getSource().equals(chkBook))
        {
            boolean selectAll = chkMovie.getValue() && chkMusic.getValue() && chkGame.getValue() && chkOther.getValue();
            chkAll.setValue(selectAll);
        }
        if(event.getSource().equals(btnSearch))
        {
            if(txtQuery.getValue() != null && !txtQuery.getValue().trim().equals(""))
            {
                Search.getInstance().doSearch(txtQuery.getValue());
            }
        }
        if(event.getSource().equals(logoSpacer))
        {
            Search.getInstance().gotoHomepage();
        }
    }

    /**
     * @see com.google.gwt.event.dom.client.KeyPressHandler#onKeyPress(com.google.gwt.event.dom.client.KeyPressEvent)
     */
    public void onKeyPress(KeyPressEvent event)
    {
        if(event.getSource().equals(txtQuery) && event.getCharCode() == 13)
        {
            if(txtQuery.getValue() != null && !txtQuery.getValue().trim().equals(""))
            {
                Search.getInstance().doSearch(txtQuery.getValue());
            }
        }
    }

    public void reset()
    {
        txtQuery.setText("");
    }
    
}
