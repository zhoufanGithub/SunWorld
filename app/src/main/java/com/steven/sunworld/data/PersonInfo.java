package com.steven.sunworld.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: zhoufan
 * data: 2021/7/20 14:09
 * content:GreenDao的实体类
 */
@Entity
public class PersonInfo {

    // 设置主键自增长
    @Id(autoincrement = true)
    private Long id;

    // 设置唯一性
    @Index(unique = true)
    private int bookId;

    private String bookName;
    private String authorName;
    private String bookNotes;
    private String playUrl;
    @Generated(hash = 559777481)
    public PersonInfo(Long id, int bookId, String bookName, String authorName,
            String bookNotes, String playUrl) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookNotes = bookNotes;
        this.playUrl = playUrl;
    }
    @Generated(hash = 1597442618)
    public PersonInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getBookId() {
        return this.bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBookNotes() {
        return this.bookNotes;
    }
    public void setBookNotes(String bookNotes) {
        this.bookNotes = bookNotes;
    }
    public String getPlayUrl() {
        return this.playUrl;
    }
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
