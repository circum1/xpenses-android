package hu.engard.xpenses.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteScriptRunner {
	public static void run(SQLiteDatabase db, String script) {
		String commentRE="--.*";
		String stmtRE="((?:(?>[^;']*)|(?>'[^']*'))+);";
		Pattern re=Pattern.compile("(\\s+)|("+commentRE+")|("+stmtRE+")");
		Matcher matcher=re.matcher(script);
		while(matcher.find()) {
			String s=matcher.group();
			if (!s.matches("^\\s*$") && !s.startsWith("--")) {
				s=s.substring(0, s.length()-1);
				StringBuffer sb=new StringBuffer(s.length());
				Matcher m=Pattern.compile("('[^']*')|--.*").matcher(s);
				while (m.find()) {
					m.appendReplacement(sb, m.group(1)==null ? "" : m.group(1));
				}
				m.appendTail(sb);
//				Log.i("ScriptRunner", "stmt: '"+sb.toString()+"'");
				db.execSQL(sb.toString());
			}
		}
	}
}
