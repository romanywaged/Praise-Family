package com.example.romany.DB;

import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MarIvram.Name,version = MarIvram.Version,
        insertConflict = ConflictAction.IGNORE,
        updateConflict= ConflictAction.REPLACE,
        foreignKeyConstraintsEnforced = true
)
public class MarIvram {
    public static final String Name = "MarIvram";
    public static final int Version = 12;
}
