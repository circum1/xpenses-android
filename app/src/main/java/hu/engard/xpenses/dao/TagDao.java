package hu.engard.xpenses.dao;

import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;

public class TagDao extends AbstractDao {
	public final static String C_ID="_id".toLowerCase(Locale.getDefault());
	public final static String C_UID="uid".toLowerCase(Locale.getDefault());
	public final static String C_PARENT="parent".toLowerCase(Locale.getDefault());
	public final static String C_CATEGORY="category".toLowerCase(Locale.getDefault());
	public final static String C_LABEL="label".toLowerCase(Locale.getDefault());
	public final static String C_COMMENT="comment".toLowerCase(Locale.getDefault());
	
	@Override
	public String getTableName() {return "Tag";}
	
	@Override
	protected ContentValues convertSyncMapToCV(HashMap<String, Object> map) {
		// TODO C_CATEGORY
		ContentValues cv=map2cv(map, C_UID, C_LABEL, C_COMMENT);
		String parentUid = (String)map.get(C_PARENT);
		if (parentUid!=null) {
			int parentId=uid2id(parentUid);
			if (parentId<0) throw new RuntimeException("Invalid tag parent uid "+parentUid);
			cv.put(C_PARENT, parentId);
		}
		return cv;
	}
}
