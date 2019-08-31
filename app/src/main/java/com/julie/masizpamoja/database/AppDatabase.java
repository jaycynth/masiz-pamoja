package com.julie.masizpamoja.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.julie.masizpamoja.models.SavedMessage;


@Database(entities = {SavedMessage.class},version = 4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    private static volatile AppDatabase INSTANCE;
    private static final Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "masizPamoja_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    //dao
    public abstract MessageDao messageDao();


}
