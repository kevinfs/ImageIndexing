package app;

public class Histogram<T> {

	private int size;
	private Object values[];

	public Histogram(int size, Object[] values) {
		super();
		this.size = size;
		this.values = values;
	}

	public Histogram(int size) {
		super();
		this.size = size;
		this.values = new Object[size];
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param position
	 *            position of the desired value
	 * @return the value at position
	 */
	public T get(int position) {
		@SuppressWarnings("unchecked")
		final T e = (T) values[position];
		return e;
	}

}
