package com.nhncorp.student.sawonjungfinder.database;

import android.provider.BaseColumns;

public class Database {
	public static final class CreateDB implements BaseColumns {
		public static final String NAME = "name";
		public static final String ID = "id";
		public static final String MACADDRESS = "macaddress";
		public static final String ALARMSTATE = "alarmstate";
		public static final String DEVICESTATE = "devicestate";
		public static final String _TABLENAME = "beaconfinder";
		public static final String _CREATE = "create table " + _TABLENAME + "("
				+ ID + " integer primary key, " + NAME + " text not null , "
				+ MACADDRESS + " text not null , " + ALARMSTATE
				+ " text not null, " + DEVICESTATE + " text not null );";
	}
}
