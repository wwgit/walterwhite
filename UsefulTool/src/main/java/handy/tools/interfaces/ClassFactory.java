package handy.tools.interfaces;


import javassist.ClassPool;

public abstract class ClassFactory {
	
	private ClassPool pool;

	public ClassPool getPool() {
		return pool;
	}

	public void setPool(ClassPool pool) {
		this.pool = pool;
	}
	
	
}
