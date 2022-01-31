package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;



public class news {

    public long id;
    public String category;
    public String title;
    public String description;
    public String keyword;
    public String date;
    public String admin;
    public Boolean isactive;
    public int likes;


    public  news(){

    }

    public news(long id,String category, String title, String description, String keyword, String date, String admin) {

        this.id = id;
        this.category= category;
        this.title = title;
        this.description = description;
        this.keyword = keyword;
        this.date = date;
        this.admin=admin;
        this.isactive=true;
        this.likes=0;

    }
    public news( String category, String title, String description, String keyword, String date, String admin) {

        this.title = title;
        this.category= category;
        this.description = description;
        this.keyword = keyword;
        this.date = date;
        this.admin=admin;
        this.isactive=true;
        this.likes=0;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getCategory() {
        return category;
    }

    public int getLikes() {
        return likes;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }



    public Boolean getIsactive() {
        return isactive;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
