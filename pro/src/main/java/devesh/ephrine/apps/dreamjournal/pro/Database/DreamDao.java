package devesh.ephrine.apps.dreamjournal.pro.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import devesh.ephrine.apps.dreamjournal.pro.Data.Dream;

@Dao
public interface DreamDao {

    @Query("SELECT * FROM dream")
    List<Dream> getAllDream();

    @Insert
    void insertDream(Dream dream);

    @Delete
    void deleteDream(Dream dream);

    @Update
    void updateDream(Dream dream);

    @Query("SELECT * FROM dream WHERE dream.id = :id")
    Dream getDream(int id);

}
