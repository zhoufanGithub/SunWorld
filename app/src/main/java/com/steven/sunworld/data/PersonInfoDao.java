package com.steven.sunworld.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PERSON_INFO".
*/
public class PersonInfoDao extends AbstractDao<PersonInfo, Long> {

    public static final String TABLENAME = "PERSON_INFO";

    /**
     * Properties of entity PersonInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BookId = new Property(1, int.class, "bookId", false, "BOOK_ID");
        public final static Property BookName = new Property(2, String.class, "bookName", false, "BOOK_NAME");
        public final static Property AuthorName = new Property(3, String.class, "authorName", false, "AUTHOR_NAME");
        public final static Property BookNotes = new Property(4, String.class, "bookNotes", false, "BOOK_NOTES");
        public final static Property PlayUrl = new Property(5, String.class, "playUrl", false, "PLAY_URL");
    }


    public PersonInfoDao(DaoConfig config) {
        super(config);
    }
    
    public PersonInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PERSON_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BOOK_ID\" INTEGER NOT NULL ," + // 1: bookId
                "\"BOOK_NAME\" TEXT," + // 2: bookName
                "\"AUTHOR_NAME\" TEXT," + // 3: authorName
                "\"BOOK_NOTES\" TEXT," + // 4: bookNotes
                "\"PLAY_URL\" TEXT);"); // 5: playUrl
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_PERSON_INFO_BOOK_ID ON \"PERSON_INFO\"" +
                " (\"BOOK_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PERSON_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PersonInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBookId());
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(3, bookName);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(4, authorName);
        }
 
        String bookNotes = entity.getBookNotes();
        if (bookNotes != null) {
            stmt.bindString(5, bookNotes);
        }
 
        String playUrl = entity.getPlayUrl();
        if (playUrl != null) {
            stmt.bindString(6, playUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PersonInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getBookId());
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(3, bookName);
        }
 
        String authorName = entity.getAuthorName();
        if (authorName != null) {
            stmt.bindString(4, authorName);
        }
 
        String bookNotes = entity.getBookNotes();
        if (bookNotes != null) {
            stmt.bindString(5, bookNotes);
        }
 
        String playUrl = entity.getPlayUrl();
        if (playUrl != null) {
            stmt.bindString(6, playUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PersonInfo readEntity(Cursor cursor, int offset) {
        PersonInfo entity = new PersonInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // bookId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bookName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // authorName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // bookNotes
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // playUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PersonInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBookId(cursor.getInt(offset + 1));
        entity.setBookName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthorName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBookNotes(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPlayUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PersonInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PersonInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PersonInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
