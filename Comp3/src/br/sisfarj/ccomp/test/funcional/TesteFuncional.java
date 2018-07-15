package br.sisfarj.ccomp.test.funcional;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;

public abstract class TesteFuncional extends DBTestCase {

	public TesteFuncional(String metodo) {
		super(metodo);
		
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.apache.derby.jdbc.EmbeddedDriver");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:derby:C:\\Users\\email\\sisfarj;create=true" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "comp3");
	}
	
	@BeforeClass
	public void zerarBD () {
		System.out.println("OI");
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}
	
}
