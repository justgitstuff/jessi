package misc;
public class Pair<L,R> {

	  private L first;
	  private R second;

	 public Pair(L left, R right) {
	    first = left;
	    second = right;
	  }

	  public L getLeft() { return first; }
	  public R getRight() { return second; }
	  public void setLeft(L left) { first=left; }
	  public void setRight(R right) { second=right; }
	  public void copy(Pair<L,R> par)
	  {
		this.first=par.getLeft();
		this.second=par.getRight();
	  }
	  public int hashCode() { return first.hashCode() ^ second.hashCode(); }

	  public boolean equals(Object o) {
	    if (o == null) return false;
	    if (!(o instanceof Pair<?,?>)) return false;
	    Pair<L,R> pairo = (Pair<L,R>) o;
	    return this.first.equals(pairo.getLeft()) &&
	           this.second.equals(pairo.getRight());
	  }

	}