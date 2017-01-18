package store;

import store.db.sql.beans.WhereDefinition;

public class StoreMain {

	public static void main(String[] args) {

		
		String where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>";
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
		System.out.println(wd.generateWhereConditions());
		
	}

}
