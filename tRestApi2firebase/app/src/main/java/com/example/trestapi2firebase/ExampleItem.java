package com.example.trestapi2firebase;

public class ExampleItem {
    private int mImageResource;
    private String mdate;
    private String mtitle;
    private String maddress;

    public ExampleItem(int ImageResource, String date, String title, String address) {
        mImageResource = ImageResource;
        mdate = date;
        mtitle = title;
        maddress = address;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getdate() {
        return mdate;
    }

    public String gettitle() {
        return mtitle;
    }

    public String getMaddress() {
        return maddress;
    }

}
