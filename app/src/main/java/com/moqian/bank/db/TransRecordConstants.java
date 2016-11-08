package com.moqian.bank.db;

import java.security.PublicKey;

public class TransRecordConstants {
	
	public final static String RECORD_TABLE_NAME= "transformRecord";
	public final static class Columns{
		public final static String COLLECTION_ACCOUNT ="collectionAccount";
		public final static String COLLECTION_BANK= "collectionBank";
		public final static String NAME = "name";
		public final static String AMOUNT = "amount"; 
		public final static String STATE = "state"; 
		public final static String _ID = "_id";
		public final static String DATE = "date";
		
		public final static String WITHWOUT_ID_COLUMNS[] = {COLLECTION_ACCOUNT,
			COLLECTION_BANK,NAME,AMOUNT,STATE,DATE,_ID
	};
	}

}
