package com.example.masud.gridviewpractice;

/**
 * Created by MASUD on 3/31/2016.
 */
public class Item
{
    String title;
    int size;
    // Empty Constructor
    public Item()
    {

    }

    // Constructor
    public Item(String title,int size)
    {
        super();
        this.title = title;
        this.size=size;
    }

    // Getter and Setter Method
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }



}

